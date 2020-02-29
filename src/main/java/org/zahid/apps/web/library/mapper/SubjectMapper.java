package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.model.SubjectModel;
import org.zahid.apps.web.library.service.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper {

  @Autowired
  protected BookMapper bookMapper;

  @Autowired
  protected SubjectService subjectService;

  @Mapping(target = "books", expression = "java(subject != null ? bookMapper.toModels(subject.getBooks()) : null)")
  @Mapping(target = "parentSubjectId", expression = "java(subject != null && subject.getParentSubjectId() != null ? subject.getParentSubjectId().getSubjectId() : null)")
  public abstract SubjectModel toModel(final SubjectEntity subject);

  @Mapping(target = "books", expression = "java(model != null ? bookMapper.toEntities(model.getBooks()) : null)")
  @Mapping(target = "parentSubjectId", expression = "java(model != null && model.getParentSubjectId() != null ? subjectService.findById(model.getParentSubjectId()) : null)")
  public abstract SubjectEntity toEntity(final SubjectModel model);

  public List<SubjectModel> toModels(final List<SubjectEntity> subjects) {
    if (CollectionUtils.isEmpty(subjects)) {
      return new ArrayList<>();
    }
    final List<SubjectModel> models = new ArrayList<>();
    subjects.forEach(subject -> {
      models.add(this.toModel(subject));
    });
    return models;
  }

  public List<SubjectEntity> toEntities(final List<SubjectModel> models) {
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
