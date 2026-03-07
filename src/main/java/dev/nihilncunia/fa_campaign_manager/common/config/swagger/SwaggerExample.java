package dev.nihilncunia.fa_campaign_manager.common.config.swagger;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
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
      RESPONSE_MESSAGE.DEFAULT_BAD_REQUEST
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
      RESPONSE_MESSAGE.DEFAULT_UNAUTHORIZED
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
      RESPONSE_MESSAGE.DEFAULT_FORBIDDEN
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
      RESPONSE_MESSAGE.DEFAULT_INTERNAL_SERVER_ERROR
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
      RESPONSE_MESSAGE.AUTH_SIGN_IN_SUCCESS
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
      RESPONSE_MESSAGE.AUTH_REFRESH_SUCCESS
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
      RESPONSE_MESSAGE.AUTH_SIGN_OUT_SUCCESS
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
      RESPONSE_MESSAGE.DEFAULT_UNAUTHORIZED
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
      RESPONSE_MESSAGE.AUTH_INVALID_TOKEN
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
      RESPONSE_MESSAGE.USER_NOT_FOUND
    );
  }
}
