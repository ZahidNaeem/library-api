package com.alabtaal.library.service;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.repo.RackRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RackServiceImpl implements RackService {

  public static final Logger LOG = LogManager.getLogger(RackServiceImpl.class);

  private final RackRepo rackRepo;

  @Override
  public List<RackEntity> findAll() {
    return rackRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<RackEntity> findAllByShelf(final ShelfEntity shelf) {
    return rackRepo.findAllByShelfOrderByIdAsc(shelf);
  }

  @Override
  public RackEntity findById(UUID id) {
    return rackRepo.findById(id)
        .orElse(new RackEntity());
  }

  @Override
  public boolean exists(UUID id) {
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
  public void deleteById(UUID id) {
    rackRepo.deleteById(id);
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
