package dev.nihilncunia.base.domains.health;

import dev.nihilncunia.base.common.annotation.ApiExampleExclude;
import dev.nihilncunia.base.common.constant.RESPONSE_MESSAGE;
import dev.nihilncunia.base.common.response.BaseResponse;
import dev.nihilncunia.base.common.constant.RESPONSE_CODE;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Tag(name = "Health")
public class HeathController {
  private final HealthService healthService;
  
  /**
   * 서버 활성 상태 체크를 수행합니다.
   * @return 서버 활성 상태
   */
  @GetMapping("")
  @Operation(
    summary = "서버 활성 상태 체크",
    description = "서버의 상태를 true / false 로 응답합니다."
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = BaseResponse.class),
        examples = {
          @ExampleObject(name = "서버 상태 정상 응답", ref = "#/components/examples/healthSuccess"),
          @ExampleObject(name = "서버 상태 비정상 응답", ref = "#/components/examples/healthFail")
        }
      )
    )
  })
  @ApiExampleExclude(keys = {"인증 실패", "권한 부족", "잘못된 요청"})
  public BaseResponse<Boolean> getHealthStatus() {
    boolean health = healthService.getHealthStatus();
    return BaseResponse.ok(health, RESPONSE_CODE.OK, RESPONSE_MESSAGE.HEALTH_SUCCESS);
  }
}
