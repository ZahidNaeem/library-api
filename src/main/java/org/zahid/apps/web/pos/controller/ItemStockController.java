package org.zahid.apps.web.pos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.entity.ItemStock;
import org.zahid.apps.web.pos.mapper.ItemStockMapper;
import org.zahid.apps.web.pos.model.ItemStockModel;
import org.zahid.apps.web.pos.service.ItemStockService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/stock")
public class ItemStockController {

  private static final Logger LOG = LogManager.getLogger(ItemStockController.class);

  @Autowired
  private ItemStockService stockService;

  @Autowired
  private ItemStockMapper mapper;

  @GetMapping("all")
  public List<ItemStockModel> findAll() {
    return mapper.mapItemStocksToItemStockModels(stockService.findAll());
  }

  @GetMapping("{id}")
  public ItemStockModel findById(@PathVariable("id") final Long id) {
    return mapper.fromItemStock(stockService.findById(id));
  }

  @GetMapping("item/{itemCode}")
  public List<ItemStockModel> findByItemCode(@PathVariable("itemCode") final Long itemCode) {
    return mapper.mapItemStocksToItemStockModels(stockService.findAllByItem(itemCode));
  }

  @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ItemStockModel save(@RequestBody final ItemStockModel model) {
        /*if (null == stock.getItemStockId()) {
            stock.setItemStockId(stockService.generateID() >= (findAll().size() + 1) ? stockService.generateID() : (findAll().size() + 1));
        }*/
    final ItemStock savedStock = stockService.save(mapper.toItemStock(model));
    return mapper.fromItemStock(savedStock);
  }

  @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ItemStock> saveAll(@RequestBody final Set<ItemStockModel> stockModel) {
    final Set<ItemStock> stocks = new HashSet<>();
    stockModel.forEach(model -> {
      final ItemStock stock = mapper.toItemStock(model);
      stocks.add(stock);
    });
    return stockService.save(stocks);
  }

  @DeleteMapping("delete/{id}")
  public boolean deleteById(@PathVariable("id") final Long id) {
    if (!stockService.exists(id)) {
      throw new IllegalArgumentException("Item stock with id: " + id + " does not exist");
    } else {
      try {
        stockService.deleteById(id);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }

  @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean delete(@RequestBody final ItemStockModel model) {
    if (null == model || null == model.getItemStockId() || !stockService
        .exists(model.getItemStockId())) {
      throw new IllegalArgumentException("Item stock does not exist");
    } else {
      try {
        stockService.delete(mapper.toItemStock(model));
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }
}
