package com.alabtaal.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.mapper.RackMapper;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.service.SubjectService;

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
