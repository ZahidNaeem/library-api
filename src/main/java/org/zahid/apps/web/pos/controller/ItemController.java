package org.zahid.apps.web.pos.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.dto.ItemDTO;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.entity.NavigationDtl;
import org.zahid.apps.web.pos.mapper.ItemMapper;
import org.zahid.apps.web.pos.model.ItemModel;
import org.zahid.apps.web.pos.service.ItemService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/item")
public class ItemController {

  private static final Logger LOG = LogManager.getLogger(ItemController.class);
  @Autowired
  private ItemService itemService;

  @Autowired
  private ItemMapper mapper;

  private final int[] indx = {-1};

  private static void setItemForStock(final Item item) {
    if (CollectionUtils.isNotEmpty(item.getItemStocks())) {
      item.getItemStocks().forEach(stock -> {
        stock.setItem(item);
      });
    }
  }

  @GetMapping("all")
  public List<ItemModel> findAll() {
    return mapper.mapItemsToItemModels(itemService.findAll());
  }

  @GetMapping("{id}")
  public ItemDTO findById(@PathVariable("id") final Long id) {
    final ItemModel model = mapper.fromItem(itemService.findById(id));
    indx[0] = findAll().indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return getItemDTO(findAll(), indx[0]);
  }

  @GetMapping("{id}/desc")
  public String getItemDesc(@PathVariable("id") final Long id) {
    return itemService.getItemDesc(id);
  }

  @GetMapping("first")
  public ItemDTO first() {
    indx[0] = 0;
    LOG.info("Index in first(): {}", indx[0]);
    return getItemDTO(findAll(), indx[0]);
  }

  @GetMapping("previous")
  public ItemDTO previous() {
    indx[0]--;
    LOG.info("Index in previous(): {}", indx[0]);
    return getItemDTO(findAll(), indx[0]);
  }

  @GetMapping("next")
  public ItemDTO next() {
    indx[0]++;
    LOG.info("Index in next(): {}", indx[0]);
    return getItemDTO(findAll(), indx[0]);
  }

  @GetMapping("last")
  public ItemDTO last() {
    indx[0] = findAll().size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return getItemDTO(findAll(), indx[0]);
  }

  @GetMapping("cats")
  public Set<String> findAllCategories() {
    return itemService.getItemCategories();
  }

  @GetMapping("uoms")
  public Set<String> findAllUOMs() {
    return itemService.getItemUOM();
  }

  @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ItemDTO save(@RequestBody final ItemModel model) {
        /*if (null == model.getItemCode()) {
            model.setItemCode(itemService.generateID() >= (findAll().size() + 1) ? itemService.generateID() : (findAll().size() + 1));
        }*/
    final Item item = mapper.toItem(model);
//    Below line added, because when converted from model to Item, there is no item set in itemStock list.
    setItemForStock(item);
    final Item itemSaved = itemService.save(item);
    final ItemModel savedModel = mapper.fromItem(itemSaved);
    indx[0] = this.findAll().indexOf(savedModel);
    LOG.info("Index in saveItem(): {}", indx[0]);
    return getItemDTO(findAll(), indx[0]);
  }

  @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Item> saveAll(@RequestBody final List<ItemModel> models) {
    final List<Item> items = mapper.mapItemModelsToItems(models);
    //    Below line added, because when converted from model to Item, there is no item set in itemStock list.
    items.forEach(item -> {
      setItemForStock(item);
    });
    return itemService.save(new HashSet<>(items));
  }

  @DeleteMapping("/delete/{id}")
  public ItemDTO deleteById(@PathVariable("id") final Long id) {
    if (!itemService.exists(id)) {
      throw new IllegalArgumentException("Item with id: " + id + " does not exist");
    } else {
      try {
        itemService.deleteById(id);
        indx[0]--;
        LOG.info("Index in deleteItemById(): {}", indx[0]);
        return getItemDTO(findAll(), indx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ItemDTO delete(@RequestBody final ItemModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getItemCode() || !itemService.exists(model.getItemCode())) {
      throw new IllegalArgumentException("Item does not exist");
    } else {
      try {
        itemService.delete(mapper.toItem(model));
        indx[0]--;
        LOG.info("Index in deleteItem(): {}", indx[0]);
        return getItemDTO(findAll(), indx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  private static final NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static final ItemDTO getItemDTO(List<ItemModel> models, int indx) {
    if (indx < 0 || indx > models.size() - 1) {
      LOG.info("Index in getItemDTO(): {}", indx);
      throw new IndexOutOfBoundsException();
    } else {
      final NavigationDtl dtl = resetNavigation();
      final ItemModel model = models.get(indx);
      final ItemDTO itemDTO = new ItemDTO();
      itemDTO.setItem(model);
      if (indx > 0) {
        dtl.setFirst(false);
      }
      if (indx < models.size() - 1) {
        dtl.setLast(false);
      }
      itemDTO.setNavigationDtl(dtl);
      return itemDTO;
    }
  }
}
