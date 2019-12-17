package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = BookEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
@Entity
@Table(name = "book", schema = "library", catalog = "", uniqueConstraints = {
        @UniqueConstraint(name = "book_name_uk", columnNames = {"book_name"})
})
public class BookEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long bookId;

    @NotNull
    @Column(name = "book_name")
    private String bookName;

    @Column(name = "publication_date")
    private Timestamp publicationDate;

    @Column(name = "book_condition")
    private String bookCondition;

    @Column(name = "purchased")
    private Byte purchased;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<VolumeEntity> volumes;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    @ManyToOne
    @JoinColumn(name = "researcher_id")
    private ResearcherEntity researcher;

    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private ShelfEntity shelf;
}
