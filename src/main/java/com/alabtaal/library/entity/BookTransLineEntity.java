package com.alabtaal.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

@SuperBuilder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_trans_lines", schema = "library", uniqueConstraints = {
    @UniqueConstraint(name = "header_id_volume_id_uk", columnNames = {"header_id", "volume_id"})
})
public class BookTransLineEntity extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "line_id", columnDefinition = "BINARY(16)")
  private UUID id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "volume_id")
  private VolumeEntity volume;

  @NotNull
  @Column(name = "issuance_date")
  private Date issuanceDate;

  @Column(name = "receipt_date")
  private Date receiptDate;

  @Column(name = "remarks")
  private String remarks;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "header_id")
  private BookTransHeaderEntity bookTransHeader;

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
    BookTransLineEntity that = (BookTransLineEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
