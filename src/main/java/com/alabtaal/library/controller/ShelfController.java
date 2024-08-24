package com.alabtaal.library.controller;

import com.alabtaal.library.dto.NavigationDtl;
import com.alabtaal.library.dto.ShelfDTO;
import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.mapper.ShelfMapper;
import com.alabtaal.library.model.ShelfModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.ShelfService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shelves")
@RequiredArgsConstructor
public class ShelfController {

  private static final Logger LOG = LoggerFactory.getLogger(ShelfController.class);
  private final ShelfService shelfService;
  private final ShelfMapper mapper;
  private final int[] indx = {-1};
  private List<ShelfModel> shelfModels = new ArrayList<>();

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

  private static NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static ShelfDTO getShelfDTO(List<ShelfModel> models, int indx) {
    final NavigationDtl dtl = resetNavigation();
    if (models.isEmpty()) {
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

  @PostConstruct
  public void init() {
    shelfModels = mapper.toModels(shelfService.findAll());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ShelfModel>>> findAll() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<ShelfModel>>builder()
            .success(true)
            .message("findAll response")
            .entity(shelfModels)
            .build()
    );
  }

  @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ShelfModel>>> searchShelf(@RequestBody final ShelfModel model) {
    shelfModels = mapper.toModels(shelfService.searchShelf(mapper.toEntity(model)));
    return ResponseEntity.ok(
        ApiResponse
            .<List<ShelfModel>>builder()
            .success(true)
            .message("searchShelf response")
            .entity(shelfModels)
            .build()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<ShelfDTO>> findById(@PathVariable("id") final UUID id) {
    final ShelfModel model = mapper.toModel(shelfService.findById(id));
    indx[0] = shelfModels.indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ShelfDTO>builder()
            .success(true)
            .message("findById response")
            .entity(getShelfDTO(shelfModels, indx[0]))
            .build()
    );
  }

  @GetMapping("{id}/name")
  public ResponseEntity<ApiResponse<String>> getShelfName(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getShelfName response")
            .entity(shelfService.findById(id).getName())
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
            .entity(getShelfDTO(shelfModels, indx[0]))
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
            .entity(getShelfDTO(shelfModels, indx[0]))
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
            .entity(getShelfDTO(shelfModels, indx[0]))
            .build()
    );
  }

  @GetMapping("last")
  public ResponseEntity<ApiResponse<ShelfDTO>> last() {
    indx[0] = shelfModels.size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ShelfDTO>builder()
            .success(true)
            .message("last response")
            .entity(getShelfDTO(shelfModels, indx[0]))
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ShelfDTO>> save(@RequestBody final ShelfModel model) {
    final ShelfEntity shelf = mapper.toEntity(model);
//    Below line added, because when converted from model to ShelfEntity, there is no shelf set in book list.
    setShelfForBooks(shelf);
    setShelfForRacks(shelf);
    final ShelfEntity shelfSaved = shelfService.save(shelf);
    final ShelfModel savedModel = mapper.toModel(shelfSaved);
    init();
    indx[0] = this.shelfModels.indexOf(savedModel);
    LOG.info("Index in saveShelf(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ShelfDTO>builder()
            .success(true)
            .message("Shelf saved successfully")
            .entity(getShelfDTO(shelfModels, indx[0]))
            .build()
    );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ShelfEntity>>> saveAll(@RequestBody final List<ShelfModel> models) {
    final List<ShelfEntity> shelves = mapper.toEntities(models);
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
  public ResponseEntity<ApiResponse<ShelfDTO>> deleteById(@PathVariable("id") final UUID id) {
    if (!shelfService.exists(id)) {
      throw new IllegalArgumentException("ShelfEntity with id: " + id + " does not exist");
    } else {

        shelfService.deleteById(id);
        init();
        indx[0]--;
        LOG.info("Index in deleteShelfById(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<ShelfDTO>builder()
                .success(true)
                .message("Shelf deleted successfully")
                .entity(getShelfDTO(shelfModels, indx[0]))
                .build()
        );

    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ShelfDTO>> delete(@RequestBody final ShelfModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getId() || !shelfService.exists(model.getId())) {
      throw new IllegalArgumentException("ShelfEntity does not exist");
    } else {

        shelfService.delete(mapper.toEntity(model));
        init();
        indx[0]--;
        LOG.info("Index in deleteShelf(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<ShelfDTO>builder()
                .success(true)
                .message("Shelf deleted successfully")
                .entity(getShelfDTO(shelfModels, indx[0]))
                .build()
        );

    }
  }
}
