package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.PublisherEntity;
import org.zahid.apps.web.library.mapper.qualifier.PublisherQualifier;
import org.zahid.apps.web.library.model.PublisherModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {PublisherQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PublisherMapper {

    @Mapping(target = "books", qualifiedByName = "bookModels")
    PublisherModel toModel(final PublisherEntity publisher);

    @Mapping(target = "books", qualifiedByName = "bookEntities")
    PublisherEntity toEntity(final PublisherModel model);

    default List<PublisherModel> toModels(final List<PublisherEntity> publishers) {
        if (CollectionUtils.isEmpty(publishers)) {
            return new ArrayList<>();
        }
        final List<PublisherModel> models = new ArrayList<>();
        publishers.forEach(publisher -> {
            models.add(this.toModel(publisher));
        });
        return models;
    }

    default List<PublisherEntity> toEntities(final List<PublisherModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<PublisherEntity> publishers = new ArrayList<>();
        models.forEach(model -> {
            publishers.add(this.toEntity(model));
        });
        return publishers;
    }
}
