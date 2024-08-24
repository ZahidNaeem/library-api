package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransLineEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransLineRepo extends JpaRepository<BookTransLineEntity, UUID> {

  List<BookTransLineEntity> findAllByOrderByIdAsc();

  List<BookTransLineEntity> findAllByBookTransHeaderOrderByIdAsc(final BookTransHeaderEntity bookTransHeader);
}
