package com.alabtaal.library.service;

import com.alabtaal.library.entity.AuthorEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AuthorService {

  List<AuthorEntity> findAll();

  List<AuthorEntity> searchAuthor(final AuthorEntity authorEntity);

  AuthorEntity findById(final UUID id);

  boolean exists(UUID id);

  AuthorEntity save(AuthorEntity author);

  List<AuthorEntity> save(Set<AuthorEntity> authors);

  void delete(AuthorEntity author);

  void delete(Set<AuthorEntity> authors);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<AuthorEntity> authors);
}
