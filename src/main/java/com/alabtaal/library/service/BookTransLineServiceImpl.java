package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.repo.BookTransLineRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTransLineServiceImpl implements BookTransLineService {

  private static final Logger LOG = LoggerFactory.getLogger(BookTransLineServiceImpl.class);

  private final BookTransLineRepo bookTransLineRepo;

  @Override
  public List<BookTransLineEntity> findAll() {
    return bookTransLineRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<BookTransLineEntity> findAllByBookTransHeader(BookTransHeaderEntity bookTransHeader) {
    return bookTransLineRepo.findAllByBookTransHeaderOrderByIdAsc(bookTransHeader);
  }

  @Override
  public BookTransLineEntity findById(UUID id) {
    return bookTransLineRepo.findById(id)
        .orElse(new BookTransLineEntity());
  }

  @Override
  public boolean exists(UUID id) {
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
  public void deleteById(UUID id) {
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
