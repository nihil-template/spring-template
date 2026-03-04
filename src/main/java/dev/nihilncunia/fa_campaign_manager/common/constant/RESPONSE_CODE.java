package dev.nihilncunia.fa_campaign_manager.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RESPONSE_CODE {
  // 2xx Success
  OK("요청 성공 (200)"),
  CREATED("리소스 생성 성공 (201)"),
  ACCEPTED("요청 수락됨, 비동기 처리 (202)"),
  NO_CONTENT("성공했으나 반환할 내용 없음 (204)"),
  
  // 4xx Client Error
  BAD_REQUEST("잘못된 요청 (400)"),
  UNAUTHORIZED("인증 필요, 로그인이 필요함 (401)"),
  FORBIDDEN("권한 없음, 접근 권한이 없음 (403)"),
  NOT_FOUND("리소스를 찾을 수 없음 (404)"),
  METHOD_NOT_ALLOWED("허용되지 않은 HTTP 메서드 (405)"),
  REQUEST_TIMEOUT("요청 시간 초과 (408)"),
  CONFLICT("리소스 충돌, 중복 등 (409)"),
  GONE("리소스가 영구적으로 삭제됨 (410)"),
  PRECONDITION_FAILED("선행 조건 실패 (412)"),
  PAYLOAD_TOO_LARGE("요청 본문이 너무 큼 (413)"),
  UNSUPPORTED_MEDIA_TYPE("지원하지 않는 Content-Type (415)"),
  UNPROCESSABLE_ENTITY("처리할 수 없는 엔티티 (422)"),
  VALIDATION_ERROR("입력값 검증 실패 (422)"),
  TOO_MANY_REQUESTS("요청 횟수 제한 초과 (429)"),
  
  // 권한 관련
  NOT_OWNER("소유자가 아님, 수정/삭제 권한이 없음"),
  NOT_PUBLIC("비공개 리소스, 공유되지 않은 리소스"),
  
  // 인증 관련
  INVALID_CREDENTIALS("잘못된 인증 정보, 이메일 또는 비밀번호가 올바르지 않음"),
  EMAIL_ALREADY_EXISTS("이메일이 이미 존재함"),
  INVALID_TOKEN("잘못된 토큰"),
  INVALID_PASSWORD("잘못된 비밀번호"),
  USER_NOT_FOUND("사용자를 찾을 수 없음"),
  TOKEN_EXPIRED("토큰이 만료됨"),
  ACCOUNT_LOCKED("계정이 잠금됨"),
  
  // 리소스 관련 (도메인)
  TAG_NOT_FOUND("태그를 찾을 수 없음"),
  PROJECT_NOT_FOUND("프로젝트를 찾을 수 없음"),
  ABILITY_NOT_FOUND("어빌리티를 찾을 수 없음"),
  PROJECT_ABILITY_NOT_FOUND("프로젝트 종속 어빌리티를 찾을 수 없음"),
  TRAIT_NOT_FOUND("특성을 찾을 수 없음"),
  PROJECT_TRAIT_NOT_FOUND("프로젝트 종속 특성을 찾을 수 없음"),
  CORE_RULE_NOT_FOUND("코어 설정을 찾을 수 없음"),
  CREATURE_NOT_FOUND("생물/종족을 찾을 수 없음"),
  CHARACTER_NOT_FOUND("인물을 찾을 수 없음"),
  ITEM_NOT_FOUND("아이템을 찾을 수 없음"),
  REGION_NOT_FOUND("지역을 찾을 수 없음"),
  NATION_NOT_FOUND("국가를 찾을 수 없음"),
  ORGANIZATION_NOT_FOUND("조직을 찾을 수 없음"),
  EVENT_NOT_FOUND("사건을 찾을 수 없음"),
  LORE_NOT_FOUND("전승/설화를 찾을 수 없음"),
  CHAR_TRAIT_MAP_NOT_FOUND("인물-특성 매핑을 찾을 수 없음"),
  CHAR_ABILITY_MAP_NOT_FOUND("인물-어빌리티 매핑을 찾을 수 없음"),
  CREATURE_TRAIT_MAP_NOT_FOUND("종족-특성 매핑을 찾을 수 없음"),
  CREATURE_ABILITY_MAP_NOT_FOUND("종족-어빌리티 매핑을 찾을 수 없음"),
  
  // 5xx Server Error
  INTERNAL_SERVER_ERROR("내부 서버 오류 (500)"),
  NOT_IMPLEMENTED("구현되지 않음 (501)"),
  BAD_GATEWAY("게이트웨이 오류 (502)"),
  SERVICE_UNAVAILABLE("서비스 사용 불가 (503)"),
  GATEWAY_TIMEOUT("게이트웨이 시간 초과 (504)"),
  
  // 기타
  ERROR("일반적인 에러, 구체적인 에러 코드를 사용할 수 없을 때");
  
  private final String message;
  
  @JsonValue
  public String getCodeName() {
    return this.name();
  }
}
