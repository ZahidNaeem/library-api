package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = ResearcherEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "researcherId")
@Entity
@Table(name = "researcher", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "researcher_name_uk", columnNames = {"researcher_name"})
})
public class ResearcherEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "researcher_id")
    private Long researcherId;

    @NotNull
    @Column(name = "researcher_name")
    private String researcherName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "researcher")
    private List<BookEntity> books;
}
