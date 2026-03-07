package dev.nihilncunia.fa_campaign_manager.domains.users;

import dev.nihilncunia.fa_campaign_manager.common.annotation.IsAdmin;
import dev.nihilncunia.fa_campaign_manager.common.annotation.IsUser;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.common.validation.ValidationGroups;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import dev.nihilncunia.fa_campaign_manager.common.dto.ListOutDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "02. 사용자 관리", description = "사용자 등록, 수정, 삭제 및 목록/상세 조회를 담당하는 API")
public class UserController {

  private final UserService userService;

  @PostMapping
  @Operation(summary = "사용자 생성 (회원가입)", description = "새로운 사용자를 등록합니다. 초기 역할은 ROLE_USER로 설정됩니다.")
  public BaseResponse<UserOutDto> createUser(
      @Validated(ValidationGroups.Create.class) @RequestBody UserInDto userInDto) {
    UserOutDto newUser = userService.createUser(userInDto);
    
    return BaseResponse.ok(newUser, RESPONSE_CODE.CREATED,
        RESPONSE_MESSAGE.USER_CREATE_SUCCESS);
  }

  @IsUser
  @GetMapping("/me")
  @Operation(summary = "내 정보 조회", description = "인증된 쿠키 정보를 바탕으로 현재 로그인한 사용자의 상세 정보를 조회합니다.")
  public BaseResponse<UserOutDto> getMyInfo() {
    UserOutDto user = userService.getMyInfo();
    
    return BaseResponse.ok(user, RESPONSE_CODE.OK, RESPONSE_MESSAGE.USER_GET_DETAIL_SUCCESS);
  }

  @IsAdmin
  @GetMapping
  @Operation(summary = "사용자 목록 조회", description = "시스템에 등록된 모든 사용자 목록을 페이징하여 조회합니다. (관리자 전용)")
  public BaseResponse<ListOutDto<UserOutDto>> getUserList(UserInDto searchDto) {
    ListOutDto<UserOutDto> userList = userService.getUserList(searchDto);
    
    return BaseResponse.ok(userList, RESPONSE_CODE.OK,
        RESPONSE_MESSAGE.USER_GET_LIST_SUCCESS);
  }

  @IsUser
  @GetMapping("/{id}")
  @Operation(summary = "사용자 상세 조회", description = "특정 ID를 가진 사용자의 상세 정보를 조회합니다.")
  public BaseResponse<UserOutDto> getUserById(@PathVariable Long id) {
    UserOutDto user = userService.getUserById(id);
    
    return BaseResponse.ok(user, RESPONSE_CODE.OK, RESPONSE_MESSAGE.USER_GET_DETAIL_SUCCESS);
  }

  @IsUser
  @PutMapping("/{id}")
  @Operation(summary = "사용자 정보 수정", description = "사용자의 이름이나 비밀번호 등 프로필 정보를 수정합니다.")
  public BaseResponse<UserOutDto> updateUser(
      @PathVariable Long id,
      @Validated(ValidationGroups.Update.class) @RequestBody UserInDto userInDto) {
    UserOutDto updatedUser = userService.updateUser(id, userInDto);
    
    return BaseResponse.ok(updatedUser, RESPONSE_CODE.OK,
        RESPONSE_MESSAGE.USER_UPDATE_SUCCESS);
  }

  @IsUser
  @DeleteMapping("/{id}")
  @Operation(summary = "사용자 삭제 (탈퇴)", description = "사용자 계정을 논리적 삭제 처리합니다.")
  public BaseResponse<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return BaseResponse.ok(null, RESPONSE_CODE.OK, RESPONSE_MESSAGE.USER_DELETE_SUCCESS);
  }
}
