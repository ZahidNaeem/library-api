package org.zahid.apps.web.pos.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.zahid.apps.web.pos.enumeration.RoleName;

import javax.persistence.*;

@Entity
@Table(name = "XXIM_ROLES")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private RoleName name;
}
