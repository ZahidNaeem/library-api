package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import com.alabtaal.library.repo.VolumeRepo;
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
public class VolumeServiceImpl implements VolumeService {

  private static final Logger LOG = LoggerFactory.getLogger(VolumeServiceImpl.class);
  private static List<VolumeModel> volumeModels = new ArrayList<>();
  private final VolumeRepo volumeRepo;
  private final VolumeMapper volumeMapper;
  private final BookMapper bookMapper;

  @EventListener(classes = ApplicationStartedEvent.class)
  @Transactional
  @Override
  public void refreshCachedModels() {
    volumeModels = volumeMapper.toModels(volumeRepo.findAll());
  }

  @Override
  public List<VolumeModel> findAll() {
    return volumeModels;
  }

  public ListWithPagination<VolumeModel> findAll(
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    return ListToPageConverter.convert(
        volumeModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        VolumeModel.class);
  }

  public ListWithPagination<VolumeModel> searchVolumes(
      Map<String, Object> filters,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection) throws BadRequestException {
    final List<VolumeModel> filteredModels = DynamicFilter.filter(
        volumeModels,
        filters
    );
    return ListToPageConverter.convert(
        filteredModels,
        pageNumber,
        pageSize,
        sortBy,
        sortDirection,
        VolumeModel.class);
  }

  @Override
  public VolumeModel findById(UUID id) {
    return volumeModels
        .stream()
        .filter(model -> model.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean exists(UUID id) {
    return volumeModels
        .stream()
        .anyMatch(model -> model.getId().equals(id));
  }

  @Override
  public VolumeModel add(VolumeModel model) throws BadRequestException {
    model.setId(null);
    final VolumeModel savedModel = save(model);
    volumeModels.add(savedModel);
    return savedModel;
  }

  @Override
  public VolumeModel edit(VolumeModel model) throws BadRequestException {
    if (!exists(model.getId())) {
      throw new BadRequestException("Record does not exist");
    }
    final VolumeModel savedModel = save(model);
    final Optional<VolumeModel> modelFound = volumeModels
        .stream()
        .filter(volumeModel -> volumeModel.getId().equals(savedModel.getId()))
        .findFirst();

    modelFound.ifPresent(volumeModel -> volumeModels.set(volumeModels.indexOf(volumeModel), savedModel));
    return savedModel;
  }

  private VolumeModel save(VolumeModel model) throws BadRequestException {
    final VolumeEntity entity = volumeMapper.toEntity(model);
    RelationshipHandler.setParentForChildren(entity);
    RelationshipHandler.setManyToManyRelation(entity);
    Miscellaneous.constraintViolation(entity);
    return volumeMapper.toModel(volumeRepo.saveAndFlush(entity));
  }

  @Override
  public void deleteById(UUID id) throws BadRequestException {
    if (!exists(id)) {
      throw new BadRequestException("Record does not exist");
    }
    volumeRepo.deleteById(id);
    volumeModels.removeIf(model -> model.getId().equals(id));
  }

  @Override
  public List<VolumeModel> findAllByBook(final BookModel model) {
    final BookEntity entity = bookMapper.toEntity(model);
    return volumeMapper.toModels(volumeRepo.findAllByBook(entity));
  }
}
