package com.alabtaal.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.exception.ChildRecordFoundException;
import com.alabtaal.library.repo.PublisherRepo;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private static final Logger LOG = LogManager.getLogger(PublisherServiceImpl.class);

    private final PublisherRepo publisherRepo;

    @Override
    public List<PublisherEntity> findAll() {
        return publisherRepo.findAllByOrderByPublisherIdAsc();
    }

    @Override
    public List<PublisherEntity> searchPublisher(PublisherEntity publisherEntity) {
        return publisherRepo.searchPublisher(publisherEntity);
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
        try {
            publisherRepo.deleteById(id);
        } catch (Exception e) {
            final Exception ex = Miscellaneous.getNestedException(e);
            LOG.error("Exception in delete: {}", ex.getMessage());
            if(ex.getMessage().startsWith("ORA-02292")){
                throw new ChildRecordFoundException("You can't delete this record. Child record found");
            }
        }
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
