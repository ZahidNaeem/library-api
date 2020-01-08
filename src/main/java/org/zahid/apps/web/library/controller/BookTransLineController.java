package org.zahid.apps.web.library.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.mapper.BookTransLineMapper;
import org.zahid.apps.web.library.model.BookTransLineModel;
import org.zahid.apps.web.library.service.BookTransHeaderService;
import org.zahid.apps.web.library.service.BookTransLineService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("bookTransLines")
public class BookTransLineController {

    private static final Logger LOG = LogManager.getLogger(BookTransLineController.class);

    @Autowired
    private BookTransLineService bookTransLineService;

    @Autowired
    private BookTransLineMapper mapper;

    @Autowired
    private BookTransHeaderService bookTransHeaderService;

    @GetMapping
    public ResponseEntity<List<BookTransLineModel>> findAll() {
        return ResponseEntity.ok(mapper.toBookTransLineModels(bookTransLineService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookTransLineModel> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(mapper.toBookTransLineModel(bookTransLineService.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookTransLineModel> save(@RequestBody final BookTransLineModel model) {
        final BookTransLineEntity savedBookTransLine = bookTransLineService.save(mapper.toBookTransLineEntity(model));
        return ResponseEntity.ok(mapper.toBookTransLineModel(savedBookTransLine));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookTransLineEntity>> saveAll(@RequestBody final Set<BookTransLineModel> bookTransLineModel) {
        final Set<BookTransLineEntity> bookTransLines = new HashSet<>();
        bookTransLineModel.forEach(model -> {
            final BookTransLineEntity bookTransLine = mapper.toBookTransLineEntity(model);
            bookTransLines.add(bookTransLine);
        });
        return ResponseEntity.ok(bookTransLineService.save(bookTransLines));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") final Long id) {
        if (!bookTransLineService.exists(id)) {
            throw new IllegalArgumentException("Item bookTransLine with id: " + id + " does not exist");
        } else {
            try {
                bookTransLineService.deleteById(id);
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@RequestBody final BookTransLineModel model) {
        if (null == model || null == model.getLineId() || !bookTransLineService
                .exists(model.getLineId())) {
            throw new IllegalArgumentException("Item bookTransLine does not exist");
        } else {
            try {
                bookTransLineService.delete(mapper.toBookTransLineEntity(model));
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }
}
