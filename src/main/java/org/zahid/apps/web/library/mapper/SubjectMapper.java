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
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper {

    @Autowired
    protected BookMapper bookMapper;

    @Autowired
    protected SubjectService subjectService;

    @Mapping(target = "books", expression = "java(subject != null ? mapBookEntitiesToBookModels(subject.getBooks()) : null)")
    public abstract SubjectModel fromSubject(final SubjectEntity subject);

    @Mapping(target = "books", expression = "java(model != null ? mapBookModelsToBookEntities(model.getBooks()) : null)")
    public abstract SubjectEntity toSubject(final SubjectModel model);

    public List<BookModel> mapBookEntitiesToBookModels(final List<BookEntity> books) {
        return bookMapper.mapBookEntitiesToBookModels(books);
    }

    public List<BookEntity> mapBookModelsToBookEntities(final List<BookModel> models) {
        return bookMapper.mapBookModelsToBookEntities(models);
    }

    public List<SubjectModel> mapSubjectEntitiesToSubjectModels(final List<SubjectEntity> subjects) {
        if (CollectionUtils.isEmpty(subjects)) {
            return new ArrayList<>();
        }
        final List<SubjectModel> models = new ArrayList<>();
        subjects.forEach(subject -> {
            models.add(this.fromSubject(subject));
        });
        return models;
    }

    public List<SubjectEntity> mapSubjectModelsToSubjects(final List<SubjectModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<SubjectEntity> subjects = new ArrayList<>();
        models.forEach(model -> {
            subjects.add(this.toSubject(model));
        });
        return subjects;
    }

    public long map(SubjectEntity value) {
        return value != null ? value.getSubjectId() : null;
    }

    public SubjectEntity map(Long value) {
        return value != null ? subjectService.findById(value) : null;
    }
}
