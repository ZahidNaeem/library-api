package com.alabtaal.library.repo;

import com.alabtaal.library.entity.AuthorEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<AuthorEntity, UUID> {

  Page<AuthorEntity> findAll(Specification<AuthorEntity> spec, final Pageable pageable);
}
