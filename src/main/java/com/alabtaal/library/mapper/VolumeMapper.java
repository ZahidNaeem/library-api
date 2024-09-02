package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, BookTransLineMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface VolumeMapper {

  @Mapping(target = "book", source = "book.id")
  @Mapping(target = "rack", source = "rack.id")
  VolumeModel toModel(final VolumeEntity volume);

  @Mapping(target = "book", qualifiedByName = "bookMTE")
  @Mapping(target = "rack", qualifiedByName = "rackMTE")
  VolumeEntity toEntity(final VolumeModel model);

  default List<VolumeModel> toModels(final List<VolumeEntity> volumes) {
    if (CollectionUtils.isEmpty(volumes)) {
      return new ArrayList<>();
    }
    final List<VolumeModel> models = new ArrayList<>();
    volumes.forEach(volume -> {
      models.add(this.toModel(volume));
    });
    return models;
  }

  default List<VolumeEntity> toEntities(final List<VolumeModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<VolumeEntity> volumes = new ArrayList<>();
    models.forEach(model -> {
      volumes.add(this.toEntity(model));
    });
    return volumes;
  }

  default ListWithPagination<VolumeEntity> toEntitiesWithPagination(
      final ListWithPagination<VolumeModel> models) {

    return ListWithPagination
        .<VolumeEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<VolumeModel> toModelsWithPagination(
      final ListWithPagination<VolumeEntity> entities) {
    return ListWithPagination
        .<VolumeModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
