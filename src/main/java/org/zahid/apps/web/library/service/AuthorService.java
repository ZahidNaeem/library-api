package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.AuthorEntity;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    List<AuthorEntity> findAll();

    List<AuthorEntity> searchAuthor(final AuthorEntity authorEntity);

    AuthorEntity findById(final Long id);

    boolean exists(Long id);

    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> save(Set<AuthorEntity> authors);

    void delete(AuthorEntity author);

    void delete(Set<AuthorEntity> authors);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<AuthorEntity> authors);
}
