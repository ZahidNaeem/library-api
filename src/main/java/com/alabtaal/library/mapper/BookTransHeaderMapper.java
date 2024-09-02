package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.BookTransHeaderModel;
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
public interface BookTransHeaderMapper {

  @Mapping(target = "reader", source = "reader.id")
  BookTransHeaderModel toModel(final BookTransHeaderEntity bookTransHeader);

  @Mapping(target = "reader", qualifiedByName = "readerMTE")
  BookTransHeaderEntity toEntity(final BookTransHeaderModel model);

  default List<BookTransHeaderModel> toModels(final List<BookTransHeaderEntity> bookTransHeaders) {
    if (CollectionUtils.isEmpty(bookTransHeaders)) {
      return new ArrayList<>();
    }
    final List<BookTransHeaderModel> models = new ArrayList<>();
    bookTransHeaders.forEach(bookTransHeader -> {
      models.add(this.toModel(bookTransHeader));
    });
    return models;
  }

  default List<BookTransHeaderEntity> toEntities(final List<BookTransHeaderModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<BookTransHeaderEntity> bookTransHeaders = new ArrayList<>();
    models.forEach(model -> {
      bookTransHeaders.add(this.toEntity(model));
    });
    return bookTransHeaders;
  }

  default ListWithPagination<BookTransHeaderEntity> toEntitiesWithPagination(
      final ListWithPagination<BookTransHeaderModel> models) {

    return ListWithPagination
        .<BookTransHeaderEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<BookTransHeaderModel> toModelsWithPagination(
      final ListWithPagination<BookTransHeaderEntity> entities) {
    return ListWithPagination
        .<BookTransHeaderModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
