package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.mapper.RackMapper;
import org.zahid.apps.web.library.model.RackModel;
import org.zahid.apps.web.library.service.SubjectService;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class SubjectQualifier extends CommonQualifier {

    private final SubjectService subjectService;

    @Autowired
    public SubjectQualifier(final BookMapper bookMapper, final SubjectService subjectService) {
        super(bookMapper);
        this.subjectService = subjectService;
    }

    @Named("parentSubject")
    public SubjectEntity rackModels(final Long parentSubjectId) {
        return parentSubjectId != null ? subjectService.findById(parentSubjectId) : null;
    }
}
