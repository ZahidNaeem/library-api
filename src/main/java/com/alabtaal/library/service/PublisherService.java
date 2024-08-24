package com.alabtaal.library.service;

import com.alabtaal.library.entity.PublisherEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PublisherService {

  List<PublisherEntity> findAll();

  List<PublisherEntity> searchPublisher(final PublisherEntity publisherEntity);

  PublisherEntity findById(final UUID id);

  boolean exists(UUID id);

  PublisherEntity save(PublisherEntity publisher);

  List<PublisherEntity> save(Set<PublisherEntity> publishers);

  void delete(PublisherEntity publisher);

  void delete(Set<PublisherEntity> publishers);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<PublisherEntity> publishers);
}
