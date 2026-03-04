package dev.nihilncunia.base.common.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
  
  private final SecretKey accessKey;
  private final SecretKey refreshKey;
  
  private final long accessTokenExpiration;
  private final long refreshTokenExpiration;
  
  /**
   * JwtProvider 생성자
   * application.yml에 정의된 비밀키와 만료 시간을 주입받아 초기화합니다.
   * @param accessSecret Access Token 비밀키
   * @param refreshSecret Refresh Token 비밀키
   * @param accessExpiration Access Token 만료 시간 (ms)
   * @param refreshExpiration Refresh Token 만료 시간 (ms)
   */
  public JwtProvider(
    @Value("${jwt.secret.access}") String accessSecret,
    @Value("${jwt.secret.refresh}") String refreshSecret,
    @Value("${jwt.expiration.access}") long accessExpiration,
    @Value("${jwt.expiration.refresh}") long refreshExpiration) {
    
    this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
    this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    
    this.accessTokenExpiration = accessExpiration;
    this.refreshTokenExpiration = refreshExpiration;
  }
  
  /**
   * Access Token을 생성합니다.
   * @param id 사용자 ID
   * @param email 사용자 이메일
   * @param role 사용자 역할
   * @return 생성된 Access Token 문자열
   */
  public String createAccessToken(Long id, String email, String role) {
    return createToken(id, email, role, accessTokenExpiration, accessKey);
  }
  
  /**
   * Refresh Token을 생성합니다.
   * @param id 사용자 ID
   * @param email 사용자 이메일
   * @param role 사용자 역할
   * @return 생성된 Refresh Token 문자열
   */
  public String createRefreshToken(Long id, String email, String role) {
    return createToken(id, email, role, refreshTokenExpiration, refreshKey);
  }
  
  /**
   * 토큰 생성 공통 로직
   * @param id 사용자 ID
   * @param email 사용자 이메일
   * @param role 사용자 역할
   * @param expiration 만료 시간
   * @param key 서명 키
   * @return 생성된 토큰 문자열
   */
  private String createToken(Long id, String email, String role,
                             long expiration, SecretKey key) {
    
    Claims claims = Jwts.claims()
      .subject(email)
      .add("id", id)
      .add("role", role)
      .build();
    
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);
    
    return Jwts.builder()
      .claims(claims)
      .issuedAt(now)
      .expiration(expiryDate)
      .signWith(key)
      .compact();
  }
  
  /**
   * 토큰에서 클레임 정보를 추출합니다.
   * @param token 토큰 문자열
   * @param isRefresh Refresh Token 여부
   * @return 클레임 정보
   */
  public Claims getClaims(String token, boolean isRefresh) {
    SecretKey key = isRefresh ? refreshKey : accessKey;
    
    return Jwts.parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
  
  /**
   * 토큰에서 사용자 이메일을 추출합니다.
   * @param token 토큰 문자열
   * @param isRefresh Refresh Token 여부
   * @return 사용자 이메일
   */
  public String getEmail(String token, boolean isRefresh) {
    return getClaims(token, isRefresh).getSubject();
  }
  
  /**
   * 토큰에서 사용자 ID를 추출합니다.
   * @param token 토큰 문자열
   * @param isRefresh Refresh Token 여부
   * @return 사용자 ID
   */
  public Long getId(String token, boolean isRefresh) {
    return getClaims(token, isRefresh).get("id", Long.class);
  }
  
  /**
   * 토큰에서 사용자 역할을 추출합니다.
   * @param token 토큰 문자열
   * @param isRefresh Refresh Token 여부
   * @return 사용자 역할
   */
  public String getRole(String token, boolean isRefresh) {
    return getClaims(token, isRefresh).get("role", String.class);
  }
  
  /**
   * 토큰의 유효성을 검사합니다.
   * @param token 토큰 문자열
   * @param isRefresh Refresh Token 여부
   * @return 유효 여부
   */
  public boolean validateToken(String token, boolean isRefresh) {
    try {
      SecretKey key = isRefresh ? refreshKey : accessKey;
      
      Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token);
      
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  /**
   * 토큰을 담을 HttpOnly 쿠키를 생성합니다.
   * @param name 쿠키 이름
   * @param value 쿠키 값
   * @param maxAge 쿠키 만료 시간 (초)
   * @return 생성된 Cookie 객체
   */
  public Cookie createCookie(String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    
    return cookie;
  }
  
  /**
   * HTTP 요청에서 특정 이름의 쿠키 값을 추출합니다.
   * @param request HTTP 요청 객체
   * @param name 쿠키 이름
   * @return 쿠키 값 (없을 경우 null)
   */
  public String resolveToken(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie.getValue();
        }
      }
    }
    
    return null;
  }
}
