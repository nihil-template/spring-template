package dev.nihilncunia.base.domains.health;

public interface HealthService {
  
  /**
   * 서버 상태를 확인합니다.
   * @return 서버 상태
   */
  boolean getHealthStatus();
}
