package dev.nihilncunia.fa_campaign_manager.common.dto;

import dev.nihilncunia.fa_campaign_manager.common.annotation.SearchCondition;
import dev.nihilncunia.fa_campaign_manager.common.constant.SEARCH_TYPE;
import dev.nihilncunia.fa_campaign_manager.common.constant.YN_CODE;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공통 입력 DTO")
public class CommonInDto extends SearchDto {
  
  @SearchCondition(type = SEARCH_TYPE.EQUALS)
  @Schema(description = "ID", example = "1")
  private Long id;
  
  @SearchCondition(type = SEARCH_TYPE.EQUALS)
  @Schema(description = "사용 여부", example = "Y", allowableValues = { "Y", "N" })
  private YN_CODE useYn;
  
  @SearchCondition(type = SEARCH_TYPE.EQUALS)
  @Schema(description = "삭제 여부", example = "N", allowableValues = { "Y", "N" })
  private YN_CODE deleteYn;
  
  @SearchCondition(type = SEARCH_TYPE.EQUALS)
  @Schema(description = "수정자 ID", example = "1")
  private Long updaterId;

  @Schema(description = "수정일시", example = "2026-03-07T10:00:00.000+09:00")
  private OffsetDateTime updateDate;
  
  @SearchCondition(type = SEARCH_TYPE.EQUALS)
  @Schema(description = "삭제자 ID", example = "1")
  private Long deleterId;

  @Schema(description = "삭제일시", example = "2026-03-07T10:00:00.000+09:00")
  private OffsetDateTime deleteDate;
}
