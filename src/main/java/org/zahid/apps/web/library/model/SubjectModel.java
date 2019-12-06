package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectModel {

    private Long subjectId;

    private String subjectCode;

    private String subjectName;

    private Long parentSubjectId;

    private String remarks;

    private List<BookModel> books;

    private List<SubjectModel> subjects;
}
