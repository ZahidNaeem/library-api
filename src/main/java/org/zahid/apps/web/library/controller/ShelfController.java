package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.ShelfDTO;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.ShelfMapper;
import org.zahid.apps.web.library.model.ShelfModel;
import org.zahid.apps.web.library.service.ShelfService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("shelfs")
public class ShelfController {

    private static final Logger LOG = LogManager.getLogger(ShelfController.class);
    @Autowired
    private ShelfService shelfService;

    @Autowired
    private ShelfMapper mapper;

    private final int[] indx = {-1};

    private static void setShelfForBooks(final ShelfEntity shelf) {
        if (CollectionUtils.isNotEmpty(shelf.getBooks())) {
            shelf.getBooks().forEach(book -> {
                book.setShelf(shelf);
            });
        }
    }

    @GetMapping
    public ResponseEntity<List<ShelfModel>> findAll() {
        return ResponseEntity.ok(mapper.mapShelfEntitiesToShelfModels(shelfService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ShelfDTO> findById(@PathVariable("id") final Long id) {
        final ShelfModel model = mapper.fromShelf(shelfService.findById(id));
        indx[0] = findAll().getBody().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("{id}/name")
    public ResponseEntity<String> getShelfName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(shelfService.findById(id).getShelfName());
    }

    @GetMapping("first")
    public ResponseEntity<ShelfDTO> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("previous")
    public ResponseEntity<ShelfDTO> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("next")
    public ResponseEntity<ShelfDTO> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("last")
    public ResponseEntity<ShelfDTO> last() {
        indx[0] = findAll().getBody().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShelfDTO> save(@RequestBody final ShelfModel model) {
        final ShelfEntity shelf = mapper.toShelf(model);
//    Below line added, because when converted from model to ShelfEntity, there is no shelf set in book list.
        setShelfForBooks(shelf);
        final ShelfEntity shelfSaved = shelfService.save(shelf);
        final ShelfModel savedModel = mapper.fromShelf(shelfSaved);
        indx[0] = this.findAll().getBody().indexOf(savedModel);
        LOG.info("Index in saveShelf(): {}", indx[0]);
        return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShelfEntity>> saveAll(@RequestBody final List<ShelfModel> models) {
        final List<ShelfEntity> shelfs = mapper.mapShelfModelsToShelfs(models);
        //    Below line added, because when converted from model to ShelfEntity, there is no shelf set in book list.
        shelfs.forEach(shelf -> {
            setShelfForBooks(shelf);
        });
        return ResponseEntity.ok(shelfService.save(new HashSet<>(shelfs)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ShelfDTO> deleteById(@PathVariable("id") final Long id) {
        if (!shelfService.exists(id)) {
            throw new IllegalArgumentException("ShelfEntity with id: " + id + " does not exist");
        } else {
            try {
                shelfService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteShelfById(): {}", indx[0]);
                return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShelfDTO> delete(@RequestBody final ShelfModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getShelfId() || !shelfService.exists(model.getShelfId())) {
            throw new IllegalArgumentException("ShelfEntity does not exist");
        } else {
            try {
                shelfService.delete(mapper.toShelf(model));
                indx[0]--;
                LOG.info("Index in deleteShelf(): {}", indx[0]);
                return ResponseEntity.ok(getShelfDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private static final NavigationDtl resetNavigation() {
        NavigationDtl dtl = new NavigationDtl();
        dtl.setFirst(true);
        dtl.setLast(true);
        return dtl;
    }

    private static final ShelfDTO getShelfDTO(List<ShelfModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
            final ShelfModel model = new ShelfModel();
            return ShelfDTO.builder()
                    .shelf(model)
                    .navigationDtl(dtl)
                    .build();
        }
        if (indx < 0 || indx > models.size() - 1) {
            LOG.info("models.size(): {}", models.size());
            LOG.info("Index in getShelfDTO(): {}", indx);
            throw new IndexOutOfBoundsException();
        } else {
            final ShelfModel model = models.get(indx);
            if (indx > 0) {
                dtl.setFirst(false);
            }
            if (indx < models.size() - 1) {
                dtl.setLast(false);
            }

            return ShelfDTO.builder()
                    .shelf(model)
                    .navigationDtl(dtl)
                    .build();
        }
    }
}
