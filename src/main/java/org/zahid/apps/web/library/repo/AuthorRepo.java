package org.zahid.apps.web.library.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zahid.apps.web.library.entity.AuthorEntity;

public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {

  List<AuthorEntity> findAllByOrderByAuthorIdAsc();

  @Query(value = "select a from AuthorEntity a\n"
      + "where (:#{#authorEntity.authorName} is null or a.authorName like concat('%',:#{#authorEntity.authorName},'%'))\n"
      + "  and (:#{#authorEntity.remarks} is null or a.remarks like concat('%',:#{#authorEntity.remarks},'%'))")
  List<AuthorEntity> searchAuthor(final AuthorEntity authorEntity);
}
