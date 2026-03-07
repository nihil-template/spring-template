package dev.nihilncunia.fa_campaign_manager.domains.users;

import dev.nihilncunia.fa_campaign_manager.common.dto.ListOutDto;

public interface UserService {
  /**
   * 새로운 사용자를 생성합니다. (회원가입)
   */
  UserOutDto createUser(UserInDto userInDto);

  /**
   * 현재 로그인한 사용자의 정보를 조회합니다.
   */
  UserOutDto getMyInfo();

  /**
   * ID로 사용자 상세 정보를 조회합니다.
   */
  UserOutDto getUserById(Long id);

  /**
   * 검색 조건과 페이징 정보를 바탕으로 사용자 목록을 조회합니다.
   */
  ListOutDto<UserOutDto> getUserList(UserInDto searchDto);

  /**
   * 사용자 정보를 수정합니다.
   */
  UserOutDto updateUser(Long id, UserInDto userInDto);

  /**
   * 사용자를 삭제(논리 삭제) 처리합니다.
   */
  void deleteUser(Long id);
}
