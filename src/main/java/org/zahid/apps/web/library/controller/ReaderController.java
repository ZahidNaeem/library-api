package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.ReaderDTO;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.ReaderMapper;
import org.zahid.apps.web.library.model.ReaderModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.ReaderService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("readers")
public class ReaderController {

  private static final Logger LOG = LogManager.getLogger(ReaderController.class);
  private List<ReaderModel> readerModels = new ArrayList<>();

  @PostConstruct
  public void init() {
    readerModels = mapper.toReaderModels(readerService.findAll());
  }

  @Autowired
  private ReaderService readerService;

  @Autowired
  private ReaderMapper mapper;

  private final int[] indx = {-1};

  private static void setReaderForBookTransHeaders(final ReaderEntity reader) {
    if (CollectionUtils.isNotEmpty(reader.getBookTransHeaders())) {
      reader.getBookTransHeaders().forEach(bookTransHeader -> {
        bookTransHeader.setReader(reader);
      });
    }
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
    readerModels = Miscellaneous.searchReader(model);
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
  public ResponseEntity<ApiResponse<ReaderDTO>> findById(@PathVariable("id") final Long id) {
    final ReaderModel model = mapper.toReaderModel(readerService.findById(id));
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
  public ResponseEntity<ApiResponse<String>> getReaderName(@PathVariable("id") final Long id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getReaderName response")
            .entity(readerService.findById(id).getReaderName())
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
    final ReaderEntity reader = mapper.toReaderEntity(model);
//    Below line added, because when converted from model to ReaderEntity, there is no reader set in bookTransHeader list.
    setReaderForBookTransHeaders(reader);
    final ReaderEntity readerSaved = readerService.save(reader);
    final ReaderModel savedModel = mapper.toReaderModel(readerSaved);
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
    final List<ReaderEntity> readers = mapper.toReaderEntities(models);
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
  public ResponseEntity<ApiResponse<ReaderDTO>> deleteById(@PathVariable("id") final Long id) {
    if (!readerService.exists(id)) {
      throw new IllegalArgumentException("ReaderEntity with id: " + id + " does not exist");
    } else {
      try {
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
      } catch (Exception e) {
        e.printStackTrace();
        throw new InternalServerErrorException(e.getMessage());
      }
    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ReaderDTO>> delete(@RequestBody final ReaderModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getReaderId() || !readerService
        .exists(model.getReaderId())) {
      throw new IllegalArgumentException("ReaderEntity does not exist");
    } else {
      try {
        readerService.delete(mapper.toReaderEntity(model));
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
      } catch (Exception e) {
        e.printStackTrace();
        throw new InternalServerErrorException(e.getMessage());
      }
    }
  }

  private static final NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static final ReaderDTO getReaderDTO(List<ReaderModel> models, int indx) {
    final NavigationDtl dtl = resetNavigation();
    if (models.size() < 1) {
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
}
