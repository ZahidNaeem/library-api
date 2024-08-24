package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.payload.response.SearchBookResponse;
import com.alabtaal.library.repo.BookRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

  private final BookRepo bookRepo;

  @Override
  public List<BookEntity> findAll() {
    return bookRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<BookEntity> searchBook(BookEntity bookEntity) {
    return bookRepo.searchBook(bookEntity);
  }

  @Override
  public List<SearchBookResponse> searchByCriteria(
      final UUID author,
      final UUID subject,
      final UUID publisher,
      final UUID researcher) {
    return bookRepo.searchBookByCriteria(author, subject, publisher, researcher);
  }

  @Override
  public BookEntity findById(UUID id) {
    return bookRepo.findById(id)
        .orElse(new BookEntity());
  }

  @Override
  public boolean exists(UUID id) {
    return bookRepo.existsById(id);
  }

  @Override
  public BookEntity save(BookEntity book) {
    return bookRepo.saveAndFlush(book);
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
  public void deleteById(UUID id) {
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
