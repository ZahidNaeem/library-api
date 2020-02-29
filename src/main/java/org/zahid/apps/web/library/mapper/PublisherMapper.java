package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.PublisherEntity;
import org.zahid.apps.web.library.model.PublisherModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PublisherMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Mapping(target = "books", expression = "java(publisher != null ? bookMapper.toModels(publisher.getBooks()) : null)")
    public abstract PublisherModel toModel(final PublisherEntity publisher);

    @Mapping(target = "books", expression = "java(model != null ? bookMapper.toEntities(model.getBooks()) : null)")
    public abstract PublisherEntity toEntity(final PublisherModel model);

    public List<PublisherModel> toModels(final List<PublisherEntity> publishers) {
        if (CollectionUtils.isEmpty(publishers)) {
            return new ArrayList<>();
        }
        final List<PublisherModel> models = new ArrayList<>();
        publishers.forEach(publisher -> {
            models.add(this.toModel(publisher));
        });
        return models;
    }

    public List<PublisherEntity> toEntities(final List<PublisherModel> models) {
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
