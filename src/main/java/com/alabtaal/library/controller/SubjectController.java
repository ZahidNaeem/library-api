package com.alabtaal.library.controller;

import com.alabtaal.library.dto.NavigationDtl;
import com.alabtaal.library.dto.SubjectDTO;
import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.mapper.SubjectMapper;
import com.alabtaal.library.model.SubjectModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.SubjectService;
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
@RequestMapping("subjects")
@RequiredArgsConstructor
public class SubjectController {

  private static final Logger LOG = LoggerFactory.getLogger(SubjectController.class);
  private final SubjectService subjectService;
  private final SubjectMapper mapper;
  private final int[] indx = {-1};
  private List<SubjectModel> subjectModels = new ArrayList<>();

  private static void setSubjectForBooks(final SubjectEntity subject) {
    if (CollectionUtils.isNotEmpty(subject.getBooks())) {
      subject.getBooks().forEach(book -> {
        book.setSubject(subject);
      });
    }
  }

  private static NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static SubjectDTO getSubjectDTO(List<SubjectModel> models, int indx) {
    final NavigationDtl dtl = resetNavigation();
    if (models.isEmpty()) {
      final SubjectModel model = new SubjectModel();
      return SubjectDTO.builder()
          .subject(model)
          .navigationDtl(dtl)
          .build();
    }
    if (indx < 0 || indx > models.size() - 1) {
      LOG.info("models.size(): {}", models.size());
      LOG.info("Index in getSubjectDTO(): {}", indx);
      throw new IndexOutOfBoundsException();
    } else {
      final SubjectModel model = models.get(indx);
      if (indx > 0) {
        dtl.setFirst(false);
      }
      if (indx < models.size() - 1) {
        dtl.setLast(false);
      }

      return SubjectDTO.builder()
          .subject(model)
          .navigationDtl(dtl)
          .build();
    }
  }

  @PostConstruct
  public void init() {
    subjectModels = mapper.toModels(subjectService.findAll());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<SubjectModel>>> findAll() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<SubjectModel>>builder()
            .success(true)
            .message("findAll response")
            .entity(subjectModels)
            .build()
    );
  }

  @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<SubjectModel>>> searchSubject(@RequestBody final SubjectModel model) {
    subjectModels = mapper.toModels(subjectService.searchSubject(mapper.toEntity(model)));
    return ResponseEntity.ok(
        ApiResponse
            .<List<SubjectModel>>builder()
            .success(true)
            .message("searchSubject response")
            .entity(subjectModels)
            .build()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<SubjectDTO>> findById(@PathVariable("id") final UUID id) {
    final SubjectModel model = mapper.toModel(subjectService.findById(id));
    indx[0] = subjectModels.indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectDTO>builder()
            .success(true)
            .message("findById response")
            .entity(getSubjectDTO(subjectModels, indx[0]))
            .build()
    );
  }

  @GetMapping("{id}/name")
  public ResponseEntity<ApiResponse<String>> getSubjectName(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getSubjectName response")
            .entity(subjectService.findById(id).getName())
            .build()
    );
  }

  @GetMapping("hierarchy/{id}")
  public ResponseEntity<ApiResponse<String>> getSubjectHierarchy(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getSubjectHierarchy response")
            .entity(subjectService.getSubjectHierarchy(id))
            .build()
    );
  }

  @GetMapping("first")
  public ResponseEntity<ApiResponse<SubjectDTO>> first() {
    indx[0] = 0;
    LOG.info("Index in first(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectDTO>builder()
            .success(true)
            .message("first response")
            .entity(getSubjectDTO(subjectModels, indx[0]))
            .build()
    );
  }

  @GetMapping("previous")
  public ResponseEntity<ApiResponse<SubjectDTO>> previous() {
    indx[0]--;
    LOG.info("Index in previous(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectDTO>builder()
            .success(true)
            .message("previous response")
            .entity(getSubjectDTO(subjectModels, indx[0]))
            .build()
    );
  }

  @GetMapping("next")
  public ResponseEntity<ApiResponse<SubjectDTO>> next() {
    indx[0]++;
    LOG.info("Index in next(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectDTO>builder()
            .success(true)
            .message("next response")
            .entity(getSubjectDTO(subjectModels, indx[0]))
            .build()
    );
  }

  @GetMapping("last")
  public ResponseEntity<ApiResponse<SubjectDTO>> last() {
    indx[0] = subjectModels.size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectDTO>builder()
            .success(true)
            .message("last response")
            .entity(getSubjectDTO(subjectModels, indx[0]))
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<SubjectDTO>> save(@RequestBody final SubjectModel model) {
    final SubjectEntity subject = mapper.toEntity(model);
//    Below line added, because when converted from model to SubjectEntity, there is no subject set in book list.
    setSubjectForBooks(subject);
    final SubjectEntity[] subjectSaved = new SubjectEntity[1];
      subjectSaved[0] = subjectService.save(subject);
      final SubjectModel savedModel = mapper.toModel(subjectSaved[0]);
      init();
      indx[0] = this.subjectModels.indexOf(savedModel);
      LOG.info("Index in saveSubject(): {}", indx[0]);
      return ResponseEntity.ok(
          ApiResponse
              .<SubjectDTO>builder()
              .success(true)
              .message("Subject saved successfully")
              .entity(getSubjectDTO(subjectModels, indx[0]))
              .build()
      );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<SubjectEntity>>> saveAll(@RequestBody final List<SubjectModel> models) {
    final List<SubjectEntity> subjects = mapper.toEntities(models);
    //    Below line added, because when converted from model to SubjectEntity, there is no subject set in book list.
    subjects.forEach(subject -> {
      setSubjectForBooks(subject);
    });
    return ResponseEntity.ok(
        ApiResponse
            .<List<SubjectEntity>>builder()
            .success(true)
            .message("All subjects saved seccessfully")
            .entity(subjectService.save(new HashSet<>(subjects)))
            .build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse<SubjectDTO>> deleteById(@PathVariable("id") final UUID id) {
    if (!subjectService.exists(id)) {
      throw new IllegalArgumentException("SubjectEntity with id: " + id + " does not exist");
    } else {

        subjectService.deleteById(id);
        init();
        indx[0]--;
        LOG.info("Index in deleteSubjectById(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<SubjectDTO>builder()
                .success(true)
                .message("Subject deleted successfully")
                .entity(getSubjectDTO(subjectModels, indx[0]))
                .build()
        );

    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<SubjectDTO>> delete(@RequestBody final SubjectModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getId() || !subjectService.exists(model.getId())) {
      throw new IllegalArgumentException("SubjectEntity does not exist");
    } else {

        subjectService.delete(mapper.toEntity(model));
        init();
        indx[0]--;
        LOG.info("Index in deleteSubject(): {}", indx[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<SubjectDTO>builder()
                .success(true)
                .message("Subject deleted successfully")
                .entity(getSubjectDTO(subjectModels, indx[0]))
                .build()
        );

    }
  }
}
