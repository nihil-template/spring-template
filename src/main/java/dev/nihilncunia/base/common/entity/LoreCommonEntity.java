package dev.nihilncunia.base.common.entity;

import dev.nihilncunia.base.common.constant.YN_CODE;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoreCommonEntity extends CommonEntity {
  // --- 설정 테이블 공통 정보 ---

  @Schema(description = "설정 타입", example = "종족")
  @Column(nullable = false, length = 50)
  private String loreType;

  @Schema(description = "서브 설정타입", example = "휴머노이드")
  @Column(length = 50)
  private String subLoreType;

  // --- 상태 플래그 (Y/N) ---

  @Schema(description = "공유 여부", example = "N", allowableValues = { "Y", "N" })
  @Enumerated(EnumType.STRING)
  @Column(length = 1, columnDefinition = "char(1) default 'N'")
  private YN_CODE shareYn;

  protected void onPrePersist() {
    super.onPrePersist();

    if (this.shareYn == null)
      this.shareYn = YN_CODE.N;
  }
}
