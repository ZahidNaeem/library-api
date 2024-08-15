package com.alabtaal.library.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.alabtaal.library.entity.BookEntity;

public interface BookRepo extends JpaRepository<BookEntity, Long> {

  List<BookEntity> findAllByOrderByBookIdAsc();

  @Query(value = "select b from BookEntity b\n"
      + "where (:#{#bookEntity.bookName} is null or b.bookName like concat('%',:#{#bookEntity.bookName},'%') )\n"
      + "  and (:#{#bookEntity.author} is null or b.author = :#{#bookEntity.author})\n"
      + "  and (:#{#bookEntity.subject} is null or b.subject = :#{#bookEntity.subject})\n"
      + "  and (:#{#bookEntity.publisher} is null or b.publisher = :#{#bookEntity.publisher})\n"
      + "  and (:#{#bookEntity.researcher} is null or b.researcher = :#{#bookEntity.researcher})\n"
      + "  and (:#{#bookEntity.bookCondition} is null or b.bookCondition = :#{#bookEntity.bookCondition})\n"
      + "  and (:#{#bookEntity.purchased} is null or b.purchased = :#{#bookEntity.purchased})\n"
      + "  and (:#{#bookEntity.remarks} is null or b.remarks like concat('%',:#{#bookEntity.remarks},'%') )"
  )
  List<BookEntity> searchBook(final BookEntity bookEntity);
}
