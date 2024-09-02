package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.ReaderModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReaderService {

  List<ReaderModel> findAll();

  ListWithPagination<ReaderModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<ReaderModel> searchReaders(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ReaderModel findById(final UUID id);

  boolean exists(UUID id);

  ReaderModel add(ReaderModel model) throws BadRequestException;

  ReaderModel edit(ReaderModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
