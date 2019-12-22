package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.BookDTO;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.service.BookService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private static final Logger LOG = LogManager.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper mapper;

    private final int[] indx = {-1};

    private static void setBookForVolumes(final BookEntity book) {
        if (CollectionUtils.isNotEmpty(book.getVolumes())) {
            book.getVolumes().forEach(volume -> {
                volume.setBook(book);
            });
        }
    }

    @GetMapping
    public ResponseEntity<List<BookModel>> findAll() {
        return ResponseEntity.ok(mapper.toBookModels(bookService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable("id") final Long id) {
        final BookModel model = mapper.toBookModel(bookService.findById(id));
        indx[0] = findAll().getBody().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("{id}/name")
    public ResponseEntity<String> getBookName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(bookService.findById(id).getBookName());
    }

    @GetMapping("first")
    public ResponseEntity<BookDTO> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("previous")
    public ResponseEntity<BookDTO> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("next")
    public ResponseEntity<BookDTO> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
    }

    @GetMapping("last")
    public ResponseEntity<BookDTO> last() {
        indx[0] = findAll().getBody().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> save(@RequestBody final BookModel model) {
        final BookEntity book = mapper.toBookEntity(model);
        setBookForVolumes(book);
        final BookEntity bookSaved = bookService.save(book);
        final BookModel savedModel = mapper.toBookModel(bookSaved);
        indx[0] = this.findAll().getBody().indexOf(savedModel);
        LOG.info("Index in saveBook(): {}", indx[0]);
        return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookEntity>> saveAll(@RequestBody final List<BookModel> models) {
        final List<BookEntity> books = mapper.toBookEntities(models);
        books.forEach(book -> {
            setBookForVolumes(book);
        });
        return ResponseEntity.ok(bookService.save(new HashSet<>(books)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookDTO> deleteById(@PathVariable("id") final Long id) {
        if (!bookService.exists(id)) {
            throw new IllegalArgumentException("BookEntity with id: " + id + " does not exist");
        } else {
            try {
                bookService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteBookById(): {}", indx[0]);
                return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> delete(@RequestBody final BookModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getBookId() || !bookService.exists(model.getBookId())) {
            throw new IllegalArgumentException("BookEntity does not exist");
        } else {
            try {
                bookService.delete(mapper.toBookEntity(model));
                indx[0]--;
                LOG.info("Index in deleteBook(): {}", indx[0]);
                return ResponseEntity.ok(getBookDTO(findAll().getBody(), indx[0]));
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

    private static final BookDTO getBookDTO(List<BookModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
            final BookModel model = new BookModel();
            return BookDTO.builder()
                    .book(model)
                    .navigationDtl(dtl)
                    .build();
        }
        if (indx < 0 || indx > models.size() - 1) {
            LOG.info("models.size(): {}", models.size());
            LOG.info("Index in getBookDTO(): {}", indx);
            throw new IndexOutOfBoundsException();
        } else {
            final BookModel model = models.get(indx);
            if (indx > 0) {
                dtl.setFirst(false);
            }
            if (indx < models.size() - 1) {
                dtl.setLast(false);
            }

            return BookDTO.builder()
                    .book(model)
                    .navigationDtl(dtl)
                    .build();
        }
    }
}
