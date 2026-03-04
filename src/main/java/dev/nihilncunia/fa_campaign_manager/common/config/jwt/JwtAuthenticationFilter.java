package dev.nihilncunia.fa_campaign_manager.common.config.jwt;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import dev.nihilncunia.fa_campaign_manager.common.helper.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  private final JwtProvider jwtProvider;
  
  private final UserDetailsService userDetailsService;
  
  /**
   * 요청을 필터링하여 JWT 인증을 수행합니다.
   * @param request HTTP 요청 객체
   * @param response HTTP 응답 객체
   * @param filterChain 필터 체인 객체
   * @throws ServletException 서블릿 예외
   * @throws IOException 입출력 예외
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
    throws ServletException, IOException {
    
    // 요청에서 accessToken 추출
    String token = jwtProvider.resolveToken(request, "accessToken");
    
    // 토큰이 존재하고 유효한 경우
    if (token != null && jwtProvider.validateToken(token, false)) {
      
      // 토큰에서 사용자 식별자(email) 추출
      String email = jwtProvider.getEmail(token, false);
      
      // DB 또는 저장소에서 사용자 정보 조회
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);
      
      if (userDetails != null) {
        
        // 인증 객체 생성 (비밀번호는 null, 권한 정보 포함)
        UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        
        // 요청 정보(IP 등)를 인증 객체에 추가
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
        
        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    
    // 다음 필터로 요청 전달
    filterChain.doFilter(request, response);
  }
}
