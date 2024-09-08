package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public interface BookService {

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  void refreshCachedModels();

  List<BookModel> findAll();

  ListWithPagination<BookModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<BookModel> searchBooks(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  BookModel findById(final UUID id);

  boolean exists(UUID id);

  BookModel add(BookModel model) throws BadRequestException;

  BookModel edit(BookModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
