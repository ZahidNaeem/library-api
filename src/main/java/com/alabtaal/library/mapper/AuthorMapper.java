package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.AuthorModel;
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
public interface AuthorMapper {

  @Mapping(target = "books", qualifiedByName = "bookModels")
  AuthorModel toModel(final AuthorEntity author);

  @Mapping(target = "books", qualifiedByName = "bookEntities")
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
}
