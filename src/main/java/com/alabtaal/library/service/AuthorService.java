package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.AuthorModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public interface AuthorService {

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  void refreshCachedModels();

  List<AuthorModel> findAll();

  ListWithPagination<AuthorModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<AuthorModel> searchAuthors(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  AuthorModel findById(final UUID id);

  boolean exists(UUID id);

  AuthorModel add(AuthorModel model) throws BadRequestException;

  AuthorModel edit(AuthorModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
