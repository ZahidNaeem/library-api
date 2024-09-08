package com.alabtaal.library.service;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.RackMapper;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.RackRepo;
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
public class RackServiceImpl implements RackService {

  private static List<RackModel> rackModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(RackServiceImpl.class);

  private final RackRepo rackRepo;
  private final RackMapper rackMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    rackModels = rackMapper.toModels(rackRepo.findAll());
  }

  @Override
  public List<RackModel> findAll() {
    return rackModels;
  }

  public ListWithPagination<RackModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        rackModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        RackModel.class);
  }

  public ListWithPagination<RackModel> searchRacks(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<RackModel> filteredModels = DynamicFilter.filter(
        rackModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        RackModel.class);
  }

  @Override
  public RackModel findById(UUID id) {
    return rackModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return rackModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public RackModel add(final RackModel model) throws BadRequestException {
    model.setId(null);
    final RackModel savedModel = save(model);
    rackModels.add(savedModel);
    return savedModel;
  }

  @Override
  public RackModel edit(final RackModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final RackModel savedModel = save(model);
        final Optional<RackModel> modelFound = rackModels
        .stream()
        .filter(rackModel -> rackModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(rackModel -> rackModels.set(rackModels.indexOf(rackModel), savedModel));
    return savedModel;
  }

  private RackModel save(RackModel model) throws BadRequestException {
    final RackEntity entity = rackMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
Miscellaneous.constraintViolation(entity);
    return rackMapper.toModel(rackRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    rackRepo.deleteById(id);
    rackModels.removeIf(model -> model.getId().equals(id));
  }
}
