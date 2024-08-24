package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.BookTransHeaderModel;
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
public interface BookTransHeaderMapper {

  @Mapping(target = "reader", qualifiedByName = "readerETM")
  @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineModels")
  BookTransHeaderModel toModel(final BookTransHeaderEntity bookTransHeader);

  @Mapping(target = "reader", qualifiedByName = "readerMTE")
  @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineEntities")
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
}
