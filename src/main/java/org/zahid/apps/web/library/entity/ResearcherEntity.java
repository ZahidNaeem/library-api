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
@JsonIdentityInfo(scope = ResearcherEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "researcherId")
@Entity
@Table(name = "researcher", schema = "library", catalog = "")
public class ResearcherEntity {
    @Id
    @Column(name = "researcher_id")
    private Long researcherId;

    @Column(name = "researcher_name")
    private String researcherName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "researcher")
    private List<BookEntity> books;
}
