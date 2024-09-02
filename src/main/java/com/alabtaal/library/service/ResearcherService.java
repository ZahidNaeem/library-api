package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.ResearcherModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ResearcherService {

  List<ResearcherModel> findAll();

  ListWithPagination<ResearcherModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<ResearcherModel> searchResearchers(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ResearcherModel findById(final UUID id);

  boolean exists(UUID id);

  ResearcherModel add(ResearcherModel model) throws BadRequestException;

  ResearcherModel edit(ResearcherModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
