package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.exception.ShelfNotFoundException;
import org.zahid.apps.web.library.repo.ShelfRepo;
import org.zahid.apps.web.library.service.ShelfService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    private ShelfRepo shelfRepo;

    @Override
    public List<ShelfEntity> findAll() {
        return shelfRepo.findAll();
    }

    @Override
    public ShelfEntity findById(Long id) {
        return Optional.ofNullable(shelfRepo.findById(id))
                .map(shelf -> shelf.get())
                .orElseThrow(() -> new ShelfNotFoundException("Shelf with id: " + id + " not found"));
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
    public List<ShelfEntity> save(Set<ShelfEntity> shelfs) {
        return shelfRepo.saveAll(shelfs);
    }

    @Override
    public void delete(ShelfEntity shelf) {
        shelfRepo.delete(shelf);
    }

    @Override
    public void delete(Set<ShelfEntity> shelfs) {
        shelfRepo.deleteAll(shelfs);
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
    public void deleteInBatch(Set<ShelfEntity> shelfs) {
        shelfRepo.deleteInBatch(shelfs);
    }
}
