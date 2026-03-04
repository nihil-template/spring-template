package dev.nihilncunia.fa_campaign_manager.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "다건 삭제 결과")
public class DeletedListDto {
  
  @Schema(description = "삭제 엔티티 타입", example = "TAG")
  private String entityType;
  
  @Schema(description = "삭제된 개수", example = "5")
  private Integer deletedCount;
}
