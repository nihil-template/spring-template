package dev.nihilncunia.fa_campaign_manager.common.response;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;

public record BaseResponse<T>(
    T data,
    boolean error,
    RESPONSE_CODE code,
    String message) {

  /**
   * 성공 응답 객체를 생성합니다. (데이터, 코드, 메시지 필수)
   * 
   * @param data    응답 데이터
   * @param code    응답 코드
   * @param message 성공 메시지 Enum
   * @param <T>     데이터 타입
   * @return 생성된 BaseResponse 객체
   */
  public static <T> BaseResponse<T> ok(T data, RESPONSE_CODE code, RESPONSE_MESSAGE message) {
    return new BaseResponse<>(data, false, code, message.getMessage());
  }

  /**
   * 실패 응답 객체를 생성합니다. (데이터, 코드, 메시지 필수)
   * 
   * @param data    응답 데이터 (주로 null)
   * @param code    응답 코드
   * @param message 실패 메시지 Enum
   * @param <T>     데이터 타입
   * @return 생성된 BaseResponse 객체
   */
  public static <T> BaseResponse<T> fail(T data, RESPONSE_CODE code, RESPONSE_MESSAGE message) {
    return new BaseResponse<>(data, true, code, message.getMessage());
  }

  /**
   * 실패 응답 객체를 생성합니다. (커스텀 문자열 메시지 지원 - 예외 처리용)
   * 
   * @param data    응답 데이터
   * @param code    응답 코드
   * @param message 실패 메시지 문자열
   * @param <T>     데이터 타입
   * @return 생성된 BaseResponse 객체
   */
  public static <T> BaseResponse<T> fail(T data, RESPONSE_CODE code, String message) {
    return new BaseResponse<>(data, true, code, message);
  }
}
