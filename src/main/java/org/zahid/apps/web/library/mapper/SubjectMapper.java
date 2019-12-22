package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.model.BookModel;
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

  @Mapping(target = "books", expression = "java(subject != null ? toBookModels(subject.getBooks()) : null)")
  @Mapping(target = "parentSubjectId", expression = "java(subject != null && subject.getParentSubjectId() != null ? subject.getParentSubjectId().getSubjectId() : null)")
  public abstract SubjectModel toSubjectModel(final SubjectEntity subject);

  @Mapping(target = "books", expression = "java(model != null ? toBookEntities(model.getBooks()) : null)")
  @Mapping(target = "parentSubjectId", expression = "java(model != null && model.getParentSubjectId() != null ? subjectService.findById(model.getParentSubjectId()) : null)")
  public abstract SubjectEntity toSubjectEntity(final SubjectModel model);

  public List<BookModel> toBookModels(final List<BookEntity> books) {
    return bookMapper.toBookModels(books);
  }

  public List<BookEntity> toBookEntities(final List<BookModel> models) {
    return bookMapper.toBookEntities(models);
  }

  public List<SubjectModel> toSubjectModels(final List<SubjectEntity> subjects) {
    if (CollectionUtils.isEmpty(subjects)) {
      return new ArrayList<>();
    }
    final List<SubjectModel> models = new ArrayList<>();
    subjects.forEach(subject -> {
      models.add(this.toSubjectModel(subject));
    });
    return models;
  }

  public List<SubjectEntity> toSubjectEntities(final List<SubjectModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<SubjectEntity> subjects = new ArrayList<>();
    models.forEach(model -> {
      subjects.add(this.toSubjectEntity(model));
    });
    return subjects;
  }
}
