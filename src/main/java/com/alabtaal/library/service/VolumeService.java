package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.payload.response.SearchVolumeResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface VolumeService {

  List<VolumeEntity> findAll();

  List<VolumeEntity> findAllByBook(final BookEntity book);

  List<SearchVolumeResponse> findAllSearchResponses();

  List<SearchVolumeResponse> findAllByBookId(final UUID id);

  VolumeEntity findById(final UUID id);

  boolean exists(UUID id);

  VolumeEntity save(VolumeEntity volume);

  List<VolumeEntity> save(Set<VolumeEntity> volumes);

  void delete(VolumeEntity volume);

  void delete(Set<VolumeEntity> volumes);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<VolumeEntity> volumes);
}
