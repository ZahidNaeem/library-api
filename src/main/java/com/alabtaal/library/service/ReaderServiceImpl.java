package com.alabtaal.library.service;

import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.ReaderMapper;
import com.alabtaal.library.model.ReaderModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.ReaderRepo;
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
public class ReaderServiceImpl implements ReaderService {

  private static List<ReaderModel> readerModels = new ArrayList<>();

  private static final Logger LOG = LoggerFactory.getLogger(ReaderServiceImpl.class);

  private final ReaderRepo readerRepo;
  private final ReaderMapper readerMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  protected void findAllModels() {
    readerModels = readerMapper.toModels(readerRepo.findAll());
  }

  @Override
  public List<ReaderModel> findAll() {
    return readerModels;
  }

  public ListWithPagination<ReaderModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        readerModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        ReaderModel.class);
  }

  public ListWithPagination<ReaderModel> searchReaders(
      Map<String, Object>filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<ReaderModel> filteredModels = DynamicFilter.filter(
        readerModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        ReaderModel.class);
  }

  @Override
  public ReaderModel findById(UUID id) {
    return readerModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return readerModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public ReaderModel add(final ReaderModel model) throws BadRequestException {
    model.setId(null);
    final ReaderModel savedModel = save(model);
    readerModels.add(savedModel);
    return savedModel;
  }

  @Override
  public ReaderModel edit(final ReaderModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final ReaderModel savedModel = save(model);
    readerModels.set(readerModels.indexOf(model), savedModel);
    return savedModel;
  }

  private ReaderModel save(ReaderModel model) throws BadRequestException {
    final ReaderEntity entity = readerMapper.toEntity(model);
    Miscellaneous.constraintViolation(entity);
    return readerMapper.toModel(readerRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    readerRepo.deleteById(id);
    readerModels.removeIf(model -> model.getId().equals(id));
  }
}
