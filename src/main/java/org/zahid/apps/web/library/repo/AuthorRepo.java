package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.AuthorEntity;

import java.util.List;

public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {
    List<AuthorEntity> findAllByOrderByAuthorIdAsc();
}
