package com.alabtaal.library.service;

import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.ResearcherMapper;
import com.alabtaal.library.model.ResearcherModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.ResearcherRepo;
import com.alabtaal.library.util.DynamicFilter;
import com.alabtaal.library.util.ListToPageConverter;
import com.alabtaal.library.util.Miscellaneous;
import com.alabtaal.library.util.RelationshipHandler;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResearcherServiceImpl implements ResearcherService {

  private static List<ResearcherModel> researcherModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(ResearcherServiceImpl.class);

  private final ResearcherRepo researcherRepo;
  private final ResearcherMapper researcherMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    researcherModels = researcherMapper.toModels(researcherRepo.findAll());
  }

  @Override
  public List<ResearcherModel> findAll() {
    return researcherModels;
  }

  public ListWithPagination<ResearcherModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        researcherModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        ResearcherModel.class);
  }

  public ListWithPagination<ResearcherModel> searchResearchers(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<ResearcherModel> filteredModels = DynamicFilter.filter(
        researcherModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        ResearcherModel.class);
  }

  @Override
  public ResearcherModel findById(UUID id) {
    return researcherModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return researcherModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public ResearcherModel add(ResearcherModel model) throws BadRequestException {
    model.setId(null);
    final ResearcherModel savedModel = save(model);
    researcherModels.add(savedModel);
    return savedModel;
  }

  @Override
  public ResearcherModel edit(ResearcherModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final ResearcherModel savedModel = save(model);
        final Optional<ResearcherModel> modelFound = researcherModels
        .stream()
        .filter(researcherModel -> researcherModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(researcherModel -> researcherModels.set(researcherModels.indexOf(researcherModel), savedModel));
    return savedModel;
  }

  private ResearcherModel save(ResearcherModel model) throws BadRequestException {
    final ResearcherEntity entity = researcherMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
Miscellaneous.constraintViolation(entity);
    return researcherMapper.toModel(researcherRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    researcherRepo.deleteById(id);
    researcherModels.removeIf(model -> model.getId().equals(id));
  }
}
