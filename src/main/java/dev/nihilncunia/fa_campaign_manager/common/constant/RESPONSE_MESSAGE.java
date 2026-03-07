package dev.nihilncunia.fa_campaign_manager.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.text.MessageFormat;

/**
 * 응답 메시지 내용을 관리하는 Enum 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public enum RESPONSE_MESSAGE {
  // 공통 (Default)
  DEFAULT_OK("요청 성공"),
  DEFAULT_CREATED("리소스 생성 성공"),
  DEFAULT_BAD_REQUEST("잘못된 요청입니다."),
  DEFAULT_UNAUTHORIZED("인증이 실패했습니다."),
  DEFAULT_FORBIDDEN("권한이 없습니다."),
  DEFAULT_NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
  DEFAULT_CONFLICT("리소스 충돌이 발생했습니다."),
  DEFAULT_INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
  DEFAULT_BAD_GATEWAY("잘못된 게이트웨이 응답입니다."),
  DEFAULT_VALIDATION_ERROR("입력값 검증에 실패했습니다."),
  DEFAULT_VALIDATION_ERROR_DETAILS("입력값 검증에 실패했습니다. ({0})"),
  DEFAULT_MESSAGE_NOT_READABLE("요청 본문을 읽을 수 없습니다. (바디 누락 또는 형식 오류)"),
  DEFAULT_MEDIA_TYPE_NOT_SUPPORTED("지원되지 않는 Content-Type입니다. (application/json 필수)"),

  // 서버 상태 (Health)
  HEALTH_SUCCESS("서버 이상 없음."),
  HEALTH_FAIL("서버 시스템 에러"),

  // 인증 (Auth)
  AUTH_SIGN_IN_SUCCESS("로그인 성공"),
  AUTH_SIGN_OUT_SUCCESS("로그아웃 성공"),
  AUTH_REFRESH_SUCCESS("토큰 재발급 성공"),
  AUTH_INVALID_TOKEN("유효하지 않은 토큰입니다."),
  AUTH_TOKEN_MISMATCH("토큰 정보가 일치하지 않습니다."),

  // 사용자 (User)
  USER_GET_LIST_SUCCESS("사용자 전체 목록 조회 성공"),
  USER_GET_PAGE_SUCCESS("사용자 페이징 목록 조회 성공"),
  USER_GET_DETAIL_SUCCESS("사용자 조회 성공"),
  USER_CREATE_SUCCESS("사용자 생성 성공"),
  USER_UPDATE_SUCCESS("사용자 수정 성공"),
  USER_DELETE_SUCCESS("사용자 삭제 성공"),
  USER_NOT_FOUND("사용자를 찾을 수 없습니다. (id={0})"),
  USER_EMAIL_NOT_FOUND("사용자를 찾을 수 없습니다. (email={0})"),
  USER_NAME_CONFLICT("이미 존재하는 이름입니다. (name={0})"),
  USER_EMAIL_CONFLICT("이미 존재하는 이메일입니다. (email={0})"),
  USER_NAME_IN_USE("이미 사용 중인 이름입니다. 다른 이름을 입력해 주세요.");

  @JsonValue
  private final String message;

  /**
   * 메시지 인자를 포함하여 포맷팅된 메시지를 반환합니다.
   *
   * @param args 메시지 인자
   * @return 포맷팅된 메시지
   */
  public String getMessage(Object... args) {
    if (args == null || args.length == 0) {
      return this.message;
    }
    return MessageFormat.format(this.message, args);
  }

  @Override
  public String toString() {
    return this.message;
  }
}
