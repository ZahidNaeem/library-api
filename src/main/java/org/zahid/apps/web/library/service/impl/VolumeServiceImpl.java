package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.payload.response.SearchVolumeResponse;
import org.zahid.apps.web.library.repo.VolumeRepo;
import org.zahid.apps.web.library.service.VolumeService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import java.util.List;
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
    public List<SearchVolumeResponse> findAllSearchResponses() {
        return Miscellaneous.searchAllVolumes();
    }

    @Override
    public List<SearchVolumeResponse> findAllByBookId(Long id) {
//        return volumeRepo.findAllByBookId(id);
        return Miscellaneous.searchVolumeByBookId(id);
    }

    @Override
    public VolumeEntity findById(Long id) {
        return volumeRepo.findById(id)
                .orElse(new VolumeEntity());
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
