package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.exception.ChildRecordFoundException;
import org.zahid.apps.web.library.repo.ShelfRepo;
import org.zahid.apps.web.library.service.ShelfService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements ShelfService {

    private static final Logger LOG = LogManager.getLogger(ShelfServiceImpl.class);

    private final ShelfRepo shelfRepo;

    @Override
    public List<ShelfEntity> findAll() {
        return shelfRepo.findAllByOrderByShelfIdAsc();
    }

    @Override
    public List<ShelfEntity> searchShelf(ShelfEntity shelfEntity) {
        return shelfRepo.searchShelf(shelfEntity);
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
    public void deleteById(Long id) {
        try {
            shelfRepo.deleteById(id);
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
