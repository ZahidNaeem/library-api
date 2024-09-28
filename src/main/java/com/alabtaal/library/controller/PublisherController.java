package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.PublisherModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.PublisherService;
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
@RequestMapping(value = "publishers")
@RequiredArgsConstructor
public class PublisherController {

  private static final Logger LOG = LoggerFactory.getLogger(PublisherController.class);

  private final PublisherService publisherService;

  @GetMapping(value = "refresh")
  public ResponseEntity<ApiResponse<String>> refresh() {
    publisherService.refreshCachedModels();
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("Refresh successful")
            .entity("Refresh successful")
            .build());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<PublisherModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<PublisherModel>>builder()
            .success(true)
            .message("Got all publishers successfully")
            .entity(publisherService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<PublisherModel>>> searchPublishers(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<PublisherModel> publishers = publisherService.searchPublishers(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<PublisherModel>>builder()
            .success(true)
            .message(
                "Got publishers filtered by search params successfully with pagination - Page Number: "
                    + publishers.getPageNumber()
                    + " Page Size: " + publishers.getPageSize() + " Total Pages: "
                    + publishers.getTotalPages())
            .entity(publishers)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<PublisherModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherModel>builder()
            .success(true)
            .message("Got publisher by ID successfully")
            .entity(publisherService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<PublisherModel>> add(@RequestBody final PublisherModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherModel>builder()
            .success(true)
            .message("Publisher added successfully")
            .entity(publisherService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<PublisherModel>> edit(@RequestBody final PublisherModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<PublisherModel>builder()
            .success(true)
            .message("Publisher updated successfully")
            .entity(publisherService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    publisherService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Publisher deleted successfully")
            .entity(null)
            .build());
  }
}
