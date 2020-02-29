package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.model.ShelfModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ShelfMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Autowired
    protected RackMapper rackMapper;

    @Mapping(target = "books", expression = "java(shelf != null ? bookMapper.toModels(shelf.getBooks()) : null)")
    @Mapping(target = "racks", expression = "java(shelf != null ? rackMapper.toModels(shelf.getRacks()) : null)")
    public abstract ShelfModel toModel(final ShelfEntity shelf);

    @Mapping(target = "books", expression = "java(model != null ? bookMapper.toEntities(model.getBooks()) : null)")
    @Mapping(target = "racks", expression = "java(model != null ? rackMapper.toEntities(model.getRacks()) : null)")
    public abstract ShelfEntity toEntity(final ShelfModel model);

    public List<ShelfModel> toModels(final List<ShelfEntity> shelves) {
        if (CollectionUtils.isEmpty(shelves)) {
            return new ArrayList<>();
        }
        final List<ShelfModel> models = new ArrayList<>();
        shelves.forEach(shelf -> {
            models.add(this.toModel(shelf));
        });
        return models;
    }

    public List<ShelfEntity> toEntities(final List<ShelfModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ShelfEntity> shelves = new ArrayList<>();
        models.forEach(model -> {
            shelves.add(this.toEntity(model));
        });
        return shelves;
    }
}
