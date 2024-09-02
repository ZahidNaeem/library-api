package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.BookExportToExcel;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, BookTransLineMapper.class, VolumeMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookMapper {

  @Mapping(target = "author", source = "author.id")
  @Mapping(target = "subject", source = "subject.id")
  @Mapping(target = "publisher", source = "publisher.id")
  @Mapping(target = "researcher", source = "researcher.id")
  @Mapping(target = "shelf", source = "shelf.id")
  BookModel toModel(final BookEntity book);

  @Mapping(target = "author", qualifiedByName = "authorMTE")
  @Mapping(target = "subject", qualifiedByName = "subjectMTE")
  @Mapping(target = "publisher", qualifiedByName = "publisherMTE")
  @Mapping(target = "researcher", qualifiedByName = "researcherMTE")
  @Mapping(target = "shelf", qualifiedByName = "shelfMTE")
  BookEntity toEntity(final BookModel model);

  @Mapping(target = "author", qualifiedByName = "authorMTX")
  @Mapping(target = "subject", qualifiedByName = "subjectMTX")
  @Mapping(target = "publisher", qualifiedByName = "publisherMTX")
  @Mapping(target = "researcher", qualifiedByName = "researcherMTX")
  @Mapping(target = "purchased", expression = "java(model != null ? model.getPurchased() == 1 ? \"purchased\" : model.getPurchased() == 0 ? \"Gifted\" : \"Other\" : null)")
  @Mapping(target = "volumes", expression = "java(model != null && model.getVolumes() != null ? model.getVolumes().size() : 0)")
  BookExportToExcel toExcel(final BookModel model);

  default List<BookModel> toModels(final List<BookEntity> Books) {
    if (CollectionUtils.isEmpty(Books)) {
      return new ArrayList<>();
    }
    final List<BookModel> models = new ArrayList<>();
    Books.forEach(BookEntity -> {
      models.add(this.toModel(BookEntity));
    });
    return models;
  }

  default List<BookEntity> toEntities(final List<BookModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<BookEntity> books = new ArrayList<>();
    models.forEach(model -> {
      books.add(this.toEntity(model));
    });
    return books;
  }

  default List<BookExportToExcel> toExcel(final List<BookModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<BookExportToExcel> books = new ArrayList<>();
    models.forEach(model -> {
      books.add(this.toExcel(model));
    });
    return books;
  }

  default ListWithPagination<BookEntity> toEntitiesWithPagination(
      final ListWithPagination<BookModel> models) {

    return ListWithPagination
        .<BookEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<BookModel> toModelsWithPagination(
      final ListWithPagination<BookEntity> entities) {
    return ListWithPagination
        .<BookModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
