package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.BookTransHeaderModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.BookTransHeaderService;
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
@RequestMapping(value = "bookTransHeaders")
@RequiredArgsConstructor
public class BookTransHeaderController {

  private static final Logger LOG = LoggerFactory.getLogger(BookTransHeaderController.class);

  private final BookTransHeaderService bookTransHeaderService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<BookTransHeaderModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<BookTransHeaderModel>>builder()
            .success(true)
            .message("Got all bookTransHeaders successfully")
            .entity(bookTransHeaderService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<BookTransHeaderModel>>> searchBookTransHeaders(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<BookTransHeaderModel> bookTransHeaders = bookTransHeaderService.searchBookTransHeaders(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<BookTransHeaderModel>>builder()
            .success(true)
            .message(
                "Got bookTransHeaders filtered by search params successfully with pagination - Page Number: "
                    + bookTransHeaders.getPageNumber()
                    + " Page Size: " + bookTransHeaders.getPageSize() + " Total Pages: "
                    + bookTransHeaders.getTotalPages())
            .entity(bookTransHeaders)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<BookTransHeaderModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookTransHeaderModel>builder()
            .success(true)
            .message("Got bookTransHeader by ID successfully")
            .entity(bookTransHeaderService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BookTransHeaderModel>> add(@RequestBody final BookTransHeaderModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookTransHeaderModel>builder()
            .success(true)
            .message("BookTransHeader added successfully")
            .entity(bookTransHeaderService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BookTransHeaderModel>> edit(@RequestBody final BookTransHeaderModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookTransHeaderModel>builder()
            .success(true)
            .message("BookTransHeader updated successfully")
            .entity(bookTransHeaderService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    bookTransHeaderService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("BookTransHeader deleted successfully")
            .entity(null)
            .build());
  }
}
