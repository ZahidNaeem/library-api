package com.alabtaal.library.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alabtaal.library.dto.BookTransHeaderDTO;
import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.NavigationDtl;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.mapper.BookTransHeaderMapper;
import com.alabtaal.library.model.BookTransHeaderModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.BookTransHeaderService;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("bookTransHeaders/issuance")
public class BookTransHeaderIssuanceController extends BookTransHeaderController {

    private static final Logger LOG = LogManager.getLogger(BookTransHeaderIssuanceController.class);
    private List<BookTransHeaderModel> bookTransHeaderModels = new ArrayList<>();

    @Autowired
    public BookTransHeaderIssuanceController(final BookTransHeaderService bookTransHeaderService, final BookTransHeaderMapper mapper) {
        super(bookTransHeaderService, mapper);
    }

    @PostConstruct
    public void init() {
        bookTransHeaderModels = mapper.toModels(bookTransHeaderService.findAllByTransType("ISSUE"));
    }

    private final int[] indx = {-1};

    private static void setBookTransHeaderForBookTransLines(
            final BookTransHeaderEntity bookTransHeader) {
        if (CollectionUtils.isNotEmpty(bookTransHeader.getBookTransLines())) {
            bookTransHeader.getBookTransLines().forEach(bookTransLine -> {
                bookTransLine.setBookTransHeader(bookTransHeader);
            });
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookTransHeaderModel>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookTransHeaderModel>>builder()
                        .success(true)
                        .message("findAll response")
                        .entity(bookTransHeaderModels)
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> findById(
            @PathVariable("id") final Long id) {
        final BookTransHeaderModel model = mapper
                .toModel(bookTransHeaderService.findById(id));
        indx[0] = bookTransHeaderModels.indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransHeaderDTO>builder()
                        .success(true)
                        .message("findById response")
                        .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("first")
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransHeaderDTO>builder()
                        .success(true)
                        .message("first response")
                        .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("previous")
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransHeaderDTO>builder()
                        .success(true)
                        .message("previous response")
                        .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("next")
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransHeaderDTO>builder()
                        .success(true)
                        .message("next response")
                        .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("last")
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> last() {
        indx[0] = bookTransHeaderModels.size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransHeaderDTO>builder()
                        .success(true)
                        .message("last response")
                        .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> save(
            @RequestBody final BookTransHeaderModel model) {
        final BookTransHeaderEntity bookTransHeader = mapper.toEntity(model);
//    Below line added, because when converted from model to BookTransHeaderEntity, there is no bookTransHeader set in book list.
        setBookTransHeaderForBookTransLines(bookTransHeader);
        setBookTransHeaderForBookTransLines(bookTransHeader);
        final BookTransHeaderEntity bookTransHeaderSaved = bookTransHeaderService.save(bookTransHeader);
        final BookTransHeaderModel savedModel = mapper.toModel(bookTransHeaderSaved);
        init();
        indx[0] = this.bookTransHeaderModels.indexOf(savedModel);
        LOG.info("Index in saveBookTransHeader(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransHeaderDTO>builder()
                        .success(true)
                        .message("Record saved successfully")
                        .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookTransHeaderEntity>>> saveAll(
            @RequestBody final List<BookTransHeaderModel> models) {
        final List<BookTransHeaderEntity> bookTransHeaders = mapper.toEntities(models);
        //    Below line added, because when converted from model to BookTransHeaderEntity, there is no bookTransHeader set in book list.
        bookTransHeaders.forEach(bookTransHeader -> {
            setBookTransHeaderForBookTransLines(bookTransHeader);
            setBookTransHeaderForBookTransLines(bookTransHeader);
        });
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookTransHeaderEntity>>builder()
                        .success(true)
                        .message("All bookTransHeaders saved seccessfully")
                        .entity(bookTransHeaderService.save(new HashSet<>(bookTransHeaders)))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> deleteById(
            @PathVariable("id") final Long id) {
        if (!bookTransHeaderService.exists(id)) {
            throw new IllegalArgumentException(
                    "BookTransHeaderEntity with id: " + id + " does not exist");
        } else {
            try {
                bookTransHeaderService.deleteById(id);
                init();
                indx[0]--;
                LOG.info("Index in deleteBookTransHeaderById(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<BookTransHeaderDTO>builder()
                                .success(true)
                                .message("Book Transaction deleted successfully")
                                .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookTransHeaderDTO>> delete(
            @RequestBody final BookTransHeaderModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getHeaderId() || !bookTransHeaderService
                .exists(model.getHeaderId())) {
            throw new IllegalArgumentException("BookTransHeaderEntity does not exist");
        } else {
            try {
                bookTransHeaderService.delete(mapper.toEntity(model));
                init();
                indx[0]--;
                LOG.info("Index in deleteBookTransHeader(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<BookTransHeaderDTO>builder()
                                .success(true)
                                .message("Book Transaction deleted successfully")
                                .entity(getBookTransHeaderDTO(bookTransHeaderModels, indx[0]))
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

    private static final BookTransHeaderDTO getBookTransHeaderDTO(List<BookTransHeaderModel> models,
                                                                  int indx) {
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
