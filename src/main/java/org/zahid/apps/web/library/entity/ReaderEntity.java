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
@JsonIdentityInfo(scope = ReaderEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "readerId")
@Entity
@Table(name = "reader", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "reader_name_uk", columnNames = {"reader_name"})
})
public class ReaderEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reader_id")
    private Long readerId;

    @NotNull
    @Column(name = "reader_name")
    private String readerName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "reader")
    private List<BookTransHeaderEntity> bookTransHeaders;
}
