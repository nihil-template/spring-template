package dev.nihilncunia.fa_campaign_manager.common.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import dev.nihilncunia.fa_campaign_manager.common.helper.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  private final JwtProvider jwtProvider;
  
  private final UserDetailsService userDetailsService;
  
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
    throws ServletException, IOException {
    
    // 1. 요청에서 accessToken 추출
    String accessToken = jwtProvider.resolveToken(request, "accessToken");
    String refreshToken = jwtProvider.resolveToken(request, "refreshToken");
    
    String targetToken = null;
    boolean isRefresh = false;

    // 2. 토큰 결정 (액세스 토큰 우선, 없으면 리프레시 토큰 확인)
    if (accessToken != null && jwtProvider.validateToken(accessToken, false)) {
        targetToken = accessToken;
        isRefresh = false;
        log.debug("Using accessToken for authentication");
    } else if (refreshToken != null && jwtProvider.validateToken(refreshToken, true)) {
        targetToken = refreshToken;
        isRefresh = true;
        log.info("AccessToken missing or invalid. Using refreshToken for authentication.");
    }

    // 3. 인증 처리
    if (targetToken != null) {
      String email = jwtProvider.getEmail(targetToken, isRefresh);
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);
      
      if (userDetails != null) {
        UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("User {} authenticated via {}", email, (isRefresh ? "refreshToken" : "accessToken"));
      }
    }
    
    filterChain.doFilter(request, response);
  }
}
