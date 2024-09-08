package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.RackService;
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
@RequestMapping(value = "racks")
@RequiredArgsConstructor
public class RackController {

  private static final Logger LOG = LoggerFactory.getLogger(RackController.class);

  private final RackService rackService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<RackModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<RackModel>>builder()
            .success(true)
            .message("Got all racks successfully")
            .entity(rackService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<RackModel>>> searchRacks(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<RackModel> racks = rackService.searchRacks(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<RackModel>>builder()
            .success(true)
            .message(
                "Got racks filtered by search params successfully with pagination - Page Number: "
                    + racks.getPageNumber()
                    + " Page Size: " + racks.getPageSize() + " Total Pages: "
                    + racks.getTotalPages())
            .entity(racks)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<RackModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<RackModel>builder()
            .success(true)
            .message("Got rack by ID successfully")
            .entity(rackService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<RackModel>> add(@RequestBody final RackModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<RackModel>builder()
            .success(true)
            .message("Rack added successfully")
            .entity(rackService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<RackModel>> edit(@RequestBody final RackModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<RackModel>builder()
            .success(true)
            .message("Rack updated successfully")
            .entity(rackService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    rackService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Rack deleted successfully")
            .entity(null)
            .build());
  }
}
