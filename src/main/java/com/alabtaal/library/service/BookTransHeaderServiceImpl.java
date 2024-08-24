package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.repo.BookTransHeaderRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTransHeaderServiceImpl implements BookTransHeaderService {

  private static final Logger LOG = LoggerFactory.getLogger(BookTransHeaderServiceImpl.class);

  private final BookTransHeaderRepo bookTransHeaderRepo;

  @Override
  public List<BookTransHeaderEntity> findAll() {
    return bookTransHeaderRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<BookTransHeaderEntity> findAllByTransType(String transType) {
    return bookTransHeaderRepo.findAllByTransTypeOrderByIdAsc(transType);
  }

  @Override
  public List<BookTransHeaderEntity> findAllByReader(final ReaderEntity reader) {
    return bookTransHeaderRepo.findAllByReaderOrderByIdAsc(reader);
  }

  @Override
  public BookTransHeaderEntity findById(UUID id) {
    return bookTransHeaderRepo.findById(id)
        .orElse(new BookTransHeaderEntity());
  }

  @Override
  public boolean exists(UUID id) {
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
  public void deleteById(UUID id) {
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
    bookTransHeaderRepo.deleteAllInBatch(shelves);
  }
}
