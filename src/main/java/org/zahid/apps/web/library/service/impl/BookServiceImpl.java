package org.zahid.apps.web.library.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.repo.BookRepo;
import org.zahid.apps.web.library.service.BookService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOG = LogManager.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepo bookRepo;

    @Override
    public List<BookEntity> findAll() {
        return bookRepo.findAllByOrderByBookIdAsc();
    }

    @Override
    public List<BookModel> searchByCriteria(final Integer author, final Integer subject, final Integer publisher, final Integer researcher) {
        return Miscellaneous.searchBookByCriteria(author, subject, publisher, researcher);
    }

    @Override
    public BookEntity findById(Long id) {
        return bookRepo.findById(id)
                .orElse(new BookEntity());
    }

    @Override
    public boolean exists(Long id) {
        return bookRepo.existsById(id);
    }

    @Override
    public BookEntity save(BookEntity book) {
        return bookRepo.save(book);
    }

    @Override
    public List<BookEntity> save(Set<BookEntity> books) {
        return bookRepo.saveAll(books);
    }

    @Override
    public void delete(BookEntity book) {
        bookRepo.delete(book);
    }

    @Override
    public void delete(Set<BookEntity> books) {
        bookRepo.deleteAll(books);
    }

    @Override
    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        bookRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        bookRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<BookEntity> books) {
        bookRepo.deleteInBatch(books);
    }
}
