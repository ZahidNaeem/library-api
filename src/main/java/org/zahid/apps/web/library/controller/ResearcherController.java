package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.ResearcherDTO;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.ResearcherEntity;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.ResearcherMapper;
import org.zahid.apps.web.library.model.ResearcherModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.ResearcherService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("researchers")
public class ResearcherController {

    private static final Logger LOG = LogManager.getLogger(ResearcherController.class);
    private List<ResearcherModel> researcherModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        researcherModels = mapper.toResearcherModels(researcherService.findAll());
    }

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
        researcherModels = Miscellaneous.searchResearcher(model);
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
    public ResponseEntity<ApiResponse<ResearcherDTO>> findById(@PathVariable("id") final Long id) {
        final ResearcherModel model = mapper.toResearcherModel(researcherService.findById(id));
        indx[0] = researcherModels.indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ResearcherDTO>builder()
                        .success(true)
                        .message("findById response")
                        .entity(getResearcherDTO(researcherModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("{id}/name")
    public ResponseEntity<ApiResponse<String>> getResearcherName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getResearcherName response")
                        .entity(researcherService.findById(id).getResearcherName())
                        .build()
        );
    }

    @GetMapping("first")
    public ResponseEntity<ApiResponse<ResearcherDTO>> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ResearcherDTO>builder()
                        .success(true)
                        .message("first record response")
                        .entity(getResearcherDTO(researcherModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("previous")
    public ResponseEntity<ApiResponse<ResearcherDTO>> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ResearcherDTO>builder()
                        .success(true)
                        .message("previous record response")
                        .entity(getResearcherDTO(researcherModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("next")
    public ResponseEntity<ApiResponse<ResearcherDTO>> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ResearcherDTO>builder()
                        .success(true)
                        .message("next record response")
                        .entity(getResearcherDTO(researcherModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("last")
    public ResponseEntity<ApiResponse<ResearcherDTO>> last() {
        indx[0] = researcherModels.size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ResearcherDTO>builder()
                        .success(true)
                        .message("last record response")
                        .entity(getResearcherDTO(researcherModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ResearcherDTO>> save(@RequestBody final ResearcherModel model) {
        final ResearcherEntity researcher = mapper.toResearcherEntity(model);
//    Below line added, because when converted from model to ResearcherEntity, there is no researcher set in book list.
        setResearcherForBooks(researcher);
        final ResearcherEntity researcherSaved = researcherService.save(researcher);
        final ResearcherModel savedModel = mapper.toResearcherModel(researcherSaved);
        init();
        indx[0] = this.researcherModels.indexOf(savedModel);
        LOG.info("Index in saveResearcher(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<ResearcherDTO>builder()
                        .success(true)
                        .message("Researcher saved seccessfully")
                        .entity(getResearcherDTO(researcherModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ResearcherEntity>>> saveAll(@RequestBody final List<ResearcherModel> models) {
        final List<ResearcherEntity> researchers = mapper.toResearcherEntities(models);
        //    Below line added, because when converted from model to ResearcherEntity, there is no researcher set in book list.
        researchers.forEach(researcher -> {
            setResearcherForBooks(researcher);
        });
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
    public ResponseEntity<ApiResponse<ResearcherDTO>> deleteById(@PathVariable("id") final Long id) {
        if (!researcherService.exists(id)) {
            throw new IllegalArgumentException("ResearcherEntity with id: " + id + " does not exist");
        } else {
            try {
                researcherService.deleteById(id);
                init();
                indx[0]--;
                LOG.info("Index in deleteResearcherById(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<ResearcherDTO>builder()
                                .success(true)
                                .message("Researcher deleted seccessfully")
                                .entity(getResearcherDTO(researcherModels, indx[0]))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ResearcherDTO>> delete(@RequestBody final ResearcherModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getResearcherId() || !researcherService
                .exists(model.getResearcherId())) {
            throw new IllegalArgumentException("ResearcherEntity does not exist");
        } else {
            try {
                researcherService.delete(mapper.toResearcherEntity(model));
                init();
                indx[0]--;
                LOG.info("Index in deleteResearcher(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<ResearcherDTO>builder()
                                .success(true)
                                .message("Researcher deleted seccessfully")
                                .entity(getResearcherDTO(researcherModels, indx[0]))
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
