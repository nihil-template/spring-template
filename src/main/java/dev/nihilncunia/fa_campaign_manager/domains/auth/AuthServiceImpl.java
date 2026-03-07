package dev.nihilncunia.fa_campaign_manager.domains.auth;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.exception.CustomException;
import dev.nihilncunia.fa_campaign_manager.common.helper.JwtProvider;
import dev.nihilncunia.fa_campaign_manager.common.security.CurrentUserProvider;
import dev.nihilncunia.fa_campaign_manager.domains.users.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public UserOutDto signIn(UserInDto userInDto) {
    // 1. 이메일로 사용자 조회
    UserEntity userEntity = userRepository.findByEmail(userInDto.getEmail())
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));

    // 2. 비밀번호 검증
    if (!passwordEncoder.matches(userInDto.getRawPassword(), userEntity.getPassword())) {
      throw new CustomException(RESPONSE_CODE.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
    }

    // 3. 토큰 발급
    String accessToken = jwtProvider.createAccessToken(userEntity.getId(), userEntity.getEmail(),
        userEntity.getRole().name());
    String refreshToken = jwtProvider.createRefreshToken(userEntity.getId(), userEntity.getEmail(),
        userEntity.getRole().name());

    // 4. 리프레시 토큰 저장 및 로그인 정보 업데이트
    userEntity.setRefreshToken(refreshToken);
    userEntity.setLastSignInDate(OffsetDateTime.now());

    return userMapper.toDto(userEntity);
  }

  @Override
  @Transactional
  public void signOut(Long userId) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.NOT_FOUND));

    userEntity.setRefreshToken(null);
  }

  @Override
  @Transactional
  public java.util.Map<String, String> refreshToken(String refreshToken) {
    if (!jwtProvider.validateToken(refreshToken, true)) {
      throw new CustomException(RESPONSE_CODE.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다.");
    }

    Long userId = jwtProvider.getId(refreshToken, true);
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.NOT_FOUND));

    if (!refreshToken.equals(userEntity.getRefreshToken())) {
      throw new CustomException(RESPONSE_CODE.UNAUTHORIZED, "토큰 정보가 일치하지 않습니다.");
    }

    // 새로운 토큰 쌍 생성
    String newAccessToken = jwtProvider.createAccessToken(userEntity.getId(), userEntity.getEmail(),
        userEntity.getRole().name());
    String newRefreshToken = jwtProvider.createRefreshToken(userEntity.getId(), userEntity.getEmail(),
        userEntity.getRole().name());

    // DB 업데이트
    userEntity.setRefreshToken(newRefreshToken);

    return java.util.Map.of(
        "accessToken", newAccessToken,
        "refreshToken", newRefreshToken);
  }

  @Override
  @Transactional
  public void changePassword(String oldPassword, String newPassword) {
    Long userId = CurrentUserProvider.getCurrentUserId()
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.UNAUTHORIZED));

    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.NOT_FOUND));

    if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
      throw new CustomException(RESPONSE_CODE.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다.");
    }

    userEntity.setPassword(passwordEncoder.encode(newPassword));
    userEntity.setLastPasswordChangeDate(OffsetDateTime.now());
  }
}
