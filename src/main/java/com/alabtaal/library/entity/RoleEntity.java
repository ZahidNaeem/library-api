package com.alabtaal.library.entity;

import com.alabtaal.library.enumeration.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "roles", schema = "library", uniqueConstraints = {
    @UniqueConstraint(name = "role_name_uk", columnNames = {"name"})
})
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "role_id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(name = "role_name", length = 60)
  private RoleName name;

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
    RoleEntity that = (RoleEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId())
        && getName() != null && Objects.equals(getName(), that.getName());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(name);
  }
}
