package com.alabtaal.library.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.mapper.qualifier.ShelfQualifier;
import com.alabtaal.library.model.ShelfModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ShelfQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ShelfMapper {

    @Mapping(target = "books", qualifiedByName = "bookModels")
    @Mapping(target = "racks", qualifiedByName = "rackModels")
    ShelfModel toModel(final ShelfEntity shelf);

    @Mapping(target = "books", qualifiedByName = "bookEntities")
    @Mapping(target = "racks", qualifiedByName = "rackEntities")
    ShelfEntity toEntity(final ShelfModel model);

    default List<ShelfModel> toModels(final List<ShelfEntity> shelves) {
        if (CollectionUtils.isEmpty(shelves)) {
            return new ArrayList<>();
        }
        final List<ShelfModel> models = new ArrayList<>();
        shelves.forEach(shelf -> {
            models.add(this.toModel(shelf));
        });
        return models;
    }

    default List<ShelfEntity> toEntities(final List<ShelfModel> models) {
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
