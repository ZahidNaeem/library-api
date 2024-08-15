package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.payload.response.SearchVolumeResponse;

import java.util.List;
import java.util.Set;

public interface VolumeService {

    List<VolumeEntity> findAll();

    List<VolumeEntity> findAllByBook(final BookEntity book);

    List<SearchVolumeResponse> findAllSearchResponses();

    List<SearchVolumeResponse> findAllByBookId(final Long id);

    VolumeEntity findById(final Long id);

    boolean exists(Long id);

    VolumeEntity save(VolumeEntity volume);

    List<VolumeEntity> save(Set<VolumeEntity> volumes);

    void delete(VolumeEntity volume);

    void delete(Set<VolumeEntity> volumes);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<VolumeEntity> volumes);
}
