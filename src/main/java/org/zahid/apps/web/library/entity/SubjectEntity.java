package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = SubjectEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "subjectId")
@Entity
@Table(name = "subject", schema = "library", catalog = "")
public class SubjectEntity {
    @Id
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "parent_subject_id")
    private SubjectEntity parentSubjectId;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
    private List<BookEntity> books;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentSubjectId")
    private List<SubjectEntity> subjects;
}
