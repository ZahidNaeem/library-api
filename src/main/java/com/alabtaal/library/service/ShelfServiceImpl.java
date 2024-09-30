package com.alabtaal.library.service;

import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.ShelfMapper;
import com.alabtaal.library.model.ShelfModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.ShelfRepo;
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
public class ShelfServiceImpl implements ShelfService {

  private static final Logger LOG = LoggerFactory.getLogger(ShelfServiceImpl.class);
  private static List<ShelfModel> shelfModels = new ArrayList<>();
  private final ShelfRepo shelfRepo;
  private final ShelfMapper shelfMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    shelfModels = shelfMapper.toModels(shelfRepo.findAll());
  }

  @Override
  public List<ShelfModel> findAll() {
    return shelfModels;
  }

  public ListWithPagination<ShelfModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        shelfModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        ShelfModel.class);
  }

  public ListWithPagination<ShelfModel> searchShelves(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<ShelfModel> filteredModels = DynamicFilter.filter(
        shelfModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        ShelfModel.class);
  }

  @Override
  public ShelfModel findById(UUID id) {
    return shelfModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return shelfModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public ShelfModel add(ShelfModel model) throws BadRequestException {
    model.setId(null);
    final ShelfModel savedModel = save(model);
    shelfModels.add(savedModel);
    return savedModel;
  }

  @Override
  public ShelfModel edit(ShelfModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final ShelfModel savedModel = save(model);
    final Optional<ShelfModel> modelFound = shelfModels
        .stream()
        .filter(shelfModel -> shelfModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(shelfModel -> shelfModels.set(shelfModels.indexOf(shelfModel), savedModel));
    return savedModel;
  }

  private ShelfModel save(ShelfModel model) throws BadRequestException {
    final ShelfEntity entity = shelfMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
    RelationshipHandler.setManyToManyRelation(entity);
    Miscellaneous.constraintViolation(entity);
    return shelfMapper.toModel(shelfRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    shelfRepo.deleteById(id);
    shelfModels.removeIf(model -> model.getId().equals(id));
  }
}
