package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.PublisherEntity;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.PublisherModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PublisherMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Mapping(target = "books", expression = "java(publisher != null ? mapBookEntitiesToBookModels(publisher.getBooks()) : null)")
    public abstract PublisherModel fromPublisher(final PublisherEntity publisher);

    @Mapping(target = "books", expression = "java(model != null ? mapBookModelsToBookEntities(model.getBooks()) : null)")
    public abstract PublisherEntity toPublisher(final PublisherModel model);

    public List<BookModel> mapBookEntitiesToBookModels(final List<BookEntity> books) {
        return bookMapper.mapBookEntitiesToBookModels(books);
    }

    public List<BookEntity> mapBookModelsToBookEntities(final List<BookModel> models) {
        return bookMapper.mapBookModelsToBookEntities(models);
    }

    public List<PublisherModel> mapPublisherEntitiesToPublisherModels(final List<PublisherEntity> publishers) {
        if (CollectionUtils.isEmpty(publishers)) {
            return new ArrayList<>();
        }
        final List<PublisherModel> models = new ArrayList<>();
        publishers.forEach(publisher -> {
            models.add(this.fromPublisher(publisher));
        });
        return models;
    }

    public List<PublisherEntity> mapPublisherModelsToPublishers(final List<PublisherModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<PublisherEntity> publishers = new ArrayList<>();
        models.forEach(model -> {
            publishers.add(this.toPublisher(model));
        });
        return publishers;
    }
}
