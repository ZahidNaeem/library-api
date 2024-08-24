package com.alabtaal.library.controller;

import com.alabtaal.library.dto.NavigationDtl;
import com.alabtaal.library.dto.PublisherDTO;
import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.mapper.PublisherMapper;
import com.alabtaal.library.model.PublisherModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.PublisherService;
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
@RequestMapping("publishers")
@RequiredArgsConstructor
public class PublisherController {

  private static final Logger LOG = LoggerFactory.getLogger(PublisherController.class);
  private final PublisherService publisherService;
  private final PublisherMapper mapper;
  private final int[] indx = {-1};
  private List<PublisherModel> publisherModels = new ArrayList<>();

  private static void setPublisherForBooks(final PublisherEntity publisher) {
    if (CollectionUtils.isNotEmpty(publisher.getBooks())) {
      publisher.getBooks().forEach(book -> {
        book.setPublisher(publisher);
      });
    }
  }

  private static NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static PublisherDTO getPublisherDTO(List<PublisherModel> models, int indx) {
    final NavigationDtl dtl = resetNavigation();
    if (models.isEmpty()) {
      final PublisherModel model = new PublisherModel();
      return PublisherDTO.builder()
          .publisher(model)
          .navigationDtl(dtl)
          .build();
    }
    if (indx < 0 || indx > models.size() - 1) {
      LOG.info("models.size(): {}", models.size());
      LOG.info("Index in getPublisherDTO(): {}", indx);
      throw new IndexOutOfBoundsException();
    } else {
      final PublisherModel model = models.get(indx);
      if (indx > 0) {
        dtl.setFirst(false);
      }
      if (indx < models.size() - 1) {
        dtl.setLast(false);
      }

      return PublisherDTO.builder()
          .publisher(model)
          .navigationDtl(dtl)
          .build();
    }
  }

  @PostConstruct
  public void init() {
    publisherModels = mapper.toModels(publisherService.findAll());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<PublisherModel>>> findAll() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<PublisherModel>>builder()
            .success(true)
            .message("findAll response")
            .entity(publisherModels)
            .build()
    );
  }

  @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<PublisherModel>>> searchPublisher(@RequestBody final PublisherModel model) {
    publisherModels = mapper.toModels(publisherService.searchPublisher(mapper.toEntity(model)));
    return ResponseEntity.ok(
        ApiResponse
            .<List<PublisherModel>>builder()
            .success(true)
            .message("searchPublisher response")
            .entity(publisherModels)
            .build()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<PublisherDTO>> findById(@PathVariable("id") final UUID id) {
    final PublisherModel model = mapper.toModel(publisherService.findById(id));
    indx[0] = publisherModels.indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherDTO>builder()
            .success(true)
            .message("findById response")
            .entity(getPublisherDTO(publisherModels, indx[0]))
            .build()
    );
  }

  @GetMapping("{id}/name")
  public ResponseEntity<ApiResponse<String>> getPublisherName(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getPublisherName response")
            .entity(publisherService.findById(id).getName())
            .build()
    );
  }

  @GetMapping("first")
  public ResponseEntity<ApiResponse<PublisherDTO>> first() {
    indx[0] = 0;
    LOG.info("Index in first(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherDTO>builder()
            .success(true)
            .message("first record response")
            .entity(getPublisherDTO(publisherModels, indx[0]))
            .build()
    );
  }

  @GetMapping("previous")
  public ResponseEntity<ApiResponse<PublisherDTO>> previous() {
    indx[0]--;
    LOG.info("Index in previous(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherDTO>builder()
            .success(true)
            .message("previous record response")
            .entity(getPublisherDTO(publisherModels, indx[0]))
            .build()
    );
  }

  @GetMapping("next")
  public ResponseEntity<ApiResponse<PublisherDTO>> next() {
    indx[0]++;
    LOG.info("Index in next(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherDTO>builder()
            .success(true)
            .message("next record response")
            .entity(getPublisherDTO(publisherModels, indx[0]))
            .build()
    );
  }

  @GetMapping("last")
  public ResponseEntity<ApiResponse<PublisherDTO>> last() {
    indx[0] = publisherModels.size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherDTO>builder()
            .success(true)
            .message("last record response")
            .entity(getPublisherDTO(publisherModels, indx[0]))
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<PublisherDTO>> save(@RequestBody final PublisherModel model) {
    final PublisherEntity publisher = mapper.toEntity(model);
//    Below line added, because when converted from model to PublisherEntity, there is no publisher set in book list.
    setPublisherForBooks(publisher);
    final PublisherEntity publisherSaved = publisherService.save(publisher);
    final PublisherModel savedModel = mapper.toModel(publisherSaved);
    indx[0] = this.publisherModels.indexOf(savedModel);
    LOG.info("Index in savePublisher(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherDTO>builder()
            .success(true)
            .message("Publisher saved seccessfully")
            .entity(getPublisherDTO(publisherModels, indx[0]))
            .build()
    );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<PublisherEntity>>> saveAll(
      @RequestBody final List<PublisherModel> models) {
    final List<PublisherEntity> publishers = mapper.toEntities(models);
    //    Below line added, because when converted from model to PublisherEntity, there is no publisher set in book list.
    publishers.forEach(publisher -> {
      setPublisherForBooks(publisher);
    });
    return ResponseEntity.ok(
        ApiResponse
            .<List<PublisherEntity>>builder()
            .success(true)
            .message("All publishers saved seccessfully")
            .entity(publisherService.save(new HashSet<>(publishers)))
            .build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse<PublisherDTO>> deleteById(@PathVariable("id") final UUID id) {
    if (!publisherService.exists(id)) {
      throw new IllegalArgumentException("PublisherEntity with id: " + id + " does not exist");
    } else {

        publisherService.deleteById(id);
        init();
        indx[0]--;
        LOG.info("Index in deletePublisherById(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<PublisherDTO>builder()
                .success(true)
                .message("Publisher deleted seccessfully")
                .entity(getPublisherDTO(publisherModels, indx[0]))
                .build()
        );

    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<PublisherDTO>> delete(@RequestBody final PublisherModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getId() || !publisherService.exists(model.getId())) {
      throw new IllegalArgumentException("PublisherEntity does not exist");
    } else {

        publisherService.delete(mapper.toEntity(model));
        init();
        indx[0]--;
        LOG.info("Index in deletePublisher(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<PublisherDTO>builder()
                .success(true)
                .message("Publisher deleted seccessfully")
                .entity(getPublisherDTO(publisherModels, indx[0]))
                .build()
        );

    }
  }
}
