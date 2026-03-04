package dev.nihilncunia.fa_campaign_manager.domains.users.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
  name = "users",
  indexes = {
    @Index(name = "idx_users_email", columnList = "email", unique = true)
  },
  uniqueConstraints = {}
)
@SQLRestriction(value = "delete_yn = 'N'")
public class UserEntity {
}
