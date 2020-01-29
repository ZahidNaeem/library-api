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
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.mapper.ShelfMapper;
import org.zahid.apps.web.library.model.ShelfModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.ShelfService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("shelves")
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

    private static void setShelfForRacks(final ShelfEntity shelf) {
        if (CollectionUtils.isNotEmpty(shelf.getRacks())) {
            shelf.getRacks().forEach(rack -> {
                rack.setShelf(shelf);
            });
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShelfModel>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<ShelfModel>>builder()
                        .success(true)
                        .message("findAll response")
                        .entity(mapper.toShelfModels(shelfService.findAll()))
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ShelfDTO>> findById(@PathVariable("id") final Long id) {
        final ShelfModel model = mapper.toShelfModel(shelfService.findById(id));
        indx[0] = findAll().getBody().getEntity().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("findById response")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
    }

    @GetMapping("{id}/name")
    public ResponseEntity<ApiResponse<String>> getShelfName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getShelfName response")
                        .entity(shelfService.findById(id).getShelfName())
                        .build()
        );
    }

    @GetMapping("first")
    public ResponseEntity<ApiResponse<ShelfDTO>> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("first response")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
    }

    @GetMapping("previous")
    public ResponseEntity<ApiResponse<ShelfDTO>> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("previous response")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
    }

    @GetMapping("next")
    public ResponseEntity<ApiResponse<ShelfDTO>> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("next response")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
    }

    @GetMapping("last")
    public ResponseEntity<ApiResponse<ShelfDTO>> last() {
        indx[0] = findAll().getBody().getEntity().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("last response")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ShelfDTO>> save(@RequestBody final ShelfModel model) {
        final ShelfEntity shelf = mapper.toShelfEntity(model);
//    Below line added, because when converted from model to ShelfEntity, there is no shelf set in book list.
        setShelfForBooks(shelf);
        setShelfForRacks(shelf);
        final ShelfEntity shelfSaved = shelfService.save(shelf);
        final ShelfModel savedModel = mapper.toShelfModel(shelfSaved);
        indx[0] = this.findAll().getBody().getEntity().indexOf(savedModel);
        LOG.info("Index in saveShelf(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("Shelf saved successfully")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ShelfEntity>>> saveAll(@RequestBody final List<ShelfModel> models) {
        final List<ShelfEntity> shelves = mapper.toShelfEntities(models);
        //    Below line added, because when converted from model to ShelfEntity, there is no shelf set in book list.
        shelves.forEach(shelf -> {
            setShelfForBooks(shelf);
            setShelfForRacks(shelf);
        });
        return ResponseEntity.ok(
                ApiResponse
                        .<List<ShelfEntity>>builder()
                        .success(true)
                        .message("All shelves saved seccessfully")
                        .entity(shelfService.save(new HashSet<>(shelves)))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<ShelfDTO>> deleteById(@PathVariable("id") final Long id) {
        if (!shelfService.exists(id)) {
            throw new IllegalArgumentException("ShelfEntity with id: " + id + " does not exist");
        } else {
            try {
                shelfService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteShelfById(): {}", indx[0]);
                return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("Shelf deleted successfully")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(ApiResponse
                        .<ShelfDTO>builder()
                        .success(false)
                        .message(e.getMessage())
                        .entity(null)
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ShelfDTO>> delete(@RequestBody final ShelfModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getShelfId() || !shelfService.exists(model.getShelfId())) {
            throw new IllegalArgumentException("ShelfEntity does not exist");
        } else {
            try {
                shelfService.delete(mapper.toShelfEntity(model));
                indx[0]--;
                LOG.info("Index in deleteShelf(): {}", indx[0]);
                return ResponseEntity.ok(
                ApiResponse
                        .<ShelfDTO>builder()
                        .success(true)
                        .message("Shelf deleted successfully")
                        .entity(getShelfDTO(findAll().getBody().getEntity(), indx[0]))
                        .build()
        );
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(ApiResponse
                        .<ShelfDTO>builder()
                        .success(false)
                        .message(e.getMessage())
                        .entity(null)
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
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
