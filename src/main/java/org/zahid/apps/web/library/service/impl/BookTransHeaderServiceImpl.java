package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.repo.BookTransHeaderRepo;
import org.zahid.apps.web.library.service.BookTransHeaderService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookTransHeaderServiceImpl implements BookTransHeaderService {

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
        bookTransHeaderRepo.deleteById(id);
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
