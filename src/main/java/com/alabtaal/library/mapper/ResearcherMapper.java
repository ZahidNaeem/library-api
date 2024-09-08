package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.ResearcherModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, BookMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ResearcherMapper {

  ResearcherModel toModel(final ResearcherEntity researcher);

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

  default ListWithPagination<ResearcherEntity> toEntitiesWithPagination(
      final ListWithPagination<ResearcherModel> models) {

    return ListWithPagination
        .<ResearcherEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<ResearcherModel> toModelsWithPagination(
      final ListWithPagination<ResearcherEntity> entities) {
    return ListWithPagination
        .<ResearcherModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
