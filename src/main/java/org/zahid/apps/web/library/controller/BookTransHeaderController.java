package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.mapper.BookTransHeaderMapper;
import org.zahid.apps.web.library.model.BookTransHeaderModel;
import org.zahid.apps.web.library.service.BookTransHeaderService;
import org.zahid.apps.web.library.service.ReaderService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("bookTransHeaders")
public class BookTransHeaderController {

    private static final Logger LOG = LogManager.getLogger(BookTransHeaderController.class);

    @Autowired
    private BookTransHeaderService bookTransHeaderService;

    @Autowired
    private BookTransHeaderMapper mapper;

    @Autowired
    private ReaderService readerService;

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
    public ResponseEntity<BookTransHeaderModel> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(mapper.toBookTransHeaderModel(bookTransHeaderService.findById(id)));
    }

    @GetMapping("reader/{readerId}")
    public ResponseEntity<List<BookTransHeaderModel>> findByReader(@PathVariable("readerId") final Long readerId) {
        return ResponseEntity.ok(mapper.toBookTransHeaderModels(bookTransHeaderService.findAllByReader(readerService.findById(readerId))));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookTransHeaderModel> save(@RequestBody final BookTransHeaderModel model) {
        final BookTransHeaderEntity bookTransHeader = mapper.toBookTransHeaderEntity(model);
        final BookTransHeaderEntity savedBookTransHeader = bookTransHeaderService.save(bookTransHeader);
        setBookTransHeaderForBookTransLines(bookTransHeader);
        return ResponseEntity.ok(mapper.toBookTransHeaderModel(savedBookTransHeader));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookTransHeaderEntity>> saveAll(@RequestBody final Set<BookTransHeaderModel> bookTransHeaderModel) {
        final Set<BookTransHeaderEntity> bookTransHeaders = new HashSet<>();
        bookTransHeaderModel.forEach(model -> {
            final BookTransHeaderEntity bookTransHeader = mapper.toBookTransHeaderEntity(model);
            setBookTransHeaderForBookTransLines(bookTransHeader);
            bookTransHeaders.add(bookTransHeader);
        });
        return ResponseEntity.ok(bookTransHeaderService.save(bookTransHeaders));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") final Long id) {
        if (!bookTransHeaderService.exists(id)) {
            throw new IllegalArgumentException("Item bookTransHeader with id: " + id + " does not exist");
        } else {
            try {
                bookTransHeaderService.deleteById(id);
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@RequestBody final BookTransHeaderModel model) {
        if (null == model || null == model.getHeaderId() || !bookTransHeaderService
                .exists(model.getHeaderId())) {
            throw new IllegalArgumentException("Item bookTransHeader does not exist");
        } else {
            try {
                bookTransHeaderService.delete(mapper.toBookTransHeaderEntity(model));
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }
}
