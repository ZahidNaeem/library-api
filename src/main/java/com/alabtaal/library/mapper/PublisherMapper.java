package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.PublisherModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, BookMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PublisherMapper {

  PublisherModel toModel(final PublisherEntity publisher);

  PublisherEntity toEntity(final PublisherModel model);

  default List<PublisherModel> toModels(final List<PublisherEntity> publishers) {
    if (CollectionUtils.isEmpty(publishers)) {
      return new ArrayList<>();
    }
    final List<PublisherModel> models = new ArrayList<>();
    publishers.forEach(publisher -> {
      models.add(this.toModel(publisher));
    });
    return models;
  }

  default List<PublisherEntity> toEntities(final List<PublisherModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<PublisherEntity> publishers = new ArrayList<>();
    models.forEach(model -> {
      publishers.add(this.toEntity(model));
    });
    return publishers;
  }

  default ListWithPagination<PublisherEntity> toEntitiesWithPagination(
      final ListWithPagination<PublisherModel> models) {

    return ListWithPagination
        .<PublisherEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<PublisherModel> toModelsWithPagination(
      final ListWithPagination<PublisherEntity> entities) {
    return ListWithPagination
        .<PublisherModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
