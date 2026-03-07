package dev.nihilncunia.fa_campaign_manager.domains.users;

import dev.nihilncunia.fa_campaign_manager.common.constant.USER_ROLE;
import dev.nihilncunia.fa_campaign_manager.common.constant.YN_CODE;
import dev.nihilncunia.fa_campaign_manager.common.entity.CommonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@SuperBuilder
@Getter
@Setter
@Entity
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
public class UserEntity extends CommonEntity {
  
  @Column(nullable = false, length = 50)
  @Schema(description = "사용자 이름", example = "NIHILncunia")
  private String name;

  @Column(nullable = false, length = 100)
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  @Schema(description = "사용자 이메일", example = "example@example.com")
  private String email;

  @Column(nullable = false)
  @Schema(description = "사용자 비밀번호")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Schema(description = "사용자 권한", example = "ROLE_USER")
  private USER_ROLE role;
  
  @Column(nullable = true, length = 1000)
  @Schema(description = "사용자 리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
  private String refreshToken;
  
  @Column(nullable = true, length = 1000)
  private Integer loginFailureCount = 0;
  
  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Schema(description = "사용자 잠금 여부", example = "N")
  private YN_CODE lockYn = YN_CODE.N;
  
  @Column(nullable = true)
  @Schema(description = "사용자 마지막 로그인 날짜", example = "2026-03-07T10:00:00.000+09:00")
  private OffsetDateTime lastSignInDate;
  
  @Column(nullable = true)
  @Schema(description = "사용자 마지막 비밀번호 변경 날짜", example = "2026-03-07T10:00:00.000+09:00")
  private OffsetDateTime lastPasswordChangeDate;
}
