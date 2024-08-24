package com.alabtaal.library.controller;

import com.alabtaal.library.dto.NavigationDtl;
import com.alabtaal.library.dto.ResearcherDTO;
import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.mapper.ResearcherMapper;
import com.alabtaal.library.model.ResearcherModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.ResearcherService;
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
@RequestMapping("researchers")
@RequiredArgsConstructor
public class ResearcherController {

  private static final Logger LOG = LoggerFactory.getLogger(ResearcherController.class);
  private final ResearcherService researcherService;
  private final ResearcherMapper mapper;
  private final int[] index = {-1};
  private List<ResearcherModel> researcherModels = new ArrayList<>();

  private static void setResearcherForBooks(final ResearcherEntity researcher) {
    if (CollectionUtils.isNotEmpty(researcher.getBooks())) {
      researcher.getBooks().forEach(book -> book.setResearcher(researcher));
    }
  }

  private static NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static ResearcherDTO getResearcherDTO(List<ResearcherModel> models, int index) {
    final NavigationDtl dtl = resetNavigation();
    if (models.isEmpty()) {
      final ResearcherModel model = new ResearcherModel();
      return ResearcherDTO.builder()
          .researcher(model)
          .navigationDtl(dtl)
          .build();
    }
    if (index < 0 || index > models.size() - 1) {
      LOG.info("models.size(): {}", models.size());
      LOG.info("Index in getResearcherDTO(): {}", index);
      throw new IndexOutOfBoundsException();
    } else {
      final ResearcherModel model = models.get(index);
      if (index > 0) {
        dtl.setFirst(false);
      }
      if (index < models.size() - 1) {
        dtl.setLast(false);
      }

      return ResearcherDTO.builder()
          .researcher(model)
          .navigationDtl(dtl)
          .build();
    }
  }

  @PostConstruct
  public void init() {
    researcherModels = mapper.toModels(researcherService.findAll());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ResearcherModel>>> findAll() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<ResearcherModel>>builder()
            .success(true)
            .message("findAll response")
            .entity(researcherModels)
            .build()
    );
  }

  @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ResearcherModel>>> searchResearcher(@RequestBody final ResearcherModel model) {
    researcherModels = mapper.toModels(researcherService.searchResearcher(mapper.toEntity(model)));
    return ResponseEntity.ok(
        ApiResponse
            .<List<ResearcherModel>>builder()
            .success(true)
            .message("searchResearcher response")
            .entity(researcherModels)
            .build()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<ResearcherDTO>> findById(@PathVariable("id") final UUID id) {
    final ResearcherModel model = mapper.toModel(researcherService.findById(id));
    index[0] = researcherModels.indexOf(model);
    LOG.info("Index in findById(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherDTO>builder()
            .success(true)
            .message("findById response")
            .entity(getResearcherDTO(researcherModels, index[0]))
            .build()
    );
  }

  @GetMapping("{id}/name")
  public ResponseEntity<ApiResponse<String>> getResearcherName(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getResearcherName response")
            .entity(researcherService.findById(id).getName())
            .build()
    );
  }

  @GetMapping("first")
  public ResponseEntity<ApiResponse<ResearcherDTO>> first() {
    index[0] = 0;
    LOG.info("Index in first(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherDTO>builder()
            .success(true)
            .message("first record response")
            .entity(getResearcherDTO(researcherModels, index[0]))
            .build()
    );
  }

  @GetMapping("previous")
  public ResponseEntity<ApiResponse<ResearcherDTO>> previous() {
    index[0]--;
    LOG.info("Index in previous(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherDTO>builder()
            .success(true)
            .message("previous record response")
            .entity(getResearcherDTO(researcherModels, index[0]))
            .build()
    );
  }

  @GetMapping("next")
  public ResponseEntity<ApiResponse<ResearcherDTO>> next() {
    index[0]++;
    LOG.info("Index in next(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherDTO>builder()
            .success(true)
            .message("next record response")
            .entity(getResearcherDTO(researcherModels, index[0]))
            .build()
    );
  }

  @GetMapping("last")
  public ResponseEntity<ApiResponse<ResearcherDTO>> last() {
    index[0] = researcherModels.size() - 1;
    LOG.info("Index in last(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherDTO>builder()
            .success(true)
            .message("last record response")
            .entity(getResearcherDTO(researcherModels, index[0]))
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ResearcherDTO>> save(@RequestBody final ResearcherModel model) {
    final ResearcherEntity researcher = mapper.toEntity(model);
//    Below line added, because when converted from model to ResearcherEntity, there is no researcher set in book list.
    setResearcherForBooks(researcher);
    final ResearcherEntity researcherSaved = researcherService.save(researcher);
    final ResearcherModel savedModel = mapper.toModel(researcherSaved);
    init();
    index[0] = this.researcherModels.indexOf(savedModel);
    LOG.info("Index in saveResearcher(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherDTO>builder()
            .success(true)
            .message("Researcher saved seccessfully")
            .entity(getResearcherDTO(researcherModels, index[0]))
            .build()
    );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ResearcherEntity>>> saveAll(@RequestBody final List<ResearcherModel> models) {
    final List<ResearcherEntity> researchers = mapper.toEntities(models);
    //    Below line added, because when converted from model to ResearcherEntity, there is no researcher set in book list.
    researchers.forEach(ResearcherController::setResearcherForBooks);
    return ResponseEntity.ok(
        ApiResponse
            .<List<ResearcherEntity>>builder()
            .success(true)
            .message("All researchers saved seccessfully")
            .entity(researcherService.save(new HashSet<>(researchers)))
            .build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse<ResearcherDTO>> deleteById(@PathVariable("id") final UUID id) {
    if (!researcherService.exists(id)) {
      throw new IllegalArgumentException("ResearcherEntity with id: " + id + " does not exist");
    } else {
      researcherService.deleteById(id);
      init();
      index[0]--;
      LOG.info("Index in deleteResearcherById(): {}", index[0]);
      return ResponseEntity.ok(
          ApiResponse
              .<ResearcherDTO>builder()
              .success(true)
              .message("Researcher deleted seccessfully")
              .entity(getResearcherDTO(researcherModels, index[0]))
              .build()
      );
    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ResearcherDTO>> delete(@RequestBody final ResearcherModel model) {
    LOG.info("Index: {}", index);
    if (null == model || null == model.getId() || !researcherService.exists(model.getId())) {
      throw new IllegalArgumentException("ResearcherEntity does not exist");
    } else {
      researcherService.delete(mapper.toEntity(model));
      init();
      index[0]--;
      LOG.info("Index in deleteResearcher(): {}", index[0]);
      return ResponseEntity.ok(
          ApiResponse
              .<ResearcherDTO>builder()
              .success(true)
              .message("Researcher deleted seccessfully")
              .entity(getResearcherDTO(researcherModels, index[0]))
              .build()
      );
    }
  }
}
