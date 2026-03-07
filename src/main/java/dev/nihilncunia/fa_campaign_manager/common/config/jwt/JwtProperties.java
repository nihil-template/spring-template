package dev.nihilncunia.fa_campaign_manager.common.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private Secret secret = new Secret();
  private Expiration expiration = new Expiration();

  @Getter
  @Setter
  public static class Secret {
    private String access;
    private String refresh;
  }

  @Getter
  @Setter
  public static class Expiration {
    private long access;
    private long refresh;
  }
}
