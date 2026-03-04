package dev.nihilncunia.base.common.constant;

public class RESPONSE_MESSAGE {
  // 공통 (Default)
  public static final String DEFAULT_OK = "response.message.default.ok";
  public static final String DEFAULT_CREATED = "response.message.default.created";
  public static final String DEFAULT_BAD_REQUEST = "response.message.default.bad_request";
  public static final String DEFAULT_UNAUTHORIZED = "response.message.default.unauthorized";
  public static final String DEFAULT_FORBIDDEN = "response.message.default.forbidden";
  public static final String DEFAULT_NOT_FOUND = "response.message.default.not_found";
  public static final String DEFAULT_CONFLICT = "response.message.default.conflict";
  public static final String DEFAULT_INTERNAL_SERVER_ERROR = "response.message.default.internal_server_error";
  public static final String DEFAULT_BAD_GATEWAY = "response.message.default.bad_gateway";
  public static final String DEFAULT_VALIDATION_ERROR = "response.message.default.validation_error";

  // 서버 상태 (Health)
  public static final String HEALTH_SUCCESS = "response.message.health.success";
  public static final String HEALTH_FAIL = "response.message.health.fail";

  // 인증 (Auth)
  public static final String AUTH_SIGN_IN_SUCCESS = "response.message.auth.sign_in_success";
  public static final String AUTH_SIGN_OUT_SUCCESS = "response.message.auth.sign_out_success";
  public static final String AUTH_REFRESH_SUCCESS = "response.message.auth.refresh_success";
  public static final String AUTH_INVALID_TOKEN = "response.message.auth.invalid_token";
  public static final String AUTH_TOKEN_MISMATCH = "response.message.auth.token_mismatch";

  // 사용자 (User)
  public static final String USER_GET_LIST_SUCCESS = "response.message.user.get_list_success";
  public static final String USER_GET_PAGE_SUCCESS = "response.message.user.get_page_success";
  public static final String USER_GET_DETAIL_SUCCESS = "response.message.user.get_detail_success";
  public static final String USER_CREATE_SUCCESS = "response.message.user.create_success";
  public static final String USER_UPDATE_SUCCESS = "response.message.user.update_success";
  public static final String USER_DELETE_SUCCESS = "response.message.user.delete_success";
  public static final String USER_NOT_FOUND = "response.message.user.not_found";
  public static final String USER_EMAIL_NOT_FOUND = "response.message.user.email_not_found";
  public static final String USER_NAME_CONFLICT = "response.message.user.name_conflict";
  public static final String USER_EMAIL_CONFLICT = "response.message.user.email_conflict";
  public static final String USER_NAME_IN_USE = "response.message.user.name_in_use";

  // 태그 (Tag)
  public static final String TAG_GET_LIST_SUCCESS = "response.message.tag.get_list_success";
  public static final String TAG_GET_PAGE_SUCCESS = "response.message.tag.get_page_success";
  public static final String TAG_GET_DETAIL_SUCCESS = "response.message.tag.get_detail_success";
  public static final String TAG_CREATE_SUCCESS = "response.message.tag.create_success";
  public static final String TAG_UPDATE_SUCCESS = "response.message.tag.update_success";
  public static final String TAG_DELETE_SUCCESS = "response.message.tag.delete_success";
  public static final String TAG_NOT_FOUND = "response.message.tag.not_found";
  public static final String TAG_NAME_CONFLICT = "response.message.tag.name_conflict";
}
