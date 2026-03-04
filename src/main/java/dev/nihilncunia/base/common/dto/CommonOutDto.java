package dev.nihilncunia.base.common.dto;

import dev.nihilncunia.base.common.constant.YN_CODE;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonOutDto {

  @Schema(description = "ID", example = "1")
  private Long id;

  @Schema(description = "사용 여부", example = "Y", allowableValues = { "Y", "N" })
  private YN_CODE useYn;

  @Schema(description = "삭제 여부", example = "N", allowableValues = { "Y", "N" })
  private YN_CODE deleteYn;

  @Schema(description = "생성자 ID", example = "1")
  private Long creatorId;

  @Schema(description = "생성일시", example = "2026-02-19T01:34:00.000Z")
  private LocalDateTime createDate;

  @Schema(description = "수정자 ID", example = "1")
  private Long updaterId;

  @Schema(description = "수정일시", example = "2026-02-19T01:34:00.000Z")
  private LocalDateTime updateDate;

  @Schema(description = "삭제자 ID", example = "1")
  private Long deleterId;

  @Schema(description = "삭제일시", example = "2026-02-19T01:34:00.000Z")
  private LocalDateTime deleteDate;
}
