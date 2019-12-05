package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.model.ShelfModel;
import org.zahid.apps.web.library.model.BookModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ShelfMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Mapping(target = "books", expression = "java(shelf != null ? mapBookEntitiesToBookModels(shelf.getBooks()) : null)")
    public abstract ShelfModel fromShelf(final ShelfEntity shelf);

    @Mapping(target = "books", expression = "java(model != null ? mapBookModelsToBookEntities(model.getBooks()) : null)")
    public abstract ShelfEntity toShelf(final ShelfModel model);

    public List<BookModel> mapBookEntitiesToBookModels(final List<BookEntity> books) {
        return bookMapper.mapBookEntitiesToBookModels(books);
    }

    public List<BookEntity> mapBookModelsToBookEntities(final List<BookModel> models) {
        return bookMapper.mapBookModelsToBookEntities(models);
    }

    public List<ShelfModel> mapShelfEntitiesToShelfModels(final List<ShelfEntity> shelfs) {
        if (CollectionUtils.isEmpty(shelfs)) {
            return new ArrayList<>();
        }
        final List<ShelfModel> models = new ArrayList<>();
        shelfs.forEach(shelf -> {
            models.add(this.fromShelf(shelf));
        });
        return models;
    }

    public List<ShelfEntity> mapShelfModelsToShelfs(final List<ShelfModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ShelfEntity> shelfs = new ArrayList<>();
        models.forEach(model -> {
            shelfs.add(this.toShelf(model));
        });
        return shelfs;
    }
}
