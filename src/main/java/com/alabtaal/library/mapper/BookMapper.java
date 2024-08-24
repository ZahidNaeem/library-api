package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.BookExportToExcel;
import com.alabtaal.library.model.BookModel;
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
public interface BookMapper {

  @Mapping(target = "author", qualifiedByName = "authorETM")
  @Mapping(target = "subject", qualifiedByName = "subjectETM")
  @Mapping(target = "publisher", qualifiedByName = "publisherETM")
  @Mapping(target = "researcher", qualifiedByName = "researcherETM")
  @Mapping(target = "shelf", qualifiedByName = "shelfETM")
  @Mapping(target = "volumes", qualifiedByName = "volumeModels")
  @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineModels")
  BookModel toModel(final BookEntity book);

  @Mapping(target = "author", qualifiedByName = "authorMTE")
  @Mapping(target = "subject", qualifiedByName = "subjectMTE")
  @Mapping(target = "publisher", qualifiedByName = "publisherMTE")
  @Mapping(target = "researcher", qualifiedByName = "researcherMTE")
  @Mapping(target = "shelf", qualifiedByName = "shelfMTE")
  @Mapping(target = "volumes", qualifiedByName = "volumeEntities")
  @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineEntities")
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

    /*public List<SearchBookResponse> toSearchBookResponses(final List<BookEntity> Books) {
        if (CollectionUtils.isEmpty(Books)) {
            return new ArrayList<>();
        }
        final List<SearchBookResponse> searchBookResponses = new ArrayList<>();
        Books.forEach(BookEntity -> {
            searchBookResponses.add(this.toSearchBookResponse(BookEntity));
        });
        return searchBookResponses;
    }*/
}
