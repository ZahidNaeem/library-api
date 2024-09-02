package com.alabtaal.library.service;

import static java.util.stream.Collectors.toList;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.enumeration.TransType;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.BookTransHeaderMapper;
import com.alabtaal.library.model.BookTransHeaderModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.BookTransHeaderRepo;
import com.alabtaal.library.util.DynamicFilter;
import com.alabtaal.library.util.ListToPageConverter;
import com.alabtaal.library.util.Miscellaneous;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTransHeaderServiceImpl implements BookTransHeaderService {

  private static List<BookTransHeaderModel> bookTransHeaderModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(BookTransHeaderServiceImpl.class);

  private final BookTransHeaderRepo bookTransHeaderRepo;
  private final BookTransHeaderMapper bookTransHeaderMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  protected void findAllModels() {
    bookTransHeaderModels = bookTransHeaderMapper.toModels(bookTransHeaderRepo.findAll());
  }

  @Override
  public List<BookTransHeaderModel> findAll() {
    return bookTransHeaderModels;
  }

  public ListWithPagination<BookTransHeaderModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        bookTransHeaderModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        BookTransHeaderModel.class);
  }

  public ListWithPagination<BookTransHeaderModel> searchBookTransHeaders(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<BookTransHeaderModel> filteredModels = DynamicFilter.filter(
        bookTransHeaderModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        BookTransHeaderModel.class);
  }

  @Override
  public BookTransHeaderModel findById(UUID id) {
    return bookTransHeaderModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return bookTransHeaderModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public BookTransHeaderModel add(final BookTransHeaderModel model) throws BadRequestException {
    model.setId(null);
    final BookTransHeaderModel savedModel = save(model);
    bookTransHeaderModels.add(savedModel);
    return savedModel;
  }

  @Override
  public BookTransHeaderModel edit(final BookTransHeaderModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final BookTransHeaderModel savedModel = save(model);
    bookTransHeaderModels.set(bookTransHeaderModels.indexOf(model), savedModel);
    return savedModel;
  }

  private BookTransHeaderModel save(BookTransHeaderModel model) throws BadRequestException {
    final BookTransHeaderEntity entity = bookTransHeaderMapper.toEntity(model);
    Miscellaneous.constraintViolation(entity);
    return bookTransHeaderMapper.toModel(bookTransHeaderRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    bookTransHeaderRepo.deleteById(id);
    bookTransHeaderModels.removeIf(model -> model.getId().equals(id));
  }

  @Override
  public List<BookTransHeaderModel> findAllByTransType(TransType transType) {
    return bookTransHeaderModels
        .stream()
        .filter(model -> model.getTransType().equals(transType))
        .toList();
  }
}
