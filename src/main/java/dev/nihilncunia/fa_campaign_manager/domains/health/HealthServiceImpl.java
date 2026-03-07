package dev.nihilncunia.fa_campaign_manager.domains.health;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthServiceImpl implements HealthService {
  
  /**
   * 서버 상태를 확인합니다.
   * @return 서버 상태
   */
  @Override
  public boolean getHealthStatus() {
    return true;
  }
}
