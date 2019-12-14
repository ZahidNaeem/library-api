package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.exception.BookNotFoundException;
import org.zahid.apps.web.library.repo.BookRepo;
import org.zahid.apps.web.library.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public List<BookEntity> findAll() {
        return bookRepo.findAllByOrderByBookIdAsc();
    }

    @Override
    public BookEntity findById(Long id) {
        return Optional.ofNullable(bookRepo.findById(id))
                .map(book -> book.get())
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));
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
