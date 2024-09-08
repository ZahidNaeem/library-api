package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public interface BookTransLineService {

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  void refreshCachedModels();

  List<BookTransLineModel> findAll();

  ListWithPagination<BookTransLineModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<BookTransLineModel> searchBookTransLines(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  BookTransLineModel findById(final UUID id);

  boolean exists(UUID id);

  BookTransLineModel add(BookTransLineModel model) throws BadRequestException;

  BookTransLineModel edit(BookTransLineModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
