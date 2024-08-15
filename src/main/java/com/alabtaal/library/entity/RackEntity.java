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
@JsonIdentityInfo(scope = RackEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "rackId")
@Entity
@Table(name = "rack", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "shelf_id_rack_name_uk", columnNames = {"shelf_id", "rack_name"})
})
public class RackEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rack_id")
    private Long rackId;

    @NotNull
    @Column(name = "row_key")
    private String rowKey;

    @NotNull
    @Column(name = "rack_name")
    private String rackName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "rack")
    private List<VolumeEntity> volumes;

    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private ShelfEntity shelf;
}
