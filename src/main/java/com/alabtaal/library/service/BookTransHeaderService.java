package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.BookTransHeaderModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public interface BookTransHeaderService {

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  void refreshCachedModels();

  List<BookTransHeaderModel> findAll();

  ListWithPagination<BookTransHeaderModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<BookTransHeaderModel> searchBookTransHeaders(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  BookTransHeaderModel findById(final UUID id);

  boolean exists(UUID id);

  BookTransHeaderModel add(BookTransHeaderModel model) throws BadRequestException;

  BookTransHeaderModel edit(BookTransHeaderModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
