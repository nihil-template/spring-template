package dev.nihilncunia.fa_campaign_manager.domains.auth;

import dev.nihilncunia.fa_campaign_manager.common.annotation.IsUser;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import dev.nihilncunia.fa_campaign_manager.common.helper.JwtProvider;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.common.security.CurrentUserProvider;
import dev.nihilncunia.fa_campaign_manager.domains.users.UserInDto;
import dev.nihilncunia.fa_campaign_manager.domains.users.UserOutDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "01. 인증 관리", description = "로그인, 로그아웃, 토큰 갱신 및 비밀번호 변경을 담당하는 API")
public class AuthController {

  private final AuthService authService;
  private final JwtProvider jwtProvider;

  @PostMapping("/signin")
  @Operation(summary = "로그인", description = "이메일과 비밀번호를 사용하여 로그인합니다. 성공 시 액세스 토큰과 리프레시 토큰이 쿠키로 발급됩니다.")
  public BaseResponse<UserOutDto> signIn(@RequestBody UserInDto userInDto, HttpServletResponse response) {
    UserOutDto userOutDto = authService.signIn(userInDto);
    
    // 액세스 토큰 쿠키 생성 (1시간)
    String accessToken = jwtProvider.createAccessToken(userOutDto.getId(), userOutDto.getEmail(), userOutDto.getRole().name());
    Cookie accessCookie = jwtProvider.createCookie("accessToken", accessToken, 3600);
    response.addCookie(accessCookie);

    // 리프레시 토큰 쿠키 생성 (7일)
    String refreshToken = jwtProvider.createRefreshToken(userOutDto.getId(), userOutDto.getEmail(), userOutDto.getRole().name());
    Cookie refreshCookie = jwtProvider.createCookie("refreshToken", refreshToken, 7 * 24 * 3600);
    response.addCookie(refreshCookie);

    return BaseResponse.ok(userOutDto, RESPONSE_CODE.OK, RESPONSE_MESSAGE.AUTH_SIGN_IN_SUCCESS);
  }

  @IsUser
  @PostMapping("/signout")
  @Operation(summary = "로그아웃", description = "현재 세션을 종료하고 브라우저에 저장된 인증 쿠키를 삭제합니다.")
  public BaseResponse<Void> signOut(HttpServletResponse response) {
    Long userId = CurrentUserProvider.getCurrentUserId().orElseThrow();
    authService.signOut(userId);
    
    // 쿠키 제거
    Cookie accessCookie = jwtProvider.createCookie("accessToken", null, 0);
    Cookie refreshCookie = jwtProvider.createCookie("refreshToken", null, 0);
    response.addCookie(accessCookie);
    response.addCookie(refreshCookie);
    
    return BaseResponse.ok(null, RESPONSE_CODE.OK, RESPONSE_MESSAGE.AUTH_SIGN_OUT_SUCCESS);
  }

  @PostMapping("/refresh")
  @Operation(summary = "토큰 재발급", description = "리프레시 토큰 쿠키를 사용하여 액세스 토큰을 갱신합니다.")
  public BaseResponse<Void> refresh(
      @CookieValue(value = "refreshToken", required = false) String refreshToken,
      HttpServletResponse response) {
    
    Map<String, String> tokens = authService.refreshToken(refreshToken);
    
    // 새로운 액세스 토큰 쿠키 설정 (1시간)
    Cookie accessCookie = jwtProvider.createCookie("accessToken", tokens.get("accessToken"), 3600);
    response.addCookie(accessCookie);

    // 새로운 리프레시 토큰 쿠키 설정 (7일)
    Cookie refreshCookie = jwtProvider.createCookie("refreshToken", tokens.get("refreshToken"), 7 * 24 * 3600);
    response.addCookie(refreshCookie);
    
    return BaseResponse.ok(null, RESPONSE_CODE.OK, RESPONSE_MESSAGE.AUTH_REFRESH_SUCCESS);
  }

  @IsUser
  @PostMapping("/password/change")
  @Operation(summary = "비밀번호 변경", description = "현재 로그인된 사용자의 비밀번호를 변경합니다.")
  public BaseResponse<Void> changePassword(@RequestBody Map<String, String> request) {
    authService.changePassword(request.get("oldPassword"), request.get("newPassword"));
    return BaseResponse.ok(null, RESPONSE_CODE.OK, RESPONSE_MESSAGE.DEFAULT_OK);
  }
}
