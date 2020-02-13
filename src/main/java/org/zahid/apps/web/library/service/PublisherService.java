package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.PublisherEntity;

import java.util.List;
import java.util.Set;

public interface PublisherService {

    List<PublisherEntity> findAll();

    List<PublisherEntity> searchPublisher(final PublisherEntity publisherEntity);

    PublisherEntity findById(final Long id);

    boolean exists(Long id);

    PublisherEntity save(PublisherEntity publisher);

    List<PublisherEntity> save(Set<PublisherEntity> publishers);

    void delete(PublisherEntity publisher);

    void delete(Set<PublisherEntity> publishers);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<PublisherEntity> publishers);
}
