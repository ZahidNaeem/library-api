package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.BookService;
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
@RequestMapping(value = "books")
@RequiredArgsConstructor
public class BookController {

  private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

  private final BookService bookService;
  private final BookMapper bookMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<ListWithPagination<BookModel>>> findAll(
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException, InternalServerErrorException {
    final ListWithPagination<BookModel> books = bookService.findAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDirection
    );
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<BookModel>>builder()
            .success(true)
            .message(
                "Got books successfully with pagination - Page Number: " + books.getPageNumber()
                    + " Page Size: " + books.getPageSize() + " Total Pages: "
                    + books.getTotalPages())
            .entity(books)
            .build());
  }

  @GetMapping(value = "/all")
  public ResponseEntity<ApiResponse<List<BookModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<BookModel>>builder()
            .success(true)
            .message("Got all books successfully")
            .entity(bookService.findAll())
            .build());
  }

  @GetMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<BookModel>>> searchBooks(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<BookModel> books = bookService.searchBooks(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<BookModel>>builder()
            .success(true)
            .message(
                "Got books filtered by search params successfully with pagination - Page Number: "
                    + books.getPageNumber()
                    + " Page Size: " + books.getPageSize() + " Total Pages: "
                    + books.getTotalPages())
            .entity(books)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<BookModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookModel>builder()
            .success(true)
            .message("Got book by ID successfully")
            .entity(bookService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BookModel>> add(@RequestBody final BookModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookModel>builder()
            .success(true)
            .message("Book added successfully")
            .entity(bookService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BookModel>> edit(@RequestBody final BookModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<BookModel>builder()
            .success(true)
            .message("Book updated successfully")
            .entity(bookService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    bookService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Book deleted successfully")
            .entity(null)
            .build());
  }
}
