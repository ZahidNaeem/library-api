package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.repo.ShelfRepo;
import org.zahid.apps.web.library.service.ShelfService;

import java.util.List;
import java.util.Set;

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    private ShelfRepo shelfRepo;

    @Override
    public List<ShelfEntity> findAll() {
        return shelfRepo.findAllByOrderByShelfIdAsc();
    }

    @Override
    public ShelfEntity findById(Long id) {
        return shelfRepo.findById(id)
                .orElse(new ShelfEntity());
    }

    @Override
    public boolean exists(Long id) {
        return shelfRepo.existsById(id);
    }

    @Override
    public ShelfEntity save(ShelfEntity shelf) {
        return shelfRepo.save(shelf);
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
    public void deleteById(Long id) {
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
