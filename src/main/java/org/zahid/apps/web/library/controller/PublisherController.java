package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.PublisherDTO;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.PublisherEntity;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.PublisherMapper;
import org.zahid.apps.web.library.model.PublisherModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.PublisherService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    private static final Logger LOG = LogManager.getLogger(PublisherController.class);
    private List<PublisherModel> publisherModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        publisherModels = mapper.toPublisherModels(publisherService.findAll());
    }

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private PublisherMapper mapper;

    private final int[] indx = {-1};

    private static void setPublisherForBooks(final PublisherEntity publisher) {
        if (CollectionUtils.isNotEmpty(publisher.getBooks())) {
            publisher.getBooks().forEach(book -> {
                book.setPublisher(publisher);
            });
        }
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
        publisherModels = mapper.toPublisherModels(publisherService.searchPublisher(mapper.toPublisherEntity(model)));
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
    public ResponseEntity<ApiResponse<PublisherDTO>> findById(@PathVariable("id") final Long id) {
        final PublisherModel model = mapper.toPublisherModel(publisherService.findById(id));
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
    public ResponseEntity<ApiResponse<String>> getPublisherName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getPublisherName response")
                        .entity(publisherService.findById(id).getPublisherName())
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
        final PublisherEntity publisher = mapper.toPublisherEntity(model);
//    Below line added, because when converted from model to PublisherEntity, there is no publisher set in book list.
        setPublisherForBooks(publisher);
        final PublisherEntity publisherSaved = publisherService.save(publisher);
        final PublisherModel savedModel = mapper.toPublisherModel(publisherSaved);
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
        final List<PublisherEntity> publishers = mapper.toPublisherEntities(models);
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
    public ResponseEntity<ApiResponse<PublisherDTO>> deleteById(@PathVariable("id") final Long id) {
        if (!publisherService.exists(id)) {
            throw new IllegalArgumentException("PublisherEntity with id: " + id + " does not exist");
        } else {
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<PublisherDTO>> delete(@RequestBody final PublisherModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getPublisherId() || !publisherService
                .exists(model.getPublisherId())) {
            throw new IllegalArgumentException("PublisherEntity does not exist");
        } else {
            try {
                publisherService.delete(mapper.toPublisherEntity(model));
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

    private static final PublisherDTO getPublisherDTO(List<PublisherModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
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
}
