package com.alabtaal.library.entity;

import com.alabtaal.library.util.Miscellaneous;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Auditable<U> {

  @CreatedBy
  @Column(name = "CREATED_BY", updatable = false)
  protected U createdBy;

  @CreatedDate
//  @Temporal(TIMESTAMP)
  @Column(name = "CREATION_DATE", updatable = false)
  protected LocalDateTime creationDate;

  @LastModifiedBy
  @Column(name = "LAST_UPDATED_BY")
  protected U lastUpdatedBy;

  @LastModifiedDate
//  @Temporal(TIMESTAMP)
  @Column(name = "LAST_UPDATE_DATE")
  protected LocalDateTime lastUpdateDate;
}
