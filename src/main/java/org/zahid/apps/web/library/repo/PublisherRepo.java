package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.PublisherEntity;

import java.util.List;

public interface PublisherRepo extends JpaRepository<PublisherEntity, Long> {
    List<PublisherEntity> findAllByOrderByPublisherIdAsc();
}
