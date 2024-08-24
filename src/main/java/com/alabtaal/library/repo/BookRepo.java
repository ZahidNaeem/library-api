package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.payload.response.SearchBookResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepo extends JpaRepository<BookEntity, UUID> {

  List<BookEntity> findAllByOrderByIdAsc();

  @Query(value = "select b from BookEntity b\n"
      + "where (:#{#bookEntity.name} is null or b.name like concat('%',:#{#bookEntity.name},'%') )\n"
      + "  and (:#{#bookEntity.author} is null or b.author = :#{#bookEntity.author})\n"
      + "  and (:#{#bookEntity.subject} is null or b.subject = :#{#bookEntity.subject})\n"
      + "  and (:#{#bookEntity.publisher} is null or b.publisher = :#{#bookEntity.publisher})\n"
      + "  and (:#{#bookEntity.researcher} is null or b.researcher = :#{#bookEntity.researcher})\n"
      + "  and (:#{#bookEntity.bookCondition} is null or b.bookCondition = :#{#bookEntity.bookCondition})\n"
      + "  and (:#{#bookEntity.purchased} is null or b.purchased = :#{#bookEntity.purchased})\n"
      + "  and (:#{#bookEntity.remarks} is null or b.remarks like concat('%',:#{#bookEntity.remarks},'%') )"
  )
  List<BookEntity> searchBook(final BookEntity bookEntity);

  @Query(
      nativeQuery = true,
      value = "SELECT B.BOOK_ID,\n" +
          "      B.BOOK_NAME,\n" +
          "      B.PUBLICATION_DATE,\n" +
          "      B.BOOK_CONDITION,\n" +
          "      B.PURCHASED,\n" +
          "      A.AUTHOR_ID,\n" +
          "      A.AUTHOR_NAME,\n" +
          "      S.SUBJECT_ID,\n" +
          "      S.SUBJECT_NAME,\n" +
          "      P.PUBLISHER_ID,\n" +
          "      P.PUBLISHER_NAME,\n" +
          "      R.RESEARCHER_ID,\n" +
          "      R.RESEARCHER_NAME,\n" +
          "      B.REMARKS\n" +
          "  FROM BOOKS B,\n" +
          "      AUTHORS A,\n" +
          "      SUBJECTS S,\n" +
          "      PUBLISHERS P,\n" +
          "      RESEARCHERS R,\n" +
          "      SHELVES F\n" +
          " WHERE     B.AUTHOR_ID = A.AUTHOR_ID(+)\n" +
          "       AND B.SUBJECT_ID = S.SUBJECT_ID(+)\n" +
          "       AND B.PUBLISHER_ID = P.PUBLISHER_ID(+)\n" +
          "       AND B.RESEARCHER_ID = R.RESEARCHER_ID(+)\n" +
          "       AND B.SHELF_ID = F.SHELF_ID(+)\n" +
          "       AND ( :AUTHOR IS NULL OR B.AUTHOR_ID = :AUTHOR)\n" +
          "       AND ( :SUBJECT IS NULL OR B.SUBJECT_ID = :SUBJECT)\n" +
          "       AND ( :PUBLISHER IS NULL OR B.PUBLISHER_ID = :PUBLISHER)\n" +
          "       AND ( :RESEARCHER IS NULL OR B.RESEARCHER_ID = :RESEARCHER)"
  )
  List<SearchBookResponse> searchBookByCriteria(
      @Param("AUTHOR") final UUID author,
      @Param("SUBJECT") final UUID subject,
      @Param("PUBLISHER") final UUID publisher,
      @Param("RESEARCHER") final UUID researcher);
}
