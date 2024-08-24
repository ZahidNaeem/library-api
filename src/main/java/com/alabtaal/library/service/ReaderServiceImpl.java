package com.alabtaal.library.service;

import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.repo.ReaderRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

  private static final Logger LOG = LoggerFactory.getLogger(ReaderServiceImpl.class);

  private final ReaderRepo readerRepo;

  @Override
  public List<ReaderEntity> findAll() {
    return readerRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<ReaderEntity> searchReader(ReaderEntity readerEntity) {
    return readerRepo.searchReader(readerEntity);
  }

  @Override
  public ReaderEntity findById(UUID id) {
    return readerRepo.findById(id)
        .orElse(new ReaderEntity());
  }

  @Override
  public boolean exists(UUID id) {
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
  public void deleteById(UUID id) {
    readerRepo.deleteById(id);
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
