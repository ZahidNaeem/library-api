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
@JsonIdentityInfo(scope = ShelfEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "shelfId")
@Entity
@Table(name = "shelf", schema = "library", catalog = "")
public class ShelfEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelf_id")
    private Long shelfId;

    @Column(name = "shelf_name")
    private String shelfName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shelf")
    private List<BookEntity> books;
}
