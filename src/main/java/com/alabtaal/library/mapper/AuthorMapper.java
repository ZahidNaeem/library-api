package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.AuthorModel;
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
public interface AuthorMapper {

  AuthorModel toModel(final AuthorEntity author);

  AuthorEntity toEntity(final AuthorModel model);

  default List<AuthorModel> toModels(final List<AuthorEntity> authors) {
    if (CollectionUtils.isEmpty(authors)) {
      return new ArrayList<>();
    }
    final List<AuthorModel> models = new ArrayList<>();
    authors.forEach(author -> {
      models.add(this.toModel(author));
    });
    return models;
  }

  default List<AuthorEntity> toEntities(final List<AuthorModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<AuthorEntity> authors = new ArrayList<>();
    models.forEach(model -> {
      authors.add(this.toEntity(model));
    });
    return authors;
  }

  default ListWithPagination<AuthorEntity> toEntitiesWithPagination(
      final ListWithPagination<AuthorModel> models) {

    return ListWithPagination
        .<AuthorEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<AuthorModel> toModelsWithPagination(
      final ListWithPagination<AuthorEntity> entities) {
    return ListWithPagination
        .<AuthorModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
