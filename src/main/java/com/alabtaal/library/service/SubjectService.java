package com.alabtaal.library.service;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.model.SubjectModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

public interface SubjectService {

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  void refreshCachedModels();

  List<SubjectModel> findAll();

  ListWithPagination<SubjectModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  ListWithPagination<SubjectModel> searchSubjects(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException;

  SubjectModel findById(final UUID id);

  boolean exists(UUID id);

  SubjectModel add(SubjectModel model) throws BadRequestException;

  SubjectModel edit(SubjectModel model) throws BadRequestException;

  void deleteById(UUID id) throws BadRequestException;
}
