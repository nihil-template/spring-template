package dev.nihilncunia.base.common.entity;

import dev.nihilncunia.base.common.constant.YN_CODE;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID", example = "1")
  private Long id;

  // --- 상태 플래그 (Y/N) ---

  @Builder.Default
  @Schema(description = "사용 여부", example = "Y", allowableValues = { "Y", "N" })
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
  private YN_CODE useYn = YN_CODE.Y;

  @Builder.Default
  @Schema(description = "삭제 여부", example = "N", allowableValues = { "Y", "N" })
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
  private YN_CODE deleteYn = YN_CODE.N;

  // --- 생성 정보 (Create) ---

  @CreatedBy
  @Schema(description = "생성자 ID", example = "1")
  private Long creatorId;

  @CreatedDate
  @Schema(description = "생성일시", example = "2026-02-19T01:34:00.000Z")
  private LocalDateTime createDate;

  // --- 수정 정보 (Update) ---

  @LastModifiedBy
  @Schema(description = "수정자 ID", example = "1")
  private Long updaterId;

  @LastModifiedDate
  @Schema(description = "수정일시", example = "2026-02-19T01:34:00.000Z")
  private LocalDateTime updateDate;

  // --- 삭제 정보 (Delete) ---

  @Schema(description = "삭제자 ID", example = "1")
  private Long deleterId;

  @Schema(description = "삭제일시", example = "2026-02-19T01:34:00.000Z")
  private LocalDateTime deleteDate;

  protected void onPrePersist() {
    if (this.useYn == null)
      this.useYn = YN_CODE.Y;
    if (this.deleteYn == null)
      this.deleteYn = YN_CODE.N;
  }
}
