package dev.nihilncunia.fa_campaign_manager.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private Security security = new Security();

  @Getter
  @Setter
  public static class Security {
    private boolean useDiscord;
  }
}
