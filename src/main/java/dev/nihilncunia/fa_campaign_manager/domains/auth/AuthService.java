package dev.nihilncunia.fa_campaign_manager.domains.auth;

import dev.nihilncunia.fa_campaign_manager.domains.users.UserInDto;
import dev.nihilncunia.fa_campaign_manager.domains.users.UserOutDto;

public interface AuthService {
  UserOutDto signIn(UserInDto userInDto);
  void signOut(Long userId);
  java.util.Map<String, String> refreshToken(String refreshToken);
  void changePassword(String oldPassword, String newPassword);
}
