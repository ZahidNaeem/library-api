package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.exception.ChildRecordFoundException;
import org.zahid.apps.web.library.repo.RackRepo;
import org.zahid.apps.web.library.service.RackService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RackServiceImpl implements RackService {

    public static final Logger LOG = LogManager.getLogger(RackServiceImpl.class);

    private final RackRepo rackRepo;

    @Override
    public List<RackEntity> findAll() {
        return rackRepo.findAllByOrderByRackIdAsc();
    }

    @Override
    public List<RackEntity> findAllByShelf(final ShelfEntity shelf) {
        return rackRepo.findAllByShelfOrderByRackIdAsc(shelf);
    }

    @Override
    public RackEntity findById(Long id) {
        return rackRepo.findById(id)
                .orElse(new RackEntity());
    }

    @Override
    public boolean exists(Long id) {
        return rackRepo.existsById(id);
    }

    @Override
    public RackEntity save(RackEntity rack) {
        return rackRepo.saveAndFlush(rack);
    }

    @Override
    public List<RackEntity> save(Set<RackEntity> racks) {
        return rackRepo.saveAll(racks);
    }

    @Override
    public void delete(RackEntity rack) {
        rackRepo.delete(rack);
    }

    @Override
    public void delete(Set<RackEntity> racks) {
        rackRepo.deleteAll(racks);
    }

    @Override
    public void deleteById(Long id) {
        try {
            rackRepo.deleteById(id);
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
        rackRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        rackRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<RackEntity> racks) {
        rackRepo.deleteInBatch(racks);
    }
}
