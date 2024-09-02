package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.ReaderMapper;
import com.alabtaal.library.model.ReaderModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.ReaderService;
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
@RequestMapping(value = "readers")
@RequiredArgsConstructor
public class ReaderController {

  private static final Logger LOG = LoggerFactory.getLogger(ReaderController.class);

  private final ReaderService readerService;
  private final ReaderMapper readerMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<ListWithPagination<ReaderModel>>> findAll(
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException, InternalServerErrorException {
    final ListWithPagination<ReaderModel> readers = readerService.findAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDirection
    );
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<ReaderModel>>builder()
            .success(true)
            .message(
                "Got readers successfully with pagination - Page Number: " + readers.getPageNumber()
                    + " Page Size: " + readers.getPageSize() + " Total Pages: "
                    + readers.getTotalPages())
            .entity(readers)
            .build());
  }

  @GetMapping(value = "/all")
  public ResponseEntity<ApiResponse<List<ReaderModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<ReaderModel>>builder()
            .success(true)
            .message("Got all readers successfully")
            .entity(readerService.findAll())
            .build());
  }

  @GetMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<ReaderModel>>> searchReaders(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<ReaderModel> readers = readerService.searchReaders(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<ReaderModel>>builder()
            .success(true)
            .message(
                "Got readers filtered by search params successfully with pagination - Page Number: "
                    + readers.getPageNumber()
                    + " Page Size: " + readers.getPageSize() + " Total Pages: "
                    + readers.getTotalPages())
            .entity(readers)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<ReaderModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderModel>builder()
            .success(true)
            .message("Got reader by ID successfully")
            .entity(readerService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ReaderModel>> add(@RequestBody final ReaderModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderModel>builder()
            .success(true)
            .message("Reader added successfully")
            .entity(readerService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ReaderModel>> edit(@RequestBody final ReaderModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<ReaderModel>builder()
            .success(true)
            .message("Reader updated successfully")
            .entity(readerService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    readerService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Reader deleted successfully")
            .entity(null)
            .build());
  }
}
