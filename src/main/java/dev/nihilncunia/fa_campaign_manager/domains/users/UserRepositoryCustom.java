package dev.nihilncunia.fa_campaign_manager.domains.users;

import dev.nihilncunia.fa_campaign_manager.common.dto.ListOutDto;

public interface UserRepositoryCustom {
  ListOutDto<UserOutDto> findAll(UserInDto userInDto);
}
