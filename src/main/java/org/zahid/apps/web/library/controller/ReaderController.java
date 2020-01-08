package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.ReaderDTO;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.ReaderMapper;
import org.zahid.apps.web.library.model.ReaderModel;
import org.zahid.apps.web.library.service.ReaderService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("readers")
public class ReaderController {

    private static final Logger LOG = LogManager.getLogger(ReaderController.class);
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
    public ResponseEntity<List<ReaderModel>> findAll() {
        return ResponseEntity.ok(mapper.toReaderModels(readerService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReaderDTO> findById(@PathVariable("id") final Long id) {
        final ReaderModel model = mapper.toReaderModel(readerService.findById(id));
        indx[0] = findAll().getBody().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("{id}/name")
    public ResponseEntity<String> getReaderName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(readerService.findById(id).getReaderName());
    }

    @GetMapping("first")
    public ResponseEntity<ReaderDTO> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("previous")
    public ResponseEntity<ReaderDTO> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("next")
    public ResponseEntity<ReaderDTO> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("last")
    public ResponseEntity<ReaderDTO> last() {
        indx[0] = findAll().getBody().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDTO> save(@RequestBody final ReaderModel model) {
        final ReaderEntity reader = mapper.toReaderEntity(model);
//    Below line added, because when converted from model to ReaderEntity, there is no reader set in bookTransHeader list.
        setReaderForBookTransHeaders(reader);
        final ReaderEntity readerSaved = readerService.save(reader);
        final ReaderModel savedModel = mapper.toReaderModel(readerSaved);
        indx[0] = this.findAll().getBody().indexOf(savedModel);
        LOG.info("Index in saveReader(): {}", indx[0]);
        return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReaderEntity>> saveAll(@RequestBody final List<ReaderModel> models) {
        final List<ReaderEntity> readers = mapper.toReaderEntities(models);
        //    Below line added, because when converted from model to ReaderEntity, there is no reader set in bookTransHeader list.
        readers.forEach(reader -> {
            setReaderForBookTransHeaders(reader);
        });
        return ResponseEntity.ok(readerService.save(new HashSet<>(readers)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReaderDTO> deleteById(@PathVariable("id") final Long id) {
        if (!readerService.exists(id)) {
            throw new IllegalArgumentException("ReaderEntity with id: " + id + " does not exist");
        } else {
            try {
                readerService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteReaderById(): {}", indx[0]);
                return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDTO> delete(@RequestBody final ReaderModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getReaderId() || !readerService
                .exists(model.getReaderId())) {
            throw new IllegalArgumentException("ReaderEntity does not exist");
        } else {
            try {
                readerService.delete(mapper.toReaderEntity(model));
                indx[0]--;
                LOG.info("Index in deleteReader(): {}", indx[0]);
                return ResponseEntity.ok(getReaderDTO(findAll().getBody(), indx[0]));
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
