package dev.nihilncunia.fa_campaign_manager.common.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import dev.nihilncunia.fa_campaign_manager.common.config.jwt.JwtProperties;

@Component
public class JwtProvider {

  private final SecretKey accessKey;
  private final SecretKey refreshKey;

  private final long accessTokenExpiration;
  private final long refreshTokenExpiration;

  public JwtProvider(JwtProperties properties) {
    this.accessKey = Keys.hmacShaKeyFor(properties.getSecret().getAccess().getBytes(StandardCharsets.UTF_8));
    this.refreshKey = Keys.hmacShaKeyFor(properties.getSecret().getRefresh().getBytes(StandardCharsets.UTF_8));

    this.accessTokenExpiration = properties.getExpiration().getAccess();
    this.refreshTokenExpiration = properties.getExpiration().getRefresh();
  }

  public String createAccessToken(Long id, String email, String role) {
    return createToken(id, email, role, accessTokenExpiration, accessKey);
  }

  public String createRefreshToken(Long id, String email, String role) {
    return createToken(id, email, role, refreshTokenExpiration, refreshKey);
  }

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

  public Claims getClaims(String token, boolean isRefresh) {
    SecretKey key = isRefresh ? refreshKey : accessKey;

    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public String getEmail(String token, boolean isRefresh) {
    return getClaims(token, isRefresh).getSubject();
  }

  public Long getId(String token, boolean isRefresh) {
    return getClaims(token, isRefresh).get("id", Long.class);
  }

  public String getRole(String token, boolean isRefresh) {
    return getClaims(token, isRefresh).get("role", String.class);
  }

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
   * 
   * @param name   쿠키 이름
   * @param value  쿠키 값
   * @param maxAge 쿠키 만료 시간 (초)
   * @return 생성된 Cookie 객체
   */
  public Cookie createCookie(String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);

    cookie.setHttpOnly(true);
    cookie.setSecure(false); // 로컬 테스트를 위해 false
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);

    return cookie;
  }

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
