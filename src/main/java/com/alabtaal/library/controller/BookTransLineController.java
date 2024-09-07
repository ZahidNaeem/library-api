package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.BookTransLineService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "bookTransLines")
@RequiredArgsConstructor
public class BookTransLineController {

  private static final Logger LOG = LoggerFactory.getLogger(BookTransLineController.class);

  private final BookTransLineService bookTransLineService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<BookTransLineModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<BookTransLineModel>>builder()
            .success(true)
            .message("Got all bookTransLines successfully")
            .entity(bookTransLineService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<BookTransLineModel>>> searchBookTransLines(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<BookTransLineModel> bookTransLines = bookTransLineService.searchBookTransLines(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<BookTransLineModel>>builder()
            .success(true)
            .message(
                "Got bookTransLines filtered by search params successfully with pagination - Page Number: "
                    + bookTransLines.getPageNumber()
                    + " Page Size: " + bookTransLines.getPageSize() + " Total Pages: "
                    + bookTransLines.getTotalPages())
            .entity(bookTransLines)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<BookTransLineModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookTransLineModel>builder()
            .success(true)
            .message("Got bookTransLine by ID successfully")
            .entity(bookTransLineService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BookTransLineModel>> add(@RequestBody final BookTransLineModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookTransLineModel>builder()
            .success(true)
            .message("BookTransLine added successfully")
            .entity(bookTransLineService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BookTransLineModel>> edit(@RequestBody final BookTransLineModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookTransLineModel>builder()
            .success(true)
            .message("BookTransLine updated successfully")
            .entity(bookTransLineService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    bookTransLineService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("BookTransLine deleted successfully")
            .entity(null)
            .build());
  }
}
