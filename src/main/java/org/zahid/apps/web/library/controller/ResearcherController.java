package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.ResearcherDTO;
import org.zahid.apps.web.library.entity.ResearcherEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.ResearcherMapper;
import org.zahid.apps.web.library.model.ResearcherModel;
import org.zahid.apps.web.library.service.ResearcherService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("researcher")
public class ResearcherController {

    private static final Logger LOG = LogManager.getLogger(ResearcherController.class);
    @Autowired
    private ResearcherService researcherService;

    @Autowired
    private ResearcherMapper mapper;

    private final int[] indx = {-1};

    private static void setResearcherForBooks(final ResearcherEntity researcher) {
        if (CollectionUtils.isNotEmpty(researcher.getBooks())) {
            researcher.getBooks().forEach(book -> {
                book.setResearcher(researcher);
            });
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<ResearcherModel>> findAll() {
        return ResponseEntity.ok(mapper.mapResearcherEntitiesToResearcherModels(researcherService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResearcherDTO> findById(@PathVariable("id") final Long id) {
        final ResearcherModel model = mapper.fromResearcher(researcherService.findById(id));
        indx[0] = findAll().getBody().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("{id}/name")
    public ResponseEntity<String> getResearcherName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(researcherService.findById(id).getResearcherName());
    }

    @GetMapping("first")
    public ResponseEntity<ResearcherDTO> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("previous")
    public ResponseEntity<ResearcherDTO> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("next")
    public ResponseEntity<ResearcherDTO> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("last")
    public ResponseEntity<ResearcherDTO> last() {
        indx[0] = findAll().getBody().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResearcherDTO> save(@RequestBody final ResearcherModel model) {
        final ResearcherEntity researcher = mapper.toResearcher(model);
//    Below line added, because when converted from model to ResearcherEntity, there is no researcher set in book list.
        setResearcherForBooks(researcher);
        final ResearcherEntity researcherSaved = researcherService.save(researcher);
        final ResearcherModel savedModel = mapper.fromResearcher(researcherSaved);
        indx[0] = this.findAll().getBody().indexOf(savedModel);
        LOG.info("Index in saveResearcher(): {}", indx[0]);
        return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResearcherEntity>> saveAll(@RequestBody final List<ResearcherModel> models) {
        final List<ResearcherEntity> researchers = mapper.mapResearcherModelsToResearchers(models);
        //    Below line added, because when converted from model to ResearcherEntity, there is no researcher set in book list.
        researchers.forEach(researcher -> {
            setResearcherForBooks(researcher);
        });
        return ResponseEntity.ok(researcherService.save(new HashSet<>(researchers)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResearcherDTO> deleteById(@PathVariable("id") final Long id) {
        if (!researcherService.exists(id)) {
            throw new IllegalArgumentException("ResearcherEntity with id: " + id + " does not exist");
        } else {
            try {
                researcherService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteResearcherById(): {}", indx[0]);
                return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResearcherDTO> delete(@RequestBody final ResearcherModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getResearcherId() || !researcherService.exists(model.getResearcherId())) {
            throw new IllegalArgumentException("ResearcherEntity does not exist");
        } else {
            try {
                researcherService.delete(mapper.toResearcher(model));
                indx[0]--;
                LOG.info("Index in deleteResearcher(): {}", indx[0]);
                return ResponseEntity.ok(getResearcherDTO(findAll().getBody(), indx[0]));
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

    private static final ResearcherDTO getResearcherDTO(List<ResearcherModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
            final ResearcherModel model = new ResearcherModel();
            return ResearcherDTO.builder()
                    .researcher(model)
                    .navigationDtl(dtl)
                    .build();
        }
        if (indx < 0 || indx > models.size() - 1) {
            LOG.info("models.size(): {}", models.size());
            LOG.info("Index in getResearcherDTO(): {}", indx);
            throw new IndexOutOfBoundsException();
        } else {
            final ResearcherModel model = models.get(indx);
            if (indx > 0) {
                dtl.setFirst(false);
            }
            if (indx < models.size() - 1) {
                dtl.setLast(false);
            }

            return ResearcherDTO.builder()
                    .researcher(model)
                    .navigationDtl(dtl)
                    .build();
        }
    }
}
