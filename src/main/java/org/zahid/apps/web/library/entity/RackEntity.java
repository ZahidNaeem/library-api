package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = RackEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "rackId")
@Entity
@Table(name = "rack", schema = "library", catalog = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"rack_name"})
})
public class RackEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rack_id")
    private Long rackId;

    @NotNull
    @Column(name = "rack_name")
    private String rackName;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private ShelfEntity shelf;
}
