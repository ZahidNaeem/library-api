package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.AuthorEntity;

public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {
}
