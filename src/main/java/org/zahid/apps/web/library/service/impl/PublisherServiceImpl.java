package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.PublisherEntity;
import org.zahid.apps.web.library.repo.PublisherRepo;
import org.zahid.apps.web.library.service.PublisherService;

import java.util.List;
import java.util.Set;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepo publisherRepo;

    @Override
    public List<PublisherEntity> findAll() {
        return publisherRepo.findAllByOrderByPublisherIdAsc();
    }

    @Override
    public PublisherEntity findById(Long id) {
        return publisherRepo.findById(id)
                .orElse(new PublisherEntity());
    }

    @Override
    public boolean exists(Long id) {
        return publisherRepo.existsById(id);
    }

    @Override
    public PublisherEntity save(PublisherEntity publisher) {
        return publisherRepo.saveAndFlush(publisher);
    }

    @Override
    public List<PublisherEntity> save(Set<PublisherEntity> publishers) {
        return publisherRepo.saveAll(publishers);
    }

    @Override
    public void delete(PublisherEntity publisher) {
        publisherRepo.delete(publisher);
    }

    @Override
    public void delete(Set<PublisherEntity> publishers) {
        publisherRepo.deleteAll(publishers);
    }

    @Override
    public void deleteById(Long id) {
        publisherRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        publisherRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        publisherRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<PublisherEntity> publishers) {
        publisherRepo.deleteInBatch(publishers);
    }
}
