package com.alabtaal.library.repo;

import com.alabtaal.library.entity.TokenValidationEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenValidationRepo extends JpaRepository<TokenValidationEntity, UUID> {

  Optional<TokenValidationEntity> findByToken(String token);

  List<TokenValidationEntity> findAllByUsername(String username);
}
