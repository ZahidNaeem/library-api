package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RackService {

  List<RackModel> findAll();

  ListWithPagination<RackModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<RackModel> searchRacks(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  RackModel findById(final UUID id);

  boolean exists(UUID id);

  RackModel add(RackModel model) throws BadRequestException;

  RackModel edit(RackModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
