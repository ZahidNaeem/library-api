package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.BookTransLineModel;
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

  @Mapping(target = "bookTransHeader", qualifiedByName = "bookTransHeaderETM")
  @Mapping(target = "book", qualifiedByName = "bookETM")
  @Mapping(target = "volume", qualifiedByName = "volumeETM")
  BookTransLineModel toModel(final BookTransLineEntity bookTransLine);

  @Mapping(target = "bookTransHeader", qualifiedByName = "bookTransHeaderMTE")
  @Mapping(target = "book", qualifiedByName = "bookMTE")
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
}
