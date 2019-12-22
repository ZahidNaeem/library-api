package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.ResearcherEntity;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.ResearcherModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ResearcherMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Mapping(target = "books", expression = "java(researcher != null ? toBookModels(researcher.getBooks()) : null)")
    public abstract ResearcherModel toResearcherModel(final ResearcherEntity researcher);

    @Mapping(target = "books", expression = "java(model != null ? toBookEntities(model.getBooks()) : null)")
    public abstract ResearcherEntity toResearcherEntity(final ResearcherModel model);

    public List<BookModel> toBookModels(final List<BookEntity> books) {
        return bookMapper.toBookModels(books);
    }

    public List<BookEntity> toBookEntities(final List<BookModel> models) {
        return bookMapper.toBookEntities(models);
    }

    public List<ResearcherModel> toResearcherModels(final List<ResearcherEntity> researchers) {
        if (CollectionUtils.isEmpty(researchers)) {
            return new ArrayList<>();
        }
        final List<ResearcherModel> models = new ArrayList<>();
        researchers.forEach(researcher -> {
            models.add(this.toResearcherModel(researcher));
        });
        return models;
    }

    public List<ResearcherEntity> toResearcherEntities(final List<ResearcherModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ResearcherEntity> researchers = new ArrayList<>();
        models.forEach(model -> {
            researchers.add(this.toResearcherEntity(model));
        });
        return researchers;
    }
}
