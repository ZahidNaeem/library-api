package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.exception.ChildRecordFoundException;
import org.zahid.apps.web.library.repo.ReaderRepo;
import org.zahid.apps.web.library.service.ReaderService;

import java.util.List;
import java.util.Set;
import org.zahid.apps.web.library.utils.Miscellaneous;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private static final Logger LOG = LogManager.getLogger(ReaderServiceImpl.class);

    private final ReaderRepo readerRepo;

    @Override
    public List<ReaderEntity> findAll() {
        return readerRepo.findAllByOrderByReaderIdAsc();
    }

    @Override
    public List<ReaderEntity> searchReader(ReaderEntity readerEntity) {
        return readerRepo.searchReader(readerEntity);
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
        return readerRepo.saveAndFlush(reader);
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
        try {
            readerRepo.deleteById(id);
        } catch (Exception e) {
            final Exception ex = Miscellaneous.getNestedException(e);
            LOG.error("Exception in delete: {}", ex.getMessage());
            if(ex.getMessage().startsWith("ORA-02292")){
                throw new ChildRecordFoundException("You can't delete this reader. Child record found");
            }
        }
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
