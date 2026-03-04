package dev.nihilncunia.fa_campaign_manager.common.config;

import dev.nihilncunia.fa_campaign_manager.common.config.jwt.JwtExceptionFilter;
import dev.nihilncunia.fa_campaign_manager.common.helper.JwtProvider;
//import dev.nihilncunia.fa_campaign_manager.domains.users.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CommonSecurityConfig {

  private final JwtProvider jwtProvider;
//  private final AppUserDetailsService userDetailsService;
  private final JwtExceptionFilter jwtExceptionFilter;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  private static final String[] ALLOWED_ENDPOINTS = {
    "/health/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/v3/api-docs/**",
    "/docs-json/**",
    "/webjars/**",
  };

  /**
   * 비밀번호 암호화를 위한 Encoder 빈
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
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
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//    JwtAuthenticationFilter jwtAuthenticationFilter =
//      new JwtAuthenticationFilter(jwtProvider, userDetailsService);
//
//    http
//      .csrf(AbstractHttpConfigurer::disable)
//      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//      .formLogin(AbstractHttpConfigurer::disable)
//      .httpBasic(AbstractHttpConfigurer::disable)
//      .sessionManagement(session ->
//        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//      .headers(headers ->
//        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//      .authorizeHttpRequests(auth -> auth
//        .requestMatchers(ALLOWED_ENDPOINTS).permitAll()
//        .anyRequest().authenticated())
//      .exceptionHandling(exception -> exception
//        .authenticationEntryPoint(customAuthenticationEntryPoint)
//        .accessDeniedHandler(customAccessDeniedHandler))
//      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//      .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
//
//    return http.build();
//  }
}
