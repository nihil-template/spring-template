package dev.nihilncunia.fa_campaign_manager.common.config;

import dev.nihilncunia.fa_campaign_manager.common.config.jwt.DiscordAuthenticationFilter;
import dev.nihilncunia.fa_campaign_manager.common.config.jwt.JwtAuthenticationFilter;
import dev.nihilncunia.fa_campaign_manager.common.config.jwt.JwtExceptionFilter;
import dev.nihilncunia.fa_campaign_manager.common.helper.JwtProvider;
import dev.nihilncunia.fa_campaign_manager.domains.users.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 메서드 보안 활성화
@RequiredArgsConstructor
public class CommonSecurityConfig {

  private final JwtProvider jwtProvider;
  private final AppUserDetailsService userDetailsService;
  private final JwtExceptionFilter jwtExceptionFilter;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  @Value("${app.security.use-discord:false}")
  private boolean useDiscord;

  private static final String[] ALLOWED_ENDPOINTS = {
    "/health/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/v3/api-docs/**",
    "/docs-json/**",
    "/webjars/**",
    "/auth/**",
  };

  /**
   * 비밀번호 암호화를 위한 Encoder 빈
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 권한 계층 구조 설정
   * USER_ROLE 상수에 정의된 이름을 그대로 사용하여 계층을 구성합니다.
   */
  @Bean
  public RoleHierarchy roleHierarchy() {
    return RoleHierarchyImpl.fromHierarchy(String.join("\n",
      "ROLE_SUPER_ADMIN > ROLE_ADMIN",
      "ROLE_ADMIN > ROLE_USER"
    ));
  }

  /**
   * 전역 CORS 설정
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(List.of("Set-Cookie"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * 통합 보안 필터 체인 설정
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    JwtAuthenticationFilter jwtAuthenticationFilter =
      new JwtAuthenticationFilter(jwtProvider, userDetailsService);

    DiscordAuthenticationFilter discordAuthenticationFilter =
      new DiscordAuthenticationFilter(userDetailsService, useDiscord);

    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .headers(headers ->
        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(org.springframework.http.HttpMethod.POST, "/users").permitAll() // 회원가입 허용
        .requestMatchers(ALLOWED_ENDPOINTS).permitAll()
        .anyRequest().authenticated())
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .accessDeniedHandler(customAccessDeniedHandler))
      .addFilterBefore(discordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(jwtExceptionFilter, DiscordAuthenticationFilter.class);

    return http.build();
  }
}
