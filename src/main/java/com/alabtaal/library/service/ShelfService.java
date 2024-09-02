package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.ShelfModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShelfService {

  List<ShelfModel> findAll();

  ListWithPagination<ShelfModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<ShelfModel> searchShelves(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ShelfModel findById(final UUID id);

  boolean exists(UUID id);

  ShelfModel add(ShelfModel model) throws BadRequestException;

  ShelfModel edit(ShelfModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
