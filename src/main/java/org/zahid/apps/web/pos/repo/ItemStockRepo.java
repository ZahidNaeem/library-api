package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.pos.entity.ItemStock;

import java.util.List;

@Repository
public interface ItemStockRepo extends JpaRepository<ItemStock, Long> {

    List<ItemStock> findAllByItem(Long itemCode);

    Long generateID();
}
