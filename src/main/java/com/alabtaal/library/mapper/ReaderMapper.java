package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.ReaderModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, BookTransHeaderMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ReaderMapper {

  ReaderModel toModel(final ReaderEntity reader);

  ReaderEntity toEntity(final ReaderModel model);

  default List<ReaderModel> toModels(final List<ReaderEntity> readers) {
    if (CollectionUtils.isEmpty(readers)) {
      return new ArrayList<>();
    }
    final List<ReaderModel> models = new ArrayList<>();
    readers.forEach(reader -> {
      models.add(this.toModel(reader));
    });
    return models;
  }

  default List<ReaderEntity> toEntities(final List<ReaderModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<ReaderEntity> readers = new ArrayList<>();
    models.forEach(model -> {
      readers.add(this.toEntity(model));
    });
    return readers;
  }

  default ListWithPagination<ReaderEntity> toEntitiesWithPagination(
      final ListWithPagination<ReaderModel> models) {

    return ListWithPagination
        .<ReaderEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<ReaderModel> toModelsWithPagination(
      final ListWithPagination<ReaderEntity> entities) {
    return ListWithPagination
        .<ReaderModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
