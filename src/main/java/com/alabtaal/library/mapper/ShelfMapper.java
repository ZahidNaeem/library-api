package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.ShelfModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, RackMapper.class, BookMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ShelfMapper {

  ShelfModel toModel(final ShelfEntity shelf);

  ShelfEntity toEntity(final ShelfModel model);

  default List<ShelfModel> toModels(final List<ShelfEntity> shelves) {
    if (CollectionUtils.isEmpty(shelves)) {
      return new ArrayList<>();
    }
    final List<ShelfModel> models = new ArrayList<>();
    shelves.forEach(shelf -> {
      models.add(this.toModel(shelf));
    });
    return models;
  }

  default List<ShelfEntity> toEntities(final List<ShelfModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<ShelfEntity> shelves = new ArrayList<>();
    models.forEach(model -> {
      shelves.add(this.toEntity(model));
    });
    return shelves;
  }

  default ListWithPagination<ShelfEntity> toEntitiesWithPagination(
      final ListWithPagination<ShelfModel> models) {

    return ListWithPagination
        .<ShelfEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<ShelfModel> toModelsWithPagination(
      final ListWithPagination<ShelfEntity> entities) {
    return ListWithPagination
        .<ShelfModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
