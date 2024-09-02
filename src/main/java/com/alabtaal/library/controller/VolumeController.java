package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.VolumeService;
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
@RequestMapping(value = "volumes")
@RequiredArgsConstructor
public class VolumeController {

  private static final Logger LOG = LoggerFactory.getLogger(VolumeController.class);

  private final VolumeService volumeService;
  private final VolumeMapper volumeMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<ListWithPagination<VolumeModel>>> findAll(
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException, InternalServerErrorException {
    final ListWithPagination<VolumeModel> volumes = volumeService.findAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDirection
    );
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<VolumeModel>>builder()
            .success(true)
            .message(
                "Got volumes successfully with pagination - Page Number: " + volumes.getPageNumber()
                    + " Page Size: " + volumes.getPageSize() + " Total Pages: "
                    + volumes.getTotalPages())
            .entity(volumes)
            .build());
  }

  @GetMapping(value = "/all")
  public ResponseEntity<ApiResponse<List<VolumeModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<VolumeModel>>builder()
            .success(true)
            .message("Got all volumes successfully")
            .entity(volumeService.findAll())
            .build());
  }

  @GetMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<VolumeModel>>> searchVolumes(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<VolumeModel> volumes = volumeService.searchVolumes(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<VolumeModel>>builder()
            .success(true)
            .message(
                "Got volumes filtered by search params successfully with pagination - Page Number: "
                    + volumes.getPageNumber()
                    + " Page Size: " + volumes.getPageSize() + " Total Pages: "
                    + volumes.getTotalPages())
            .entity(volumes)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<VolumeModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<VolumeModel>builder()
            .success(true)
            .message("Got volume by ID successfully")
            .entity(volumeService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<VolumeModel>> add(@RequestBody final VolumeModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<VolumeModel>builder()
            .success(true)
            .message("Volume added successfully")
            .entity(volumeService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<VolumeModel>> edit(@RequestBody final VolumeModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<VolumeModel>builder()
            .success(true)
            .message("Volume updated successfully")
            .entity(volumeService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    volumeService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Volume deleted successfully")
            .entity(null)
            .build());
  }
}
