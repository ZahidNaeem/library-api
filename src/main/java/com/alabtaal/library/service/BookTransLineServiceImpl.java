package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.BookTransLineRepo;
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
public class BookTransLineServiceImpl implements BookTransLineService {

  private static List<BookTransLineModel> bookTransLineModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(BookTransLineServiceImpl.class);

  private final BookTransLineRepo bookTransLineRepo;
  private final BookTransLineMapper bookTransLineMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  protected void findAllModels() {
    bookTransLineModels = bookTransLineMapper.toModels(bookTransLineRepo.findAll());
  }

  @Override
  public List<BookTransLineModel> findAll() {
    return bookTransLineModels;
  }

  public ListWithPagination<BookTransLineModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        bookTransLineModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        BookTransLineModel.class);
  }

  public ListWithPagination<BookTransLineModel> searchBookTransLines(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<BookTransLineModel> filteredModels = DynamicFilter.filter(
        bookTransLineModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        BookTransLineModel.class);
  }

  @Override
  public BookTransLineModel findById(UUID id) {
    return bookTransLineModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return bookTransLineModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public BookTransLineModel add(final BookTransLineModel model) throws BadRequestException {
    model.setId(null);
    final BookTransLineModel savedModel = save(model);
    bookTransLineModels.add(savedModel);
    return savedModel;
  }

  @Override
  public BookTransLineModel edit(final BookTransLineModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final BookTransLineModel savedModel = save(model);
    bookTransLineModels.set(bookTransLineModels.indexOf(model), savedModel);
    return savedModel;
  }

  private BookTransLineModel save(BookTransLineModel model) throws BadRequestException {
    final BookTransLineEntity entity = bookTransLineMapper.toEntity(model);
    Miscellaneous.constraintViolation(entity);
    return bookTransLineMapper.toModel(bookTransLineRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    bookTransLineRepo.deleteById(id);
    bookTransLineModels.removeIf(model -> model.getId().equals(id));
  }
}
