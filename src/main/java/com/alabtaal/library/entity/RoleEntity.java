package com.alabtaal.library.entity;

import com.alabtaal.library.enumeration.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;

@Entity
@Table(name = "role", schema = "library", uniqueConstraints = {
        @UniqueConstraint(name = "role_name_uk", columnNames = {"name"})
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends Auditable<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private RoleName name;
}
