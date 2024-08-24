package com.alabtaal.library.entity;

import com.alabtaal.library.enumeration.ActivationStatus;
import com.alabtaal.library.marker.UserInterface;
import com.alabtaal.library.validation.ExtendedEmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
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
@Table(name = "USERS", schema = "library")
public class UserEntity extends Auditable<String> implements UserInterface {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "USER_ID", columnDefinition = "BINARY(16)")
  private UUID id;

  @NotBlank(message = "Name must not be blank")
  @Size(max = 50, message = "Name should has max. {max} characters")
  @Column(name = "NAME")
  private String name;

  @NotBlank(message = "Username must not be blank")
  @Size(min = 3, max = 50, message = "Username should has min. {min} and max. {max} characters")
  @Column(name = "USERNAME", unique = true)
  private String username;

  @NotBlank(message = "Email must not be blank")
  @ExtendedEmailValidator
  @Column(name = "EMAIL", unique = true)
  private String email;

  @NotBlank(message = "Password must not be blank")
  @Size(min = 6, max = 100, message = "Password should has min. {min} and max. {max} characters")
  @Column(name = "PASSWORD")
  private String password;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  @Column(name = "ACTIVATION_STATUS")
  private ActivationStatus activationStatus;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "USER_ROLES",
      schema = "library",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Exclude
  private Set<RoleEntity> roles;

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
    UserEntity that = (UserEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}

