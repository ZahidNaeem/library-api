package com.alabtaal.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = SubjectEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "subjectId")
@Entity
@Table(name = "subject", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "subject_code_uk", columnNames = {"subject_code"}),
        @UniqueConstraint(name = "subject_name_uk", columnNames = {"subject_name"})
})
public class SubjectEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private Long subjectId;

    @NotNull
    @Column(name = "subject_code")
    private String subjectCode;

    @NotNull
    @Column(name = "subject_name")
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "parent_subject_id")
    private SubjectEntity parentSubjectId;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "subject")
    private List<BookEntity> books;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "parentSubjectId")
    private List<SubjectEntity> subjects;
}
