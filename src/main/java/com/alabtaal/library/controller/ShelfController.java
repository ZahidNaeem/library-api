package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.ShelfMapper;
import com.alabtaal.library.model.ShelfModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.ShelfService;
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
@RequestMapping(value = "shelves")
@RequiredArgsConstructor
public class ShelfController {

  private static final Logger LOG = LoggerFactory.getLogger(ShelfController.class);

  private final ShelfService shelfService;
  private final ShelfMapper shelfMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<ListWithPagination<ShelfModel>>> findAll(
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException, InternalServerErrorException {
    final ListWithPagination<ShelfModel> shelves = shelfService.findAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDirection
    );
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<ShelfModel>>builder()
            .success(true)
            .message(
                "Got shelves successfully with pagination - Page Number: " + shelves.getPageNumber()
                    + " Page Size: " + shelves.getPageSize() + " Total Pages: "
                    + shelves.getTotalPages())
            .entity(shelves)
            .build());
  }

  @GetMapping(value = "/all")
  public ResponseEntity<ApiResponse<List<ShelfModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<ShelfModel>>builder()
            .success(true)
            .message("Got all shelves successfully")
            .entity(shelfService.findAll())
            .build());
  }

  @GetMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<ShelfModel>>> searchShelves(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<ShelfModel> shelves = shelfService.searchShelves(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<ShelfModel>>builder()
            .success(true)
            .message(
                "Got shelves filtered by search params successfully with pagination - Page Number: "
                    + shelves.getPageNumber()
                    + " Page Size: " + shelves.getPageSize() + " Total Pages: "
                    + shelves.getTotalPages())
            .entity(shelves)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<ShelfModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<ShelfModel>builder()
            .success(true)
            .message("Got shelf by ID successfully")
            .entity(shelfService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ShelfModel>> add(@RequestBody final ShelfModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<ShelfModel>builder()
            .success(true)
            .message("Shelf added successfully")
            .entity(shelfService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ShelfModel>> edit(@RequestBody final ShelfModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<ShelfModel>builder()
            .success(true)
            .message("Shelf updated successfully")
            .entity(shelfService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    shelfService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Shelf deleted successfully")
            .entity(null)
            .build());
  }
}
