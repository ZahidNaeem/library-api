package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.repo.BookTransLineRepo;
import org.zahid.apps.web.library.service.BookTransLineService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookTransLineServiceImpl implements BookTransLineService {

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
        bookTransLineRepo.deleteById(id);
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
