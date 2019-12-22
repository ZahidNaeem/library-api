package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.RackModel;
import org.zahid.apps.web.library.model.ShelfModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ShelfMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Autowired
    protected RackMapper rackMapper;

    @Mapping(target = "books", expression = "java(shelf != null ? toBookModels(shelf.getBooks()) : null)")
    @Mapping(target = "racks", expression = "java(shelf != null ? toRackModels(shelf.getRacks()) : null)")
    public abstract ShelfModel toShelfModel(final ShelfEntity shelf);

    @Mapping(target = "books", expression = "java(model != null ? toBookEntities(model.getBooks()) : null)")
    @Mapping(target = "racks", expression = "java(model != null ? toRackEntities(model.getRacks()) : null)")
    public abstract ShelfEntity toShelfEntity(final ShelfModel model);

    public List<BookModel> toBookModels(final List<BookEntity> books) {
        return bookMapper.toBookModels(books);
    }

    public List<BookEntity> toBookEntities(final List<BookModel> models) {
        return bookMapper.toBookEntities(models);
    }

    public List<RackModel> toRackModels(final List<RackEntity> racks) {
        return rackMapper.toRackModels(racks);
    }

    public List<RackEntity> toRackEntities(final List<RackModel> models) {
        return rackMapper.toRackEntities(models);
    }

    public List<ShelfModel> toShelfModels(final List<ShelfEntity> shelves) {
        if (CollectionUtils.isEmpty(shelves)) {
            return new ArrayList<>();
        }
        final List<ShelfModel> models = new ArrayList<>();
        shelves.forEach(shelf -> {
            models.add(this.toShelfModel(shelf));
        });
        return models;
    }

    public List<ShelfEntity> toShelfEntities(final List<ShelfModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ShelfEntity> shelves = new ArrayList<>();
        models.forEach(model -> {
            shelves.add(this.toShelfEntity(model));
        });
        return shelves;
    }
}
