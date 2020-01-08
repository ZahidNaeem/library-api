package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.repo.ReaderRepo;
import org.zahid.apps.web.library.service.ReaderService;

import java.util.List;
import java.util.Set;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepo readerRepo;

    @Override
    public List<ReaderEntity> findAll() {
        return readerRepo.findAllByOrderByReaderIdAsc();
    }

    @Override
    public ReaderEntity findById(Long id) {
        return readerRepo.findById(id)
                .orElse(new ReaderEntity());
    }

    @Override
    public boolean exists(Long id) {
        return readerRepo.existsById(id);
    }

    @Override
    public ReaderEntity save(ReaderEntity reader) {
        return readerRepo.save(reader);
    }

    @Override
    public List<ReaderEntity> save(Set<ReaderEntity> readers) {
        return readerRepo.saveAll(readers);
    }

    @Override
    public void delete(ReaderEntity reader) {
        readerRepo.delete(reader);
    }

    @Override
    public void delete(Set<ReaderEntity> readers) {
        readerRepo.deleteAll(readers);
    }

    @Override
    public void deleteById(Long id) {
        readerRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        readerRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        readerRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<ReaderEntity> readers) {
        readerRepo.deleteInBatch(readers);
    }
}
