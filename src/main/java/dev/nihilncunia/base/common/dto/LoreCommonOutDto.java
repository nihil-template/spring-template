package dev.nihilncunia.base.common.dto;

import dev.nihilncunia.base.common.constant.YN_CODE;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoreCommonOutDto extends CommonOutDto {
  @Schema(description = "설정 타입", example = "종족")
  private String loreType;

  @Schema(description = "서브 설정타입", example = "인간")
  private String subLoreType;

  @Schema(description = "공유 여부", example = "N", allowableValues = { "Y", "N" })
  private YN_CODE shareYn;
}
