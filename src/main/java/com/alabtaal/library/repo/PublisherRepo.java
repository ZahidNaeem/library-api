package com.alabtaal.library.repo;

import com.alabtaal.library.entity.PublisherEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PublisherRepo extends JpaRepository<PublisherEntity, UUID> {

  List<PublisherEntity> findAllByOrderByIdAsc();

  @Query(value = "select a from PublisherEntity a\n"
      + "where (:#{#publisherEntity.name} is null or a.name like concat('%',:#{#publisherEntity.name},'%'))\n"
      + "  and (:#{#publisherEntity.remarks} is null or a.remarks like concat('%',:#{#publisherEntity.remarks},'%'))")
  List<PublisherEntity> searchPublisher(final PublisherEntity publisherEntity);
}
