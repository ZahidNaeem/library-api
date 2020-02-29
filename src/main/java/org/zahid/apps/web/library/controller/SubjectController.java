package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.SubjectDTO;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.exception.BadRequestException;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.SubjectMapper;
import org.zahid.apps.web.library.model.SubjectModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.SubjectService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("subjects")
public class SubjectController {

    private static final Logger LOG = LogManager.getLogger(SubjectController.class);
    private List<SubjectModel> subjectModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        subjectModels = mapper.toModels(subjectService.findAll());
    }

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectMapper mapper;

    private final int[] indx = {-1};

    private static void setSubjectForBooks(final SubjectEntity subject) {
        if (CollectionUtils.isNotEmpty(subject.getBooks())) {
            subject.getBooks().forEach(book -> {
                book.setSubject(subject);
            });
        }
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
    public ResponseEntity<ApiResponse<SubjectDTO>> findById(@PathVariable("id") final Long id) {
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
    public ResponseEntity<ApiResponse<String>> getSubjectName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getSubjectName response")
                        .entity(subjectService.findById(id).getSubjectName())
                        .build()
        );
    }

    @GetMapping("hierarchy/{id}")
    public ResponseEntity<ApiResponse<String>> getSubjectHierarchy(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getSubjectHierarchy response")
                        .entity(Miscellaneous.getSubjectHierarchy(id))
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
        try {
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
        } catch (DataIntegrityViolationException e) {
            LOG.error("Duplicate entry found");
            throw new BadRequestException("Duplicate entry found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException(e.getMessage());
        }
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
    public ResponseEntity<ApiResponse<SubjectDTO>> deleteById(@PathVariable("id") final Long id) {
        if (!subjectService.exists(id)) {
            throw new IllegalArgumentException("SubjectEntity with id: " + id + " does not exist");
        } else {
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<SubjectDTO>> delete(@RequestBody final SubjectModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getSubjectId() || !subjectService
                .exists(model.getSubjectId())) {
            throw new IllegalArgumentException("SubjectEntity does not exist");
        } else {
            try {
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

    private static final SubjectDTO getSubjectDTO(List<SubjectModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
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
}
