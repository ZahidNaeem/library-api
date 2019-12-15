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

    @Mapping(target = "books", expression = "java(shelf != null ? mapBookEntitiesToBookModels(shelf.getBooks()) : null)")
    @Mapping(target = "racks", expression = "java(shelf != null ? mapRackEntitiesToRackModels(shelf.getRacks()) : null)")
    public abstract ShelfModel fromShelf(final ShelfEntity shelf);

    @Mapping(target = "books", expression = "java(model != null ? mapBookModelsToBookEntities(model.getBooks()) : null)")
    @Mapping(target = "racks", expression = "java(model != null ? mapRackModelsToRackEntities(model.getRacks()) : null)")
    public abstract ShelfEntity toShelf(final ShelfModel model);

    public List<BookModel> mapBookEntitiesToBookModels(final List<BookEntity> books) {
        return bookMapper.mapBookEntitiesToBookModels(books);
    }

    public List<BookEntity> mapBookModelsToBookEntities(final List<BookModel> models) {
        return bookMapper.mapBookModelsToBookEntities(models);
    }

    public List<RackModel> mapRackEntitiesToRackModels(final List<RackEntity> racks) {
        return rackMapper.mapRackEntitiesToRackModels(racks);
    }

    public List<RackEntity> mapRackModelsToRackEntities(final List<RackModel> models) {
        return rackMapper.mapRackModelsToRacks(models);
    }

    public List<ShelfModel> mapShelfEntitiesToShelfModels(final List<ShelfEntity> shelves) {
        if (CollectionUtils.isEmpty(shelves)) {
            return new ArrayList<>();
        }
        final List<ShelfModel> models = new ArrayList<>();
        shelves.forEach(shelf -> {
            models.add(this.fromShelf(shelf));
        });
        return models;
    }

    public List<ShelfEntity> mapShelfModelsToShelves(final List<ShelfModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ShelfEntity> shelves = new ArrayList<>();
        models.forEach(model -> {
            shelves.add(this.toShelf(model));
        });
        return shelves;
    }
}
