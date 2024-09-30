package com.alabtaal.library.service;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.AuthorMapper;
import com.alabtaal.library.model.AuthorModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.AuthorRepo;
import com.alabtaal.library.util.DynamicFilter;
import com.alabtaal.library.util.ListToPageConverter;
import com.alabtaal.library.util.Miscellaneous;
import com.alabtaal.library.util.RelationshipHandler;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private static final Logger LOG = LoggerFactory.getLogger(AuthorServiceImpl.class);
  private static List<AuthorModel> authorModels = new ArrayList<>();
  private final AuthorRepo authorRepo;
  private final AuthorMapper authorMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    authorModels = authorMapper.toModels(authorRepo.findAll());
  }

  @Override
  public List<AuthorModel> findAll() {
    return authorModels;
  }

  public ListWithPagination<AuthorModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        authorModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        AuthorModel.class);
  }

  public ListWithPagination<AuthorModel> searchAuthors(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<AuthorModel> filteredModels = DynamicFilter.filter(
        authorModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        AuthorModel.class);
  }

  @Override
  public AuthorModel findById(UUID id) {
    return authorModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return authorModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public AuthorModel add(final AuthorModel model) throws BadRequestException {
    model.setId(null);
    final AuthorModel savedModel = save(model);
    authorModels.add(savedModel);
    return savedModel;
  }

  @Override
  public AuthorModel edit(final AuthorModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final AuthorModel savedModel = save(model);
    final Optional<AuthorModel> modelFound = authorModels
        .stream()
        .filter(authorModel -> authorModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(authorModel -> authorModels.set(authorModels.indexOf(authorModel), savedModel));
    return savedModel;
  }

  private AuthorModel save(AuthorModel model) throws BadRequestException {
    final AuthorEntity entity = authorMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
    RelationshipHandler.setManyToManyRelation(entity);
    Miscellaneous.constraintViolation(entity);
    return authorMapper.toModel(authorRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    authorRepo.deleteById(id);
    authorModels.removeIf(model -> model.getId().equals(id));
  }
}
