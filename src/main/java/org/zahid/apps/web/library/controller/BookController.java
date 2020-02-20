package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.BookDTO;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.model.BookExportToExcel;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.payload.request.SearchBookRequest;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.payload.response.SearchBookResponse;
import org.zahid.apps.web.library.service.BookService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.zahid.apps.web.library.utils.Miscellaneous;

@RestController
@RequestMapping("books")
public class BookController {

    private static final Logger LOG = LogManager.getLogger(BookController.class);
    private List<BookModel> bookModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        bookModels = mapper.toBookModels(bookService.findAll());
    }

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
    public ResponseEntity<ApiResponse<List<BookModel>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookModel>>builder()
                        .success(true)
                        .message("findAll response")
                        .entity(bookModels)
                        .build()
        );
    }

    @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookModel>>> searchBook(@RequestBody final BookModel model) {
        bookModels = mapper.toBookModels(bookService.searchBook(mapper.toBookEntity(model)));
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookModel>>builder()
                        .success(true)
                        .message("searchBook response")
                        .entity(bookModels)
                        .build()
        );
    }

    @GetMapping(path = "excel/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookExportToExcel>>> toExcel() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookExportToExcel>>builder()
                        .success(true)
                        .message("toExcel response")
                        .entity(mapper.toExcel(bookModels))
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<BookDTO>> findById(@PathVariable("id") final Long id) {
        final BookModel model = mapper.toBookModel(bookService.findById(id));
        indx[0] = bookModels.indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookDTO>builder()
                        .success(true)
                        .message("findById response")
                        .entity(getBookDTO(bookModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("{id}/name")
    public ResponseEntity<ApiResponse<String>> getBookName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getBookName response")
                        .entity(bookService.findById(id).getBookName())
                        .build()
        );
    }

    @GetMapping("first")
    public ResponseEntity<ApiResponse<BookDTO>> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookDTO>builder()
                        .success(true)
                        .message("first record response")
                        .entity(getBookDTO(bookModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("previous")
    public ResponseEntity<ApiResponse<BookDTO>> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookDTO>builder()
                        .success(true)
                        .message("previous record response")
                        .entity(getBookDTO(bookModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("next")
    public ResponseEntity<ApiResponse<BookDTO>> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookDTO>builder()
                        .success(true)
                        .message("next record response")
                        .entity(getBookDTO(bookModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("last")
    public ResponseEntity<ApiResponse<BookDTO>> last() {
        indx[0] = bookModels.size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookDTO>builder()
                        .success(true)
                        .message("last record response")
                        .entity(getBookDTO(bookModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookDTO>> save(@RequestBody final BookModel model) {
        final BookEntity book = mapper.toBookEntity(model);
        setBookForVolumes(book);
        final BookEntity bookSaved = bookService.save(book);
        final BookModel savedModel = mapper.toBookModel(bookSaved);
        init();
        indx[0] = this.bookModels.indexOf(savedModel);
        LOG.info("Index in saveBook(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookDTO>builder()
                        .success(true)
                        .message("Book saved successfully")
                        .entity(getBookDTO(bookModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookEntity>>> saveAll(@RequestBody final List<BookModel> models) {
        final List<BookEntity> books = mapper.toBookEntities(models);
        books.forEach(book -> {
            setBookForVolumes(book);
        });
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookEntity>>builder()
                        .success(true)
                        .message("All books saved seccessfully")
                        .entity(bookService.save(new HashSet<>(books)))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<BookDTO>> deleteById(@PathVariable("id") final Long id) {
        if (!bookService.exists(id)) {
            throw new IllegalArgumentException("BookEntity with id: " + id + " does not exist");
        } else {
            try {
                bookService.deleteById(id);
                init();
                indx[0]--;
                LOG.info("Index in deleteBookById(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<BookDTO>builder()
                                .success(true)
                                .message("first record response")
                                .entity(getBookDTO(bookModels, indx[0]))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookDTO>> delete(@RequestBody final BookModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getBookId() || !bookService.exists(model.getBookId())) {
            throw new IllegalArgumentException("BookEntity does not exist");
        } else {
            try {
                bookService.delete(mapper.toBookEntity(model));
                init();
                indx[0]--;
                LOG.info("Index in deleteBook(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<BookDTO>builder()
                                .success(true)
                                .message("first record response")
                                .entity(getBookDTO(bookModels, indx[0]))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

//    @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse<List<SearchBookResponse>>> searchBookByCriteria(@RequestBody final SearchBookRequest request) {
//        LOG.info("Request: {}", request);
//        return ResponseEntity.ok(
//                ApiResponse
//                        .<List<SearchBookResponse>>builder()
//                        .success(true)
//                        .message("Book deleted seccessfully")
//                        .entity(bookService.searchByCriteria(request.getAuthor(), request.getSubject(), request.getPublisher(), request.getResearcher()))
//                        .build()
//        );
//    }

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
