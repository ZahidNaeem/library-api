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

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = VolumeEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "volumeId")
@Entity
@Table(name = "volume", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "book_id_volume_name_uk", columnNames = {"book_id", "volume_name"})
})
public class VolumeEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "volume_id")
    private Long volumeId;

    @NotNull
    @Column(name = "row_key")
    private String rowKey;

    @NotNull
    @Column(name = "volume_name")
    private String volumeName;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private RackEntity rack;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "volume")
    private List<BookTransLineEntity> bookTransLines;
}
