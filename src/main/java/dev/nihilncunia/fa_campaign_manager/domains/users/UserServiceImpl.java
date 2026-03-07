package dev.nihilncunia.fa_campaign_manager.domains.users;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import dev.nihilncunia.fa_campaign_manager.common.constant.USER_ROLE;
import dev.nihilncunia.fa_campaign_manager.common.constant.YN_CODE;
import dev.nihilncunia.fa_campaign_manager.common.exception.CustomException;
import dev.nihilncunia.fa_campaign_manager.common.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import dev.nihilncunia.fa_campaign_manager.common.dto.ListOutDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final Optional<UserDiscordRepository> userDiscordRepository;

  @Value("${app.security.use-discord:false}")
  private boolean useDiscord;

  @Override
  @Transactional
  public UserOutDto createUser(UserInDto userInDto) {
    // 1. 중복 확인
    if (userRepository.findByEmail(userInDto.getEmail()).isPresent()) {
      throw new CustomException(RESPONSE_CODE.BAD_REQUEST, RESPONSE_MESSAGE.USER_EMAIL_CONFLICT);
    }

    // 2. 엔티티 변환 및 비밀번호 암호화
    UserEntity userEntity = userMapper.toEntity(userInDto);
    userEntity.setPassword(passwordEncoder.encode(userInDto.getRawPassword()));
    userEntity.setRole(USER_ROLE.ROLE_USER);

    // 3. 저장
    UserEntity savedUser = userRepository.save(userEntity);

    // 4. 디스코드 정보 연동 (설정 활성화 시)
    if (useDiscord && userInDto.getDiscordId() != null && userDiscordRepository.isPresent()) {
      UserDiscordEntity discordEntity = UserDiscordEntity.builder()
          .user(savedUser)
          .discordId(userInDto.getDiscordId())
          .build();
      userDiscordRepository.get().save(discordEntity);
    }

    return userMapper.toDto(savedUser);
  }

  @Override
  public UserOutDto getMyInfo() {
    Long currentUserId = CurrentUserProvider.getCurrentUserId()
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.UNAUTHORIZED));

    return getUserById(currentUserId);
  }

  @Override
  public UserOutDto getUserById(Long id) {
    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.NOT_FOUND, RESPONSE_MESSAGE.USER_NOT_FOUND));

    return userMapper.toDto(userEntity);
  }

  @Override
  public ListOutDto<UserOutDto> getUserList(UserInDto searchDto) {
    return userRepository.findAll(searchDto);
  }

  @Override
  @Transactional
  public UserOutDto updateUser(Long id, UserInDto userInDto) {
    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.NOT_FOUND, RESPONSE_MESSAGE.USER_NOT_FOUND));

    if (userInDto.getName() != null)
      userEntity.setName(userInDto.getName());
    if (userInDto.getRawPassword() != null) {
      userEntity.setPassword(passwordEncoder.encode(userInDto.getRawPassword()));
    }

    return userMapper.toDto(userEntity);
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(RESPONSE_CODE.NOT_FOUND, RESPONSE_MESSAGE.USER_NOT_FOUND));

    userEntity.setUseYn(YN_CODE.N);
    userEntity.setDeleteYn(YN_CODE.Y);
    userEntity.setDeleteDate(OffsetDateTime.now());
    userEntity.setDeleterId(CurrentUserProvider.getCurrentUserId().orElse(null));
  }
}
