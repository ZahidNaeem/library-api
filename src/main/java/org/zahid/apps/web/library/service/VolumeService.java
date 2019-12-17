package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.entity.BookEntity;

import java.util.List;
import java.util.Set;

public interface VolumeService {

    List<VolumeEntity> findAll();

    List<VolumeEntity> findAllByBook(final BookEntity book);

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