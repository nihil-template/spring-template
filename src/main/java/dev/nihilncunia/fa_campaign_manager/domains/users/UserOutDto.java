package dev.nihilncunia.fa_campaign_manager.domains.users;

import dev.nihilncunia.fa_campaign_manager.common.constant.USER_ROLE;
import dev.nihilncunia.fa_campaign_manager.common.dto.CommonOutDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 출력 DTO")
public class UserOutDto extends CommonOutDto {

  @Schema(description = "사용자 이름", example = "NIHILncunia")
  private String name;
  
  //  @Schema(description = "디스코드 사용자 ID", example = "1234567890")
  //  private String discordId;

  @Schema(description = "사용자 이메일", example = "example@example.com")
  private String email;

  @Schema(description = "사용자 권한", example = "ROLE_USER")
  private USER_ROLE role;
}
