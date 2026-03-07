package dev.nihilncunia.fa_campaign_manager.domains.health;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import dev.nihilncunia.fa_campaign_manager.common.response.ResponseExampleBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HealthExample {
  private final ResponseExampleBuilder exampleBuilder;
  
  /**
   * 서버 상태 정상일 때의 응답입니다.
   * @return 응답 객체
   */
  public Object healthSuccess() {
    return exampleBuilder.buildObject(
      true,
      false,
      RESPONSE_CODE.OK,
      RESPONSE_MESSAGE.HEALTH_SUCCESS
    );
  }
  
  /**
   * 서버 상태 비정상일 때의 응답입니다.
   * @return 응답 객체
   */
  public Object healthFail() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.INTERNAL_SERVER_ERROR,
      RESPONSE_MESSAGE.HEALTH_FAIL
    );
  }
}
