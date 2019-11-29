package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.library.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(final String username);

    Optional<User> findByEmail(final String email);

    Optional<User> findByUsernameOrEmail(final String username, final String email);

    List<User> findByIdIn(final List<Long> userIds);

    Boolean existsByUsername(final String username);

    Boolean existsByEmail(final String email);
}
