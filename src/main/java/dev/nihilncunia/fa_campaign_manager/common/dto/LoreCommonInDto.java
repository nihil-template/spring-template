package dev.nihilncunia.fa_campaign_manager.common.dto;

import dev.nihilncunia.fa_campaign_manager.common.annotation.SearchCondition;
import dev.nihilncunia.fa_campaign_manager.common.constant.SEARCH_TYPE;
import dev.nihilncunia.fa_campaign_manager.common.constant.YN_CODE;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoreCommonInDto extends CommonInDto {
  
  @SearchCondition(type = SEARCH_TYPE.LIKE)
  @Schema(description = "설정 타입", example = "종족")
  private String loreType;
  
  @SearchCondition(type = SEARCH_TYPE.LIKE)
  @Schema(description = "서브 설정타입", example = "인간")
  private String subLoreType;
  
  @SearchCondition(type = SEARCH_TYPE.EQUALS)
  @Schema(description = "공유 여부", example = "N", allowableValues = { "Y", "N" })
  private YN_CODE shareYn;
}
