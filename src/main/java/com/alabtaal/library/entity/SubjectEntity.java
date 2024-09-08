package com.alabtaal.library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "subjects", schema = "library", uniqueConstraints = {
    @UniqueConstraint(name = "subject_code_uk", columnNames = {"subject_code"}),
    @UniqueConstraint(name = "subject_name_uk", columnNames = {"subject_name"})
})
public class SubjectEntity extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "subject_id", columnDefinition = "BINARY(16)")
  private UUID id;

  @NotNull
  @Column(name = "subject_code")
  private String code;

  @NotNull
  @Column(name = "subject_name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "parent_subject_id")
  private SubjectEntity parentSubject;

  @Column(name = "remarks")
  private String remarks;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
  @Exclude
  private List<BookEntity> books;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentSubject")
  @Exclude
  private List<SubjectEntity> subjects;

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
    SubjectEntity that = (SubjectEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
