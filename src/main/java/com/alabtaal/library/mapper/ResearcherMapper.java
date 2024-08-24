package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.ResearcherModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class},
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
