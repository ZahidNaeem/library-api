package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.SubjectModel;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class, BookMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface SubjectMapper {

  @Mapping(target = "parentSubject", source = "parentSubject.id")
  @Mapping(target = "parentSubjectName", source = "parentSubject.name")
  SubjectModel toModel(final SubjectEntity subject);

  @Mapping(target = "parentSubject", qualifiedByName = "parentSubjectMTE")
  SubjectEntity toEntity(final SubjectModel model);

  default List<SubjectModel> toModels(final List<SubjectEntity> subjects) {
    if (CollectionUtils.isEmpty(subjects)) {
      return new ArrayList<>();
    }
    final List<SubjectModel> models = new ArrayList<>();
    subjects.forEach(subject -> {
      models.add(this.toModel(subject));
    });
    return models;
  }

  default List<SubjectEntity> toEntities(final List<SubjectModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<SubjectEntity> subjects = new ArrayList<>();
    models.forEach(model -> {
      subjects.add(this.toEntity(model));
    });
    return subjects;
  }

  default ListWithPagination<SubjectEntity> toEntitiesWithPagination(
      final ListWithPagination<SubjectModel> models) {

    return ListWithPagination
        .<SubjectEntity>builder()
        .list(toEntities(models.getList()))
        .pageSize(models.getPageSize())
        .pageNumber(models.getPageNumber())
        .totalPages(models.getTotalPages())
        .totalPageRecords(models.getTotalPageRecords())
        .totalRecords(models.getTotalRecords())
        .build();
  }

  default ListWithPagination<SubjectModel> toModelsWithPagination(
      final ListWithPagination<SubjectEntity> entities) {
    return ListWithPagination
        .<SubjectModel>builder()
        .list(toModels(entities.getList()))
        .pageSize(entities.getPageSize())
        .pageNumber(entities.getPageNumber())
        .totalPages(entities.getTotalPages())
        .totalPageRecords(entities.getTotalPageRecords())
        .totalRecords(entities.getTotalRecords())
        .build();
  }
}
