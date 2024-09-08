package com.alabtaal.library.service;

import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.PublisherMapper;
import com.alabtaal.library.model.PublisherModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.PublisherRepo;
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
public class PublisherServiceImpl implements PublisherService {

  private static List<PublisherModel> publisherModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(PublisherServiceImpl.class);

  private final PublisherRepo publisherRepo;
  private final PublisherMapper publisherMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    publisherModels = publisherMapper.toModels(publisherRepo.findAll());
  }

  @Override
  public List<PublisherModel> findAll() {
    return publisherModels;
  }

  public ListWithPagination<PublisherModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        publisherModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        PublisherModel.class);
  }

  public ListWithPagination<PublisherModel> searchPublishers(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<PublisherModel> filteredModels = DynamicFilter.filter(
        publisherModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        PublisherModel.class);
  }

  @Override
  public PublisherModel findById(UUID id) {
    return publisherModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return publisherModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public PublisherModel add(final PublisherModel model) throws BadRequestException {
    model.setId(null);
    final PublisherModel savedModel = save(model);
    publisherModels.add(savedModel);
    return savedModel;
  }

  @Override
  public PublisherModel edit(final PublisherModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final PublisherModel savedModel = save(model);
        final Optional<PublisherModel> modelFound = publisherModels
        .stream()
        .filter(publisherModel -> publisherModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(publisherModel -> publisherModels.set(publisherModels.indexOf(publisherModel), savedModel));
    return savedModel;
  }

  private PublisherModel save(PublisherModel model) throws BadRequestException {
    final PublisherEntity entity = publisherMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
Miscellaneous.constraintViolation(entity);
    return publisherMapper.toModel(publisherRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    publisherRepo.deleteById(id);
    publisherModels.removeIf(model -> model.getId().equals(id));
  }
}
