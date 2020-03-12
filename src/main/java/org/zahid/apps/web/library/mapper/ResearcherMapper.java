package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.ResearcherEntity;
import org.zahid.apps.web.library.mapper.qualifier.ResearcherQualifier;
import org.zahid.apps.web.library.model.ResearcherModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ResearcherQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ResearcherMapper {

    @Mapping(target = "books", qualifiedByName = "bookModels")
    ResearcherModel toModel(final ResearcherEntity researcher);

    @Mapping(target = "books", qualifiedByName = "bookEntities")
    ResearcherEntity toEntity(final ResearcherModel model);

    default List<ResearcherModel> toModels(final List<ResearcherEntity> researchers) {
        if (CollectionUtils.isEmpty(researchers)) {
            return new ArrayList<>();
        }
        final List<ResearcherModel> models = new ArrayList<>();
        researchers.forEach(researcher -> {
            models.add(this.toModel(researcher));
        });
        return models;
    }

    default List<ResearcherEntity> toEntities(final List<ResearcherModel> models) {
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
