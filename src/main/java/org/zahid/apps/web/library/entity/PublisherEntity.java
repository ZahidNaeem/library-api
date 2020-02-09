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
@JsonIdentityInfo(scope = PublisherEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "publisherId")
@Entity
@Table(name = "publisher", schema = "library"/*, catalog = ""*/, uniqueConstraints = {
        @UniqueConstraint(name = "publisher_name_uk", columnNames = {"publisher_name" })
})
public class PublisherEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publisher_id")
    private Long publisherId;

    @NotNull
    @Column(name = "publisher_name")
    private String publisherName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = {PERSIST, MERGE, /*REMOVE,*/ REFRESH, DETACH}, fetch = FetchType.LAZY, mappedBy = "publisher")
    private List<BookEntity> books;
}
