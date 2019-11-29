package org.zahid.apps.web.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.zahid.apps.web.library.enumeration.RoleName;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
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
