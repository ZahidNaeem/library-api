package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.BookTransLineEntity;

import java.util.List;

@Repository
public interface BookTransLineRepo extends JpaRepository<BookTransLineEntity, Long> {
    List<BookTransLineEntity> findAllByOrderByLineIdAsc();

    List<BookTransLineEntity> findAllByBookTransHeaderOrderByLineIdAsc(final BookTransHeaderEntity bookTransHeader);
}
