package org.zahid.apps.web.library.controller;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zahid.apps.web.library.dto.SubjectDTO;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.mapper.SubjectMapper;
import org.zahid.apps.web.library.model.SubjectModel;
import org.zahid.apps.web.library.service.SubjectService;
import org.zahid.apps.web.library.utils.Miscellaneous;

@RestController
@RequestMapping("subjects")
public class SubjectController {

    private static final Logger LOG = LogManager.getLogger(SubjectController.class);
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
    public ResponseEntity<List<SubjectModel>> findAll() {
        return ResponseEntity.ok(mapper.mapSubjectEntitiesToSubjectModels(subjectService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<SubjectDTO> findById(@PathVariable("id") final Long id) {
        final SubjectModel model = mapper.fromSubject(subjectService.findById(id));
        indx[0] = findAll().getBody().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("{id}/name")
    public ResponseEntity<String> getSubjectName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(subjectService.findById(id).getSubjectName());
    }

    @GetMapping("hierarchy/{id}")
    public ResponseEntity<String> getSubjectHierarchy(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Miscellaneous.getSubjectHierarchy(id));
    }

    @GetMapping("first")
    public ResponseEntity<SubjectDTO> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("previous")
    public ResponseEntity<SubjectDTO> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("next")
    public ResponseEntity<SubjectDTO> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("last")
    public ResponseEntity<SubjectDTO> last() {
        indx[0] = findAll().getBody().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectDTO> save(@RequestBody final SubjectModel model) {
        final SubjectEntity subject = mapper.toSubject(model);
//    Below line added, because when converted from model to SubjectEntity, there is no subject set in book list.
        setSubjectForBooks(subject);
        final SubjectEntity[] subjectSaved = new SubjectEntity[1];
        try {
            subjectSaved[0] = subjectService.save(subject);
            final SubjectModel savedModel = mapper.fromSubject(subjectSaved[0]);
            indx[0] = this.findAll().getBody().indexOf(savedModel);
            LOG.info("Index in saveSubject(): {}", indx[0]);
            return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
        } catch (DataIntegrityViolationException e) {
            LOG.error("Duplicate entry found");
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABC");
            return new ResponseEntity("Duplicate entry found", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectEntity>> saveAll(@RequestBody final List<SubjectModel> models) {
        final List<SubjectEntity> subjects = mapper.mapSubjectModelsToSubjects(models);
        //    Below line added, because when converted from model to SubjectEntity, there is no subject set in book list.
        subjects.forEach(subject -> {
            setSubjectForBooks(subject);
        });
        return ResponseEntity.ok(subjectService.save(new HashSet<>(subjects)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SubjectDTO> deleteById(@PathVariable("id") final Long id) {
        if (!subjectService.exists(id)) {
            throw new IllegalArgumentException("SubjectEntity with id: " + id + " does not exist");
        } else {
            try {
                subjectService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteSubjectById(): {}", indx[0]);
                return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectDTO> delete(@RequestBody final SubjectModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getSubjectId() || !subjectService
                .exists(model.getSubjectId())) {
            throw new IllegalArgumentException("SubjectEntity does not exist");
        } else {
            try {
                subjectService.delete(mapper.toSubject(model));
                indx[0]--;
                LOG.info("Index in deleteSubject(): {}", indx[0]);
                return ResponseEntity.ok(getSubjectDTO(findAll().getBody(), indx[0]));
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
