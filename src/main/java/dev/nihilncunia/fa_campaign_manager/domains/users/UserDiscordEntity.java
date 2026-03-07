package dev.nihilncunia.fa_campaign_manager.domains.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * 디스코드 연동 정보를 저장하는 엔티티입니다.
 * app.security.use-discord 설정이 true일 때만 활성화되어 DB 컬럼(테이블)이 생성됩니다.
 */
@Entity
@Table(name = "user_discord")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@ConditionalOnProperty(name = "app.security.use-discord", havingValue = "true")
public class UserDiscordEntity {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(nullable = false, length = 100, unique = true)
  @Schema(description = "디스코드 사용자 ID", example = "1234567890")
  private String discordId;
}
