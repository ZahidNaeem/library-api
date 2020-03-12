package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.AuthorEntity;
import org.zahid.apps.web.library.mapper.qualifier.AuthorQualifier;
import org.zahid.apps.web.library.model.AuthorModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {AuthorQualifier.class},
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
