package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.SubjectDTO;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.SubjectMapper;
import org.zahid.apps.web.library.model.SubjectModel;
import org.zahid.apps.web.library.service.SubjectService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("api/subject")
public class SubjectController {

    private static final Logger LOG = LogManager.getLogger(SubjectController.class);
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectMapper mapper;

    private final int[] indx = {-1};

    private static void setSubjectForBooks(final SubjectEntity subject) {
        if (CollectionUtils.isNotEmpty(subject.getBooks())) {
            subject.getBooks().forEach(stock -> {
                stock.setSubject(subject);
            });
        }
    }

    @GetMapping("all")
    public List<SubjectModel> findAll() {
        return mapper.mapSubjectEntitiesToSubjectModels(subjectService.findAll());
    }

    @GetMapping("{id}")
    public SubjectDTO findById(@PathVariable("id") final Long id) {
        final SubjectModel model = mapper.fromSubject(subjectService.findById(id));
        indx[0] = findAll().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return getSubjectDTO(findAll(), indx[0]);
    }

    @GetMapping("{id}/name")
    public String getSubjectName(@PathVariable("id") final Long id) {
        return subjectService.findById(id).getSubjectName();
    }

    @GetMapping("first")
    public SubjectDTO first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return getSubjectDTO(findAll(), indx[0]);
    }

    @GetMapping("previous")
    public SubjectDTO previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return getSubjectDTO(findAll(), indx[0]);
    }

    @GetMapping("next")
    public SubjectDTO next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return getSubjectDTO(findAll(), indx[0]);
    }

    @GetMapping("last")
    public SubjectDTO last() {
        indx[0] = findAll().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return getSubjectDTO(findAll(), indx[0]);
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubjectDTO save(@RequestBody final SubjectModel model) {
        final SubjectEntity subject = mapper.toSubject(model);
//    Below line added, because when converted from model to SubjectEntity, there is no subject set in book list.
        setSubjectForBooks(subject);
        final SubjectEntity subjectSaved = subjectService.save(subject);
        final SubjectModel savedModel = mapper.fromSubject(subjectSaved);
        indx[0] = this.findAll().indexOf(savedModel);
        LOG.info("Index in saveSubject(): {}", indx[0]);
        return getSubjectDTO(findAll(), indx[0]);
    }

    @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubjectEntity> saveAll(@RequestBody final List<SubjectModel> models) {
        final List<SubjectEntity> subjects = mapper.mapSubjectModelsToSubjects(models);
        //    Below line added, because when converted from model to SubjectEntity, there is no subject set in book list.
        subjects.forEach(subject -> {
            setSubjectForBooks(subject);
        });
        return subjectService.save(new HashSet<>(subjects));
    }

    @DeleteMapping("/delete/{id}")
    public SubjectDTO deleteById(@PathVariable("id") final Long id) {
        if (!subjectService.exists(id)) {
            throw new IllegalArgumentException("SubjectEntity with id: " + id + " does not exist");
        } else {
            try {
                subjectService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteSubjectById(): {}", indx[0]);
                return getSubjectDTO(findAll(), indx[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubjectDTO delete(@RequestBody final SubjectModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getSubjectId() || !subjectService.exists(model.getSubjectId())) {
            throw new IllegalArgumentException("SubjectEntity does not exist");
        } else {
            try {
                subjectService.delete(mapper.toSubject(model));
                indx[0]--;
                LOG.info("Index in deleteSubject(): {}", indx[0]);
                return getSubjectDTO(findAll(), indx[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static final NavigationDtl resetNavigation() {
        NavigationDtl dtl = new NavigationDtl();
        dtl.setFirst(true);
        dtl.setLast(true);
        return dtl;
    }

    private static final SubjectDTO getSubjectDTO(List<SubjectModel> models, int indx) {
        if (indx < 0 || indx > models.size() - 1) {
            LOG.info("Index in getSubjectDTO(): {}", indx);
            throw new IndexOutOfBoundsException();
        } else {
            final NavigationDtl dtl = resetNavigation();
            final SubjectModel model = models.get(indx);
            final SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setSubject(model);
            if (indx > 0) {
                dtl.setFirst(false);
            }
            if (indx < models.size() - 1) {
                dtl.setLast(false);
            }
            subjectDTO.setNavigationDtl(dtl);
            return subjectDTO;
        }
    }
}
