package com.alabtaal.library.service;

import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.repo.PublisherRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

  private static final Logger LOG = LoggerFactory.getLogger(PublisherServiceImpl.class);

  private final PublisherRepo publisherRepo;

  @Override
  public List<PublisherEntity> findAll() {
    return publisherRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<PublisherEntity> searchPublisher(PublisherEntity publisherEntity) {
    return publisherRepo.searchPublisher(publisherEntity);
  }

  @Override
  public PublisherEntity findById(UUID id) {
    return publisherRepo.findById(id)
        .orElse(new PublisherEntity());
  }

  @Override
  public boolean exists(UUID id) {
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
  public void deleteById(UUID id) {
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
