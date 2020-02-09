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
@JsonIdentityInfo(scope = ShelfEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "shelfId")
@Entity
@Table(name = "shelf", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "shelf_name_uk", columnNames = {"shelf_name"})
})
public class ShelfEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelf_id")
    private Long shelfId;

    @NotNull
    @Column(name = "shelf_name")
    private String shelfName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "shelf")
    private List<BookEntity> books;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shelf")
    private List<RackEntity> racks;
}
