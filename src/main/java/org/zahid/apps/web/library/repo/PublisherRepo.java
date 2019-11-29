package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.PublisherEntity;

public interface PublisherRepo extends JpaRepository<PublisherEntity, Long> {
}
