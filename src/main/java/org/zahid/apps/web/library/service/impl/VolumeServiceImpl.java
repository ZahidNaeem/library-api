package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.exception.VolumeNotFoundException;
import org.zahid.apps.web.library.repo.VolumeRepo;
import org.zahid.apps.web.library.service.VolumeService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VolumeServiceImpl implements VolumeService {

    @Autowired
    private VolumeRepo volumeRepo;

    @Override
    public List<VolumeEntity> findAll() {
        return volumeRepo.findAllByOrderByVolumeIdAsc();
    }

    @Override
    public List<VolumeEntity> findAllByBook(final BookEntity book) {
        return volumeRepo.findAllByBookOrderByVolumeIdAsc(book);
    }

    @Override
    public VolumeEntity findById(Long id) {
        return Optional.ofNullable(volumeRepo.findById(id))
                .map(volume -> volume.get())
                .orElseThrow(() -> new VolumeNotFoundException("Volume with id: " + id + " not found"));
    }

    @Override
    public boolean exists(Long id) {
        return volumeRepo.existsById(id);
    }

    @Override
    public VolumeEntity save(VolumeEntity volume) {
        return volumeRepo.save(volume);
    }

    @Override
    public List<VolumeEntity> save(Set<VolumeEntity> volumes) {
        return volumeRepo.saveAll(volumes);
    }

    @Override
    public void delete(VolumeEntity volume) {
        volumeRepo.delete(volume);
    }

    @Override
    public void delete(Set<VolumeEntity> volumes) {
        volumeRepo.deleteAll(volumes);
    }

    @Override
    public void deleteById(Long id) {
        volumeRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        volumeRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        volumeRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<VolumeEntity> volumes) {
        volumeRepo.deleteInBatch(volumes);
    }
}
