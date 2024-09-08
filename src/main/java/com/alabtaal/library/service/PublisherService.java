package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.PublisherModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public interface PublisherService {

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  void refreshCachedModels();

  List<PublisherModel> findAll();

  ListWithPagination<PublisherModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<PublisherModel> searchPublishers(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  PublisherModel findById(final UUID id);

  boolean exists(UUID id);

  PublisherModel add(PublisherModel model) throws BadRequestException;

  PublisherModel edit(PublisherModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
