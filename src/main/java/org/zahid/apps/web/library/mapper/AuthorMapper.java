package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.AuthorEntity;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.model.AuthorModel;
import org.zahid.apps.web.library.model.BookModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Mapping(target = "books", expression = "java(author != null ? toBookModels(author.getBooks()) : null)")
    public abstract AuthorModel toAuthorModel(final AuthorEntity author);

    @Mapping(target = "books", expression = "java(model != null ? toBookEntities(model.getBooks()) : null)")
    public abstract AuthorEntity toAuthorEntity(final AuthorModel model);

    public List<BookModel> toBookModels(final List<BookEntity> books) {
        return bookMapper.toBookModels(books);
    }

    public List<BookEntity> toBookEntities(final List<BookModel> models) {
        return bookMapper.toBookEntities(models);
    }

    public List<AuthorModel> toAuthorModels(final List<AuthorEntity> authors) {
        if (CollectionUtils.isEmpty(authors)) {
            return new ArrayList<>();
        }
        final List<AuthorModel> models = new ArrayList<>();
        authors.forEach(author -> {
            models.add(this.toAuthorModel(author));
        });
        return models;
    }

    public List<AuthorEntity> toAuthorEntities(final List<AuthorModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<AuthorEntity> authors = new ArrayList<>();
        models.forEach(model -> {
            authors.add(this.toAuthorEntity(model));
        });
        return authors;
    }
}
