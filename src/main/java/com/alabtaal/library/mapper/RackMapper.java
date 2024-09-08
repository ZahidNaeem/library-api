package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, VolumeMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RackMapper {

  @Mapping(target = "shelf", source = "shelf.id")
  @Mapping(target = "shelfName", source = "shelf.name")
  RackModel toModel(final RackEntity rack);

  @Mapping(target = "shelf", qualifiedByName = "shelfMTE")
  RackEntity toEntity(final RackModel model);

  default List<RackModel> toModels(final List<RackEntity> racks) {
    if (CollectionUtils.isEmpty(racks)) {
      return new ArrayList<>();
    }
    final List<RackModel> models = new ArrayList<>();
    racks.forEach(rack -> {
      models.add(this.toModel(rack));
    });
    return models;
  }

  default List<RackEntity> toEntities(final List<RackModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<RackEntity> racks = new ArrayList<>();
    models.forEach(model -> {
      racks.add(this.toEntity(model));
    });
    return racks;
  }

  default ListWithPagination<RackEntity> toEntitiesWithPagination(
      final ListWithPagination<RackModel> models) {

    return ListWithPagination
        .<RackEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<RackModel> toModelsWithPagination(
      final ListWithPagination<RackEntity> entities) {
    return ListWithPagination
        .<RackModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
