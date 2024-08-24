package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.SubjectModel;
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
public interface SubjectMapper {

  @Mapping(target = "books", qualifiedByName = "bookModels")
  @Mapping(target = "parentSubject", qualifiedByName = "parentSubjectETM")
  SubjectModel toModel(final SubjectEntity subject);

  @Mapping(target = "books", qualifiedByName = "bookEntities")
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
}
