package com.alabtaal.library.service;

import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.repo.ShelfRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements ShelfService {

  private static final Logger LOG = LoggerFactory.getLogger(ShelfServiceImpl.class);

  private final ShelfRepo shelfRepo;

  @Override
  public List<ShelfEntity> findAll() {
    return shelfRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<ShelfEntity> searchShelf(ShelfEntity shelfEntity) {
    return shelfRepo.searchShelf(shelfEntity);
  }

  @Override
  public ShelfEntity findById(UUID id) {
    return shelfRepo.findById(id)
        .orElse(new ShelfEntity());
  }

  @Override
  public boolean exists(UUID id) {
    return shelfRepo.existsById(id);
  }

  @Override
  public ShelfEntity save(ShelfEntity shelf) {
    return shelfRepo.saveAndFlush(shelf);
  }

  @Override
  public List<ShelfEntity> save(Set<ShelfEntity> shelves) {
    return shelfRepo.saveAll(shelves);
  }

  @Override
  public void delete(ShelfEntity shelf) {
    shelfRepo.delete(shelf);
  }

  @Override
  public void delete(Set<ShelfEntity> shelves) {
    shelfRepo.deleteAll(shelves);
  }

  @Override
  public void deleteById(UUID id) {
    shelfRepo.deleteById(id);
  }

  @Override
  public void deleteAll() {
    shelfRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() {
    shelfRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<ShelfEntity> shelves) {
    shelfRepo.deleteInBatch(shelves);
  }
}
