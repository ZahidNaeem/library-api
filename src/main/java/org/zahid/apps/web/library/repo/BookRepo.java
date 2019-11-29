package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.BookEntity;

public interface BookRepo extends JpaRepository<BookEntity, Long> {
}
