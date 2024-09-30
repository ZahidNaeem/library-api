package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.BookRepo;
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
public class BookServiceImpl implements BookService {

  private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);
  private static List<BookModel> bookModels = new ArrayList<>();
  private final BookRepo bookRepo;
  private final BookMapper bookMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    bookModels = bookMapper.toModels(bookRepo.findAll());
  }

  @Override
  public List<BookModel> findAll() {
    return bookModels;
  }

  public ListWithPagination<BookModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        bookModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        BookModel.class);
  }

  public ListWithPagination<BookModel> searchBooks(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<BookModel> filteredModels = DynamicFilter.filter(
        bookModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        BookModel.class);
  }

  @Override
  public BookModel findById(UUID id) {
    return bookModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return bookModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public BookModel add(final BookModel model) throws BadRequestException {
    model.setId(null);
    final BookModel savedModel = save(model);
    bookModels.add(savedModel);
    return savedModel;
  }

  @Override
  public BookModel edit(final BookModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final BookModel savedModel = save(model);
    final Optional<BookModel> modelFound = bookModels
        .stream()
        .filter(bookModel -> bookModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(bookModel -> bookModels.set(bookModels.indexOf(bookModel), savedModel));
    return savedModel;
  }

  private BookModel save(BookModel model) throws BadRequestException {
    final BookEntity entity = bookMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
    RelationshipHandler.setManyToManyRelation(entity);
    Miscellaneous.constraintViolation(entity);
    return bookMapper.toModel(bookRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    bookRepo.deleteById(id);
    bookModels.removeIf(model -> model.getId().equals(id));
  }
}
