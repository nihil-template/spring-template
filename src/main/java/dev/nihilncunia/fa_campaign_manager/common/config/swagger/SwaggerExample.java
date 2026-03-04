package dev.nihilncunia.fa_campaign_manager.common.config.swagger;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.response.ResponseExampleBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SwaggerExample {
  
  private final ResponseExampleBuilder exampleBuilder;
  
  /**
   * 400 Bad Request 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object badRequest() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.BAD_REQUEST,
      "잘못된 요청입니다."
    );
  }
  
  /**
   * 401 Unauthorized 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object unauthorized() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.UNAUTHORIZED,
      "인증되지 않은 사용자입니다."
    );
  }
  
  /**
   * 403 Forbidden 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object forbidden() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.FORBIDDEN,
      "접근 권한이 없습니다."
    );
  }
  
  /**
   * 500 Internal Server Error 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object internal_error() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.INTERNAL_SERVER_ERROR,
      "서버 내부 오류"
    );
  }
  
  /**
   * 로그인 성공 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object authSignInSuccess() {
    return exampleBuilder.buildObject(
      null,
      false,
      RESPONSE_CODE.OK,
      "로그인 성공"
    );
  }
  
  /**
   * 토큰 재발급 성공 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object authRefreshSuccess() {
    return exampleBuilder.buildObject(
      null,
      false,
      RESPONSE_CODE.OK,
      "토큰 재발급 성공"
    );
  }
  
  /**
   * 로그아웃 성공 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object authSignOutSuccess() {
    return exampleBuilder.buildObject(
      null,
      false,
      RESPONSE_CODE.OK,
      "로그아웃 성공"
    );
  }
  
  /**
   * 인증 필요 실패 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object authFailUnauthorized() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.UNAUTHORIZED,
      "인증이 필요합니다."
    );
  }
  
  /**
   * 잘못된 자격 증명 실패 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object authFailInvalidCredentials() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.INVALID_CREDENTIALS,
      "이메일 또는 비밀번호가 올바르지 않습니다."
    );
  }
  
  /**
   * 사용자 미존재 실패 응답 예시를 생성합니다.
   * @return 응답 예시 객체
   */
  public Object authFailUserNotFound() {
    return exampleBuilder.buildObject(
      null,
      true,
      RESPONSE_CODE.USER_NOT_FOUND,
      "사용자를 찾을 수 없음"
    );
  }
}
