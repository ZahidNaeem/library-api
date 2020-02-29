package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.ResearcherEntity;
import org.zahid.apps.web.library.model.ResearcherModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ResearcherMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Mapping(target = "books", expression = "java(researcher != null ? bookMapper.toModels(researcher.getBooks()) : null)")
    public abstract ResearcherModel toModel(final ResearcherEntity researcher);

    @Mapping(target = "books", expression = "java(model != null ? bookMapper.toEntities(model.getBooks()) : null)")
    public abstract ResearcherEntity toEntity(final ResearcherModel model);

    public List<ResearcherModel> toModels(final List<ResearcherEntity> researchers) {
        if (CollectionUtils.isEmpty(researchers)) {
            return new ArrayList<>();
        }
        final List<ResearcherModel> models = new ArrayList<>();
        researchers.forEach(researcher -> {
            models.add(this.toModel(researcher));
        });
        return models;
    }

    public List<ResearcherEntity> toEntities(final List<ResearcherModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ResearcherEntity> researchers = new ArrayList<>();
        models.forEach(model -> {
            researchers.add(this.toEntity(model));
        });
        return researchers;
    }
}
