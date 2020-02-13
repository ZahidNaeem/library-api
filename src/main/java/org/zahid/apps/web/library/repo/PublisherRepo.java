package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zahid.apps.web.library.entity.PublisherEntity;

import java.util.List;

public interface PublisherRepo extends JpaRepository<PublisherEntity, Long> {
    List<PublisherEntity> findAllByOrderByPublisherIdAsc();

    @Query(value = "select a from PublisherEntity a\n"
        + "where (:#{#publisherEntity.publisherName} is null or a.publisherName like concat('%',:#{#publisherEntity.publisherName},'%'))\n"
        + "  and (:#{#publisherEntity.remarks} is null or a.remarks like concat('%',:#{#publisherEntity.remarks},'%'))")
    List<PublisherEntity> searchPublisher(final PublisherEntity publisherEntity);
}
