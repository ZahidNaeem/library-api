package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.mapper.qualifier.SubjectQualifier;
import org.zahid.apps.web.library.model.SubjectModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {SubjectQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface SubjectMapper {

    @Mapping(target = "books", qualifiedByName = "bookModels")
    @Mapping(target = "parentSubjectId", expression = "java(subject != null && subject.getParentSubjectId() != null ? subject.getParentSubjectId().getSubjectId() : null)")
    SubjectModel toModel(final SubjectEntity subject);

    @Mapping(target = "books", qualifiedByName = "bookEntities")
    @Mapping(target = "parentSubjectId", qualifiedByName = "parentSubject")
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
