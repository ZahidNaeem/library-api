package com.alabtaal.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.exception.ChildRecordFoundException;
import com.alabtaal.library.repo.BookTransHeaderRepo;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookTransHeaderServiceImpl implements BookTransHeaderService {

    private static final Logger LOG = LogManager.getLogger(BookTransHeaderServiceImpl.class);

    private final BookTransHeaderRepo bookTransHeaderRepo;

    @Override
    public List<BookTransHeaderEntity> findAll() {
        return bookTransHeaderRepo.findAllByOrderByHeaderIdAsc();
    }

    @Override
    public List<BookTransHeaderEntity> findAllByTransType(String transType) {
        return bookTransHeaderRepo.findAllByTransTypeOrderByHeaderIdAsc(transType);
    }

    @Override
    public List<BookTransHeaderEntity> findAllByReader(final ReaderEntity reader) {
        return bookTransHeaderRepo.findAllByReaderOrderByHeaderIdAsc(reader);
    }

    @Override
    public BookTransHeaderEntity findById(Long id) {
        return bookTransHeaderRepo.findById(id)
                .orElse(new BookTransHeaderEntity());
    }

    @Override
    public boolean exists(Long id) {
        return bookTransHeaderRepo.existsById(id);
    }

    @Override
    public BookTransHeaderEntity save(BookTransHeaderEntity bookTransHeader) {
        return bookTransHeaderRepo.saveAndFlush(bookTransHeader);
    }

    @Override
    public List<BookTransHeaderEntity> save(Set<BookTransHeaderEntity> shelves) {
        return bookTransHeaderRepo.saveAll(shelves);
    }

    @Override
    public void delete(BookTransHeaderEntity bookTransHeader) {
        bookTransHeaderRepo.delete(bookTransHeader);
    }

    @Override
    public void delete(Set<BookTransHeaderEntity> shelves) {
        bookTransHeaderRepo.deleteAll(shelves);
    }

    @Override
    public void deleteById(Long id) {
        try {
            bookTransHeaderRepo.deleteById(id);
        } catch (Exception e) {
            final Exception ex = Miscellaneous.getNestedException(e);
            LOG.error("Exception in delete: {}", ex.getMessage());
            if(ex.getMessage().startsWith("ORA-02292")){
                throw new ChildRecordFoundException("You can't delete this record. Child record found");
            }
        }
    }

    @Override
    public void deleteAll() {
        bookTransHeaderRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        bookTransHeaderRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<BookTransHeaderEntity> shelves) {
        bookTransHeaderRepo.deleteInBatch(shelves);
    }
}
