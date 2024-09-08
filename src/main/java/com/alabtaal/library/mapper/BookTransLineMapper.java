package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookTransLineMapper {

  @Mapping(target = "bookTransHeader", source = "bookTransHeader.id")
  @Mapping(target = "volume", source = "volume.id")
  @Mapping(target = "volumeName",expression = "java(bookTransLine.getVolume().getBook().getName() + \" - V. \" + bookTransLine.getVolume().getName())")
  BookTransLineModel toModel(final BookTransLineEntity bookTransLine);

  @Mapping(target = "bookTransHeader", qualifiedByName = "bookTransHeaderMTE")
  @Mapping(target = "volume", qualifiedByName = "volumeMTE")
  BookTransLineEntity toEntity(final BookTransLineModel model);

  default List<BookTransLineModel> toModels(final List<BookTransLineEntity> bookTransLines) {
    if (CollectionUtils.isEmpty(bookTransLines)) {
      return new ArrayList<>();
    }
    final List<BookTransLineModel> models = new ArrayList<>();
    bookTransLines.forEach(bookTransLine -> {
      models.add(this.toModel(bookTransLine));
    });
    return models;
  }

  default List<BookTransLineEntity> toEntities(final List<BookTransLineModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<BookTransLineEntity> bookTransLines = new ArrayList<>();
    models.forEach(model -> {
      bookTransLines.add(this.toEntity(model));
    });
    return bookTransLines;
  }

  default ListWithPagination<BookTransLineEntity> toEntitiesWithPagination(
      final ListWithPagination<BookTransLineModel> models) {

    return ListWithPagination
        .<BookTransLineEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<BookTransLineModel> toModelsWithPagination(
      final ListWithPagination<BookTransLineEntity> entities) {
    return ListWithPagination
        .<BookTransLineModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
