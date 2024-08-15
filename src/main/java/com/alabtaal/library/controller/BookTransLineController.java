package com.alabtaal.library.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.BookTransLineService;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("bookTransLines")
@RequiredArgsConstructor
public class BookTransLineController {

    private static final Logger LOG = LogManager.getLogger(BookTransLineController.class);

    private final BookTransLineService bookTransLineService;

    private final BookTransLineMapper mapper;

//    @Autowired
//    private BookTransHeaderService bookTransHeaderService;

    private List<BookTransLineModel> bookTransLineModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        bookTransLineModels = mapper.toModels(bookTransLineService.findAll());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookTransLineModel>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookTransLineModel>>builder()
                        .success(true)
                        .message("findAll response")
                        .entity(bookTransLineModels)
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<BookTransLineModel>> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransLineModel>builder()
                        .success(true)
                        .message("Book Transaction details deleted successfully")
                        .entity(mapper.toModel(bookTransLineService.findById(id)))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookTransLineModel>> save(@RequestBody final BookTransLineModel model) {
        final BookTransLineEntity savedBookTransLine = bookTransLineService.save(mapper.toEntity(model));
        return ResponseEntity.ok(
                ApiResponse
                        .<BookTransLineModel>builder()
                        .success(true)
                        .message("BookTransLine saved seccessfully")
                        .entity(mapper.toModel(savedBookTransLine))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookTransLineEntity>>> saveAll(@RequestBody final Set<BookTransLineModel> bookTransLineModel) {
        final Set<BookTransLineEntity> bookTransLines = new HashSet<>();
        bookTransLineModel.forEach(model -> {
            final BookTransLineEntity bookTransLine = mapper.toEntity(model);
            bookTransLines.add(bookTransLine);
        });
        return ResponseEntity.ok(
                ApiResponse
                        .<List<BookTransLineEntity>>builder()
                        .success(true)
                        .message("All bookTransLines saved seccessfully")
                        .entity(bookTransLineService.save(bookTransLines))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteById(@PathVariable("id") final Long id) {
        if (!bookTransLineService.exists(id)) {
            throw new IllegalArgumentException("Item bookTransLine with id: " + id + " does not exist");
        } else {
            try {
                bookTransLineService.deleteById(id);
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("BookTransLine deleted seccessfully")
                                .entity(true)
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Boolean>> delete(@RequestBody final BookTransLineModel model) {
        if (null == model || null == model.getLineId() || !bookTransLineService
                .exists(model.getLineId())) {
            throw new IllegalArgumentException("Item bookTransLine does not exist");
        } else {
            try {
                bookTransLineService.delete(mapper.toEntity(model));
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("BookTransLine deleted seccessfully")
                                .entity(true)
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }
}
