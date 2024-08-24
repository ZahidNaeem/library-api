package com.alabtaal.library.controller;

import com.alabtaal.library.dto.NavigationDtl;
import com.alabtaal.library.dto.ReaderDTO;
import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.mapper.ReaderMapper;
import com.alabtaal.library.model.ReaderModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.ReaderService;
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
@RequestMapping("readers")
@RequiredArgsConstructor
public class ReaderController {

  private static final Logger LOG = LoggerFactory.getLogger(ReaderController.class);
  private final ReaderService readerService;
  private final ReaderMapper mapper;
  private final int[] indx = {-1};
  private List<ReaderModel> readerModels = new ArrayList<>();

  private static void setReaderForBookTransHeaders(final ReaderEntity reader) {
    if (CollectionUtils.isNotEmpty(reader.getBookTransHeaders())) {
      reader.getBookTransHeaders().forEach(bookTransHeader -> {
        bookTransHeader.setReader(reader);
      });
    }
  }

  private static NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static ReaderDTO getReaderDTO(List<ReaderModel> models, int indx) {
    final NavigationDtl dtl = resetNavigation();
    if (models.isEmpty()) {
      final ReaderModel model = new ReaderModel();
      return ReaderDTO.builder()
          .reader(model)
          .navigationDtl(dtl)
          .build();
    }
    if (indx < 0 || indx > models.size() - 1) {
      LOG.info("models.size(): {}", models.size());
      LOG.info("Index in getReaderDTO(): {}", indx);
      throw new IndexOutOfBoundsException();
    } else {
      final ReaderModel model = models.get(indx);
      if (indx > 0) {
        dtl.setFirst(false);
      }
      if (indx < models.size() - 1) {
        dtl.setLast(false);
      }

      return ReaderDTO.builder()
          .reader(model)
          .navigationDtl(dtl)
          .build();
    }
  }

  @PostConstruct
  public void init() {
    readerModels = mapper.toModels(readerService.findAll());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ReaderModel>>> findAll() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<ReaderModel>>builder()
            .success(true)
            .message("findAll response")
            .entity(readerModels)
            .build()
    );
  }

  @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ReaderModel>>> searchReader(@RequestBody final ReaderModel model) {
    readerModels = mapper.toModels(readerService.searchReader(mapper.toEntity(model)));
    return ResponseEntity.ok(
        ApiResponse
            .<List<ReaderModel>>builder()
            .success(true)
            .message("searchReader response")
            .entity(readerModels)
            .build()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<ReaderDTO>> findById(@PathVariable("id") final UUID id) {
    final ReaderModel model = mapper.toModel(readerService.findById(id));
    indx[0] = readerModels.indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderDTO>builder()
            .success(true)
            .message("findById response")
            .entity(getReaderDTO(readerModels, indx[0]))
            .build()
    );
  }

  @GetMapping("{id}/name")
  public ResponseEntity<ApiResponse<String>> getReaderName(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getReaderName response")
            .entity(readerService.findById(id).getName())
            .build()
    );
  }

  @GetMapping("first")
  public ResponseEntity<ApiResponse<ReaderDTO>> first() {
    indx[0] = 0;
    LOG.info("Index in first(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderDTO>builder()
            .success(true)
            .message("first record response")
            .entity(getReaderDTO(readerModels, indx[0]))
            .build()
    );
  }

  @GetMapping("previous")
  public ResponseEntity<ApiResponse<ReaderDTO>> previous() {
    indx[0]--;
    LOG.info("Index in previous(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderDTO>builder()
            .success(true)
            .message("previous record response")
            .entity(getReaderDTO(readerModels, indx[0]))
            .build()
    );
  }

  @GetMapping("next")
  public ResponseEntity<ApiResponse<ReaderDTO>> next() {
    indx[0]++;
    LOG.info("Index in next(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderDTO>builder()
            .success(true)
            .message("next record response")
            .entity(getReaderDTO(readerModels, indx[0]))
            .build()
    );
  }

  @GetMapping("last")
  public ResponseEntity<ApiResponse<ReaderDTO>> last() {
    indx[0] = readerModels.size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderDTO>builder()
            .success(true)
            .message("last record response")
            .entity(getReaderDTO(readerModels, indx[0]))
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ReaderDTO>> save(@RequestBody final ReaderModel model) {
    final ReaderEntity reader = mapper.toEntity(model);
//    Below line added, because when converted from model to ReaderEntity, there is no reader set in bookTransHeader list.
    setReaderForBookTransHeaders(reader);
    final ReaderEntity readerSaved = readerService.save(reader);
    final ReaderModel savedModel = mapper.toModel(readerSaved);
    init();
    indx[0] = this.readerModels.indexOf(savedModel);
    LOG.info("Index in saveReader(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderDTO>builder()
            .success(true)
            .message("first record response")
            .entity(getReaderDTO(readerModels, indx[0]))
            .build()
    );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ReaderEntity>>> saveAll(
      @RequestBody final List<ReaderModel> models) {
    final List<ReaderEntity> readers = mapper.toEntities(models);
    //    Below line added, because when converted from model to ReaderEntity, there is no reader set in bookTransHeader list.
    readers.forEach(reader -> {
      setReaderForBookTransHeaders(reader);
    });
    return ResponseEntity.ok(
        ApiResponse
            .<List<ReaderEntity>>builder()
            .success(true)
            .message("All readers saved seccessfully")
            .entity(readerService.save(new HashSet<>(readers)))
            .build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse<ReaderDTO>> deleteById(@PathVariable("id") final UUID id) {
    if (!readerService.exists(id)) {
      throw new IllegalArgumentException("ReaderEntity with id: " + id + " does not exist");
    } else {
        readerService.deleteById(id);
        init();
        indx[0]--;
        LOG.info("Index in deleteReaderById(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<ReaderDTO>builder()
                .success(true)
                .message("first record response")
                .entity(getReaderDTO(readerModels, indx[0]))
                .build()
        );
    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ReaderDTO>> delete(@RequestBody final ReaderModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getId() || !readerService.exists(model.getId())) {
      throw new IllegalArgumentException("ReaderEntity does not exist");
    } else {
        readerService.delete(mapper.toEntity(model));
        init();
        indx[0]--;
        LOG.info("Index in deleteReader(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<ReaderDTO>builder()
                .success(true)
                .message("first record response")
                .entity(getReaderDTO(readerModels, indx[0]))
                .build()
        );
    }
  }
}
