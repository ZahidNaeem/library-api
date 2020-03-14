package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.exception.ChildRecordFoundException;
import org.zahid.apps.web.library.repo.BookTransLineRepo;
import org.zahid.apps.web.library.service.BookTransLineService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookTransLineServiceImpl implements BookTransLineService {

    private static final Logger LOG = LogManager.getLogger(BookTransLineServiceImpl.class);

    private final BookTransLineRepo bookTransLineRepo;

    @Override
    public List<BookTransLineEntity> findAll() {
        return bookTransLineRepo.findAllByOrderByLineIdAsc();
    }

    @Override
    public List<BookTransLineEntity> findAllByBookTransHeader(BookTransHeaderEntity bookTransHeader) {
        return bookTransLineRepo.findAllByBookTransHeaderOrderByLineIdAsc(bookTransHeader);
    }

    @Override
    public BookTransLineEntity findById(Long id) {
        return bookTransLineRepo.findById(id)
                .orElse(new BookTransLineEntity());
    }

    @Override
    public boolean exists(Long id) {
        return bookTransLineRepo.existsById(id);
    }

    @Override
    public BookTransLineEntity save(BookTransLineEntity bookTransLine) {
        return bookTransLineRepo.saveAndFlush(bookTransLine);
    }

    @Override
    public List<BookTransLineEntity> save(Set<BookTransLineEntity> bookTransLines) {
        return bookTransLineRepo.saveAll(bookTransLines);
    }

    @Override
    public void delete(BookTransLineEntity bookTransLine) {
        bookTransLineRepo.delete(bookTransLine);
    }

    @Override
    public void delete(Set<BookTransLineEntity> bookTransLines) {
        bookTransLineRepo.deleteAll(bookTransLines);
    }

    @Override
    public void deleteById(Long id) {
        try {
            bookTransLineRepo.deleteById(id);
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
        bookTransLineRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        bookTransLineRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<BookTransLineEntity> bookTransLines) {
        bookTransLineRepo.deleteInBatch(bookTransLines);
    }
}
