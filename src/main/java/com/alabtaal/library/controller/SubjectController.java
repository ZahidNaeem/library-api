package com.alabtaal.library.controller;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.SubjectMapper;
import com.alabtaal.library.model.SubjectModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.service.SubjectService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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
@RequestMapping(value = "subjects")
@RequiredArgsConstructor
public class SubjectController {

  private static final Logger LOG = LoggerFactory.getLogger(SubjectController.class);

  private final SubjectService subjectService;
  private final SubjectMapper subjectMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<ListWithPagination<SubjectModel>>> findAll(
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException, InternalServerErrorException {
    final ListWithPagination<SubjectModel> subjects = subjectService.findAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDirection
    );
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<SubjectModel>>builder()
            .success(true)
            .message(
                "Got subjects successfully with pagination - Page Number: " + subjects.getPageNumber()
                    + " Page Size: " + subjects.getPageSize() + " Total Pages: "
                    + subjects.getTotalPages())
            .entity(subjects)
            .build());
  }

  @GetMapping(value = "/all")
  public ResponseEntity<ApiResponse<List<SubjectModel>>> findAll()
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<List<SubjectModel>>builder()
            .success(true)
            .message("Got all subjects successfully")
            .entity(subjectService.findAll())
            .build());
  }

  @PostMapping(value = "/search")
  public ResponseEntity<ApiResponse<ListWithPagination<SubjectModel>>> searchSubjects(
      @RequestBody final Map<String, Object> filters,
      @RequestParam(required = false) final Integer pageNumber,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final String sortBy,
      @RequestParam(required = false) final String sortDirection)
      throws BadRequestException {
    final ListWithPagination<SubjectModel> subjects = subjectService.searchSubjects(filters,
        pageNumber, pageSize, sortBy, sortDirection);
    return ResponseEntity.ok(
        ApiResponse
            .<ListWithPagination<SubjectModel>>builder()
            .success(true)
            .message(
                "Got subjects filtered by search params successfully with pagination - Page Number: "
                    + subjects.getPageNumber()
                    + " Page Size: " + subjects.getPageSize() + " Total Pages: "
                    + subjects.getTotalPages())
            .entity(subjects)
            .build());
  }

  @GetMapping(value = "/parent-subjects")
  public ResponseEntity<ApiResponse<List<SubjectModel>>> findParentSubjects() {
    final List<SubjectModel> subjects = subjectService.findAll()
        .stream()
        .collect(Collectors.toMap(SubjectModel::getName, SubjectModel::getId))
        .entrySet()
        .stream()
        .map((entry) -> SubjectModel.builder().parentSubjectName(entry.getKey()).parentSubject(entry.getValue()).build())
        .toList();
    return ResponseEntity.ok(
        ApiResponse
            .<List<SubjectModel>>builder()
            .success(true)
            .message("Got parent subjects successfully")
            .entity(subjects)
            .build());
  }

  @GetMapping(value = "id/{id}")
  public ResponseEntity<ApiResponse<SubjectModel>> findById(
      @PathVariable(value = "id") final
      UUID id)
      throws InternalServerErrorException {
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectModel>builder()
            .success(true)
            .message("Got subject by ID successfully")
            .entity(subjectService.findById(id))
            .build());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<SubjectModel>> add(@RequestBody final SubjectModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectModel>builder()
            .success(true)
            .message("Subject added successfully")
            .entity(subjectService.add(model))
            .build());
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<SubjectModel>> edit(@RequestBody final SubjectModel model)
      throws BadRequestException {
    return ResponseEntity.ok(
        ApiResponse
            .<SubjectModel>builder()
            .success(true)
            .message("Subject updated successfully")
            .entity(subjectService.edit(model))
            .build());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestParam(value = "id") final UUID id)
      throws InternalServerErrorException, ResourceNotFoundException, BadRequestException {
    subjectService.deleteById(id);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("Subject deleted successfully")
            .entity(null)
            .build());
  }
}
