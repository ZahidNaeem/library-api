package com.alabtaal.library.entity;

import com.alabtaal.library.enumeration.BookCondition;
import com.alabtaal.library.enumeration.BookSource;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

@SuperBuilder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books", schema = "library", uniqueConstraints = {
    @UniqueConstraint(name = "book_name_uk", columnNames = {"book_name"})
})
public class BookEntity extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "book_id", columnDefinition = "BINARY(16)")
  private UUID id;

  @NotNull
  @Column(name = "book_name")
  private String name;

  @Column(name = "publication_date")
  private Date publicationDate;

  @NotNull
  @Column(name = "book_condition")
  @Enumerated(value = EnumType.STRING)
  private BookCondition bookCondition;

  @Column(name = "book_source")
  @Enumerated(value = EnumType.STRING)
  private BookSource bookSource;

  @Column(name = "remarks")
  private String remarks;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
  @Exclude
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

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    BookEntity that = (BookEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
