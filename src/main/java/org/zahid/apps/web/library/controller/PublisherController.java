package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.PublisherDTO;
import org.zahid.apps.web.library.entity.PublisherEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.PublisherMapper;
import org.zahid.apps.web.library.model.PublisherModel;
import org.zahid.apps.web.library.service.PublisherService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("publisher")
public class PublisherController {

    private static final Logger LOG = LogManager.getLogger(PublisherController.class);
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

    @GetMapping("all")
    public List<PublisherModel> findAll() {
        return mapper.mapPublisherEntitiesToPublisherModels(publisherService.findAll());
    }

    @GetMapping("{id}")
    public PublisherDTO findById(@PathVariable("id") final Long id) {
        final PublisherModel model = mapper.fromPublisher(publisherService.findById(id));
        indx[0] = findAll().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return getPublisherDTO(findAll(), indx[0]);
    }

    @GetMapping("{id}/name")
    public String getPublisherName(@PathVariable("id") final Long id) {
        return publisherService.findById(id).getPublisherName();
    }

    @GetMapping("first")
    public PublisherDTO first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return getPublisherDTO(findAll(), indx[0]);
    }

    @GetMapping("previous")
    public PublisherDTO previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return getPublisherDTO(findAll(), indx[0]);
    }

    @GetMapping("next")
    public PublisherDTO next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return getPublisherDTO(findAll(), indx[0]);
    }

    @GetMapping("last")
    public PublisherDTO last() {
        indx[0] = findAll().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return getPublisherDTO(findAll(), indx[0]);
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PublisherDTO save(@RequestBody final PublisherModel model) {
        final PublisherEntity publisher = mapper.toPublisher(model);
//    Below line added, because when converted from model to PublisherEntity, there is no publisher set in book list.
        setPublisherForBooks(publisher);
        final PublisherEntity publisherSaved = publisherService.save(publisher);
        final PublisherModel savedModel = mapper.fromPublisher(publisherSaved);
        indx[0] = this.findAll().indexOf(savedModel);
        LOG.info("Index in savePublisher(): {}", indx[0]);
        return getPublisherDTO(findAll(), indx[0]);
    }

    @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PublisherEntity> saveAll(@RequestBody final List<PublisherModel> models) {
        final List<PublisherEntity> publishers = mapper.mapPublisherModelsToPublishers(models);
        //    Below line added, because when converted from model to PublisherEntity, there is no publisher set in book list.
        publishers.forEach(publisher -> {
            setPublisherForBooks(publisher);
        });
        return publisherService.save(new HashSet<>(publishers));
    }

    @DeleteMapping("/delete/{id}")
    public PublisherDTO deleteById(@PathVariable("id") final Long id) {
        if (!publisherService.exists(id)) {
            throw new IllegalArgumentException("PublisherEntity with id: " + id + " does not exist");
        } else {
            try {
                publisherService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deletePublisherById(): {}", indx[0]);
                return getPublisherDTO(findAll(), indx[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PublisherDTO delete(@RequestBody final PublisherModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getPublisherId() || !publisherService.exists(model.getPublisherId())) {
            throw new IllegalArgumentException("PublisherEntity does not exist");
        } else {
            try {
                publisherService.delete(mapper.toPublisher(model));
                indx[0]--;
                LOG.info("Index in deletePublisher(): {}", indx[0]);
                return getPublisherDTO(findAll(), indx[0]);
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
