package com.alabtaal.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransLineEntity;

import java.util.List;

@Repository
public interface BookTransLineRepo extends JpaRepository<BookTransLineEntity, Long> {
    List<BookTransLineEntity> findAllByOrderByLineIdAsc();

    List<BookTransLineEntity> findAllByBookTransHeaderOrderByLineIdAsc(final BookTransHeaderEntity bookTransHeader);
}
