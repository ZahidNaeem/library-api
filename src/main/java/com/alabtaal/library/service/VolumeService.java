package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VolumeService {

  List<VolumeModel> findAll();

  ListWithPagination<VolumeModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<VolumeModel> searchVolumes(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  List<VolumeModel> findAllByBook(final BookModel book);

  VolumeModel findById(final UUID id);

  boolean exists(UUID id);

  VolumeModel add(VolumeModel model) throws BadRequestException;

  VolumeModel edit(VolumeModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
