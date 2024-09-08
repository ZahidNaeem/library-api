package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.AuthorModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.AuthorService;
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
@RequestMapping(value = "authors")
@RequiredArgsConstructor
public class AuthorController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

  private final AuthorService authorService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<AuthorModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<AuthorModel>>builder()
            .success(true)
            .message("Got all authors successfully")
            .entity(authorService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<AuthorModel>>> searchAuthors(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<AuthorModel> authors = authorService.searchAuthors(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<AuthorModel>>builder()
            .success(true)
            .message(
                "Got authors filtered by search params successfully with pagination - Page Number: "
                    + authors.getPageNumber()
                    + " Page Size: " + authors.getPageSize() + " Total Pages: "
                    + authors.getTotalPages())
            .entity(authors)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<AuthorModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorModel>builder()
            .success(true)
            .message("Got author by ID successfully")
            .entity(authorService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<AuthorModel>> add(@RequestBody final AuthorModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorModel>builder()
            .success(true)
            .message("Author added successfully")
            .entity(authorService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<AuthorModel>> edit(@RequestBody final AuthorModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorModel>builder()
            .success(true)
            .message("Author updated successfully")
            .entity(authorService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    authorService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Author deleted successfully")
            .entity(null)
            .build());
  }
}
