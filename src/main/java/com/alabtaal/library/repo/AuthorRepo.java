package com.alabtaal.library.repo;

import com.alabtaal.library.entity.AuthorEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepo extends JpaRepository<AuthorEntity, UUID> {

  Page<AuthorEntity> findAll(Specification<AuthorEntity> spec, final Pageable pageable);

  @Query(value = "select a from AuthorEntity a\n"
      + "where (:#{#authorEntity.name} is null or a.name like concat('%',:#{#authorEntity.name},'%'))\n"
      + "  and (:#{#authorEntity.remarks} is null or a.remarks like concat('%',:#{#authorEntity.remarks},'%'))")
  List<AuthorEntity> searchAuthor(final AuthorEntity authorEntity);
}
