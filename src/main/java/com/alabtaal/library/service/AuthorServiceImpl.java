package com.alabtaal.library.service;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.repo.AuthorRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private static final Logger LOG = LoggerFactory.getLogger(AuthorServiceImpl.class);

  private final AuthorRepo authorRepo;

  @Override
  public List<AuthorEntity> findAll() {
    return authorRepo.findAll();
  }

  @Override
  public List<AuthorEntity> searchAuthor(AuthorEntity authorEntity) {
    return authorRepo.searchAuthor(authorEntity);
  }

  @Override
  public AuthorEntity findById(UUID id) {
    return authorRepo.findById(id)
        .orElse(new AuthorEntity());
  }

  @Override
  public boolean exists(UUID id) {
    return authorRepo.existsById(id);
  }

  @Override
  public AuthorEntity save(AuthorEntity author) {
    return authorRepo.saveAndFlush(author);
  }

  @Override
  public List<AuthorEntity> save(Set<AuthorEntity> authors) {
    return authorRepo.saveAll(authors);
  }

  @Override
  public void delete(AuthorEntity author) {
    authorRepo.delete(author);
  }

  @Override
  public void delete(Set<AuthorEntity> authors) {
    authorRepo.deleteAll(authors);
  }

  @Override
  public void deleteById(UUID id) {
    authorRepo.deleteById(id);
  }

  @Override
  public void deleteAll() {
    authorRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() {
    authorRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<AuthorEntity> authors) {
    authorRepo.deleteInBatch(authors);
  }
}
