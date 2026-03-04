package dev.nihilncunia.fa_campaign_manager.domains.health.service;

public interface HealthService {
  
  /**
   * 서버 상태를 확인합니다.
   * @return 서버 상태
   */
  boolean getHealthStatus();
}
