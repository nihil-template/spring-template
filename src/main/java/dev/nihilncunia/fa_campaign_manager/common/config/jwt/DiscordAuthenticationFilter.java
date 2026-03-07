package dev.nihilncunia.fa_campaign_manager.common.config.jwt;

import dev.nihilncunia.fa_campaign_manager.domains.users.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class DiscordAuthenticationFilter extends OncePerRequestFilter {

  private final AppUserDetailsService userDetailsService;
  private final boolean useDiscord;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    String discordId = request.getHeader("x-discord-id");

    if (useDiscord && discordId != null) {
      try {
        // 추후 UserDetails 로딩 및 SecurityContext 저장 로직 구현 예정
      } catch (Exception e) {
        // 인증 실패 시 별도 로그를 남기거나 무시 (다음 필터로 전달)
        logger.error("Discord Auth failed for ID: " + discordId, e);
      }
    }

    filterChain.doFilter(request, response);
  }
}
