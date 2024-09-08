package com.alabtaal.library.service;

import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.SubjectMapper;
import com.alabtaal.library.model.SubjectModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.SubjectRepo;
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
public class SubjectServiceImpl implements SubjectService {

  private static List<SubjectModel> subjectModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(SubjectServiceImpl.class);

  private final SubjectRepo subjectRepo;
  private final SubjectMapper subjectMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    subjectModels = subjectMapper.toModels(subjectRepo.findAll());
  }

  @Override
  public List<SubjectModel> findAll() {
    return subjectModels;
  }

  public ListWithPagination<SubjectModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        subjectModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        SubjectModel.class);
  }

  public ListWithPagination<SubjectModel> searchSubjects(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<SubjectModel> filteredModels = DynamicFilter.filter(
        subjectModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        SubjectModel.class);
  }

  @Override
  public SubjectModel findById(UUID id) {
    return subjectModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return subjectModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public SubjectModel add(SubjectModel model) throws BadRequestException {
    model.setId(null);
    final SubjectModel savedModel = save(model);
    subjectModels.add(savedModel);
    return savedModel;
  }

  @Override
  public SubjectModel edit(SubjectModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final SubjectModel savedModel = save(model);
    final Optional<SubjectModel> modelFound = subjectModels
        .stream()
        .filter(subjectModel -> subjectModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(subjectModel -> subjectModels.set(subjectModels.indexOf(subjectModel), savedModel));
    return savedModel;
  }

  private SubjectModel save(SubjectModel model) throws BadRequestException {
    final SubjectEntity entity = subjectMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
Miscellaneous.constraintViolation(entity);
    return subjectMapper.toModel(subjectRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    subjectRepo.deleteById(id);
    subjectModels.removeIf(model -> model.getId().equals(id));
  }
}
