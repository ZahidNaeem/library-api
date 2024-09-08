package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.ResearcherModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.ResearcherService;
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
@RequestMapping(value = "researchers")
@RequiredArgsConstructor
public class ResearcherController {

  private static final Logger LOG = LoggerFactory.getLogger(ResearcherController.class);

  private final ResearcherService researcherService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<ResearcherModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<ResearcherModel>>builder()
            .success(true)
            .message("Got all researchers successfully")
            .entity(researcherService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<ResearcherModel>>> searchResearchers(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<ResearcherModel> researchers = researcherService.searchResearchers(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<ResearcherModel>>builder()
            .success(true)
            .message(
                "Got researchers filtered by search params successfully with pagination - Page Number: "
                    + researchers.getPageNumber()
                    + " Page Size: " + researchers.getPageSize() + " Total Pages: "
                    + researchers.getTotalPages())
            .entity(researchers)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<ResearcherModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherModel>builder()
            .success(true)
            .message("Got researcher by ID successfully")
            .entity(researcherService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ResearcherModel>> add(@RequestBody final ResearcherModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherModel>builder()
            .success(true)
            .message("Researcher added successfully")
            .entity(researcherService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<ResearcherModel>> edit(@RequestBody final ResearcherModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<ResearcherModel>builder()
            .success(true)
            .message("Researcher updated successfully")
            .entity(researcherService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    researcherService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Researcher deleted successfully")
            .entity(null)
            .build());
  }
}
