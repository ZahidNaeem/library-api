package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.BookEntity;

import java.util.List;

public interface BookRepo extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByOrderByBookIdAsc();
}
