package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.BookTransHeaderDTO;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.mapper.BookTransHeaderMapper;
import org.zahid.apps.web.library.model.BookTransHeaderModel;
import org.zahid.apps.web.library.service.BookTransHeaderService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("bookTransHeaders")
public class BookTransHeaderController {

    private static final Logger LOG = LogManager.getLogger(BookTransHeaderController.class);
    @Autowired
    private BookTransHeaderService bookTransHeaderService;

    @Autowired
    private BookTransHeaderMapper mapper;

    private final int[] indx = {-1};

    private static void setBookTransHeaderForBookTransLines(final BookTransHeaderEntity bookTransHeader) {
        if (CollectionUtils.isNotEmpty(bookTransHeader.getBookTransLines())) {
            bookTransHeader.getBookTransLines().forEach(bookTransLine -> {
                bookTransLine.setBookTransHeader(bookTransHeader);
            });
        }
    }

    @GetMapping
    public ResponseEntity<List<BookTransHeaderModel>> findAll() {
        return ResponseEntity.ok(mapper.toBookTransHeaderModels(bookTransHeaderService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookTransHeaderDTO> findById(@PathVariable("id") final Long id) {
        final BookTransHeaderModel model = mapper.toBookTransHeaderModel(bookTransHeaderService.findById(id));
        indx[0] = findAll().getBody().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("first")
    public ResponseEntity<BookTransHeaderDTO> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("previous")
    public ResponseEntity<BookTransHeaderDTO> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("next")
    public ResponseEntity<BookTransHeaderDTO> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("last")
    public ResponseEntity<BookTransHeaderDTO> last() {
        indx[0] = findAll().getBody().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookTransHeaderDTO> save(@RequestBody final BookTransHeaderModel model) {
        final BookTransHeaderEntity bookTransHeader = mapper.toBookTransHeaderEntity(model);
//    Below line added, because when converted from model to BookTransHeaderEntity, there is no bookTransHeader set in book list.
        setBookTransHeaderForBookTransLines(bookTransHeader);
        setBookTransHeaderForBookTransLines(bookTransHeader);
        final BookTransHeaderEntity bookTransHeaderSaved = bookTransHeaderService.save(bookTransHeader);
        final BookTransHeaderModel savedModel = mapper.toBookTransHeaderModel(bookTransHeaderSaved);
        indx[0] = this.findAll().getBody().indexOf(savedModel);
        LOG.info("Index in saveBookTransHeader(): {}", indx[0]);
        return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookTransHeaderEntity>> saveAll(@RequestBody final List<BookTransHeaderModel> models) {
        final List<BookTransHeaderEntity> shelves = mapper.toBookTransHeaderEntities(models);
        //    Below line added, because when converted from model to BookTransHeaderEntity, there is no bookTransHeader set in book list.
        shelves.forEach(bookTransHeader -> {
            setBookTransHeaderForBookTransLines(bookTransHeader);
            setBookTransHeaderForBookTransLines(bookTransHeader);
        });
        return ResponseEntity.ok(bookTransHeaderService.save(new HashSet<>(shelves)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookTransHeaderDTO> deleteById(@PathVariable("id") final Long id) {
        if (!bookTransHeaderService.exists(id)) {
            throw new IllegalArgumentException("BookTransHeaderEntity with id: " + id + " does not exist");
        } else {
            try {
                bookTransHeaderService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteBookTransHeaderById(): {}", indx[0]);
                return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookTransHeaderDTO> delete(@RequestBody final BookTransHeaderModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getHeaderId() || !bookTransHeaderService.exists(model.getHeaderId())) {
            throw new IllegalArgumentException("BookTransHeaderEntity does not exist");
        } else {
            try {
                bookTransHeaderService.delete(mapper.toBookTransHeaderEntity(model));
                indx[0]--;
                LOG.info("Index in deleteBookTransHeader(): {}", indx[0]);
                return ResponseEntity.ok(getBookTransHeaderDTO(findAll().getBody(), indx[0]));
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

    private static final BookTransHeaderDTO getBookTransHeaderDTO(List<BookTransHeaderModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
            final BookTransHeaderModel model = new BookTransHeaderModel();
            return BookTransHeaderDTO.builder()
                    .bookTransHeader(model)
                    .navigationDtl(dtl)
                    .build();
        }
        if (indx < 0 || indx > models.size() - 1) {
            LOG.info("models.size(): {}", models.size());
            LOG.info("Index in getBookTransHeaderDTO(): {}", indx);
            throw new IndexOutOfBoundsException();
        } else {
            final BookTransHeaderModel model = models.get(indx);
            if (indx > 0) {
                dtl.setFirst(false);
            }
            if (indx < models.size() - 1) {
                dtl.setLast(false);
            }

            return BookTransHeaderDTO.builder()
                    .bookTransHeader(model)
                    .navigationDtl(dtl)
                    .build();
        }
    }
}
