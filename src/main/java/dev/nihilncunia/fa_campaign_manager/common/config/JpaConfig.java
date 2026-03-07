package dev.nihilncunia.fa_campaign_manager.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.nihilncunia.fa_campaign_manager.common.security.CurrentUserProvider;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class JpaConfig {

  /**
   * JPA Auditing 시 현재 작업자 정보를 제공합니다.
   * SecurityContext에서 인증된 사용자 정보를 추출하여 ID를 반환합니다.
   * @return 현재 작업자 ID를 포함한 Optional 객체
   */
  @Bean
  @SuppressWarnings("null")
  public AuditorAware<Long> auditorProvider() {
    return CurrentUserProvider::getCurrentUserId;
  }

  /**
   * JPA Auditing 시 현재 시간 정보를 제공합니다.
   * OffsetDateTime 타입을 지원하도록 설정합니다.
   * @return 현재 시간을 포함한 Optional 객체
   */
  @Bean
  public DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(OffsetDateTime.now());
  }

  /**
   * QueryDSL 쿼리 작성을 위한 JPAQueryFactory 빈을 생성합니다.
   * @param entityManager JPA 엔티티 매니저
   * @return JPAQueryFactory 객체
   */
  @Bean
  public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }
}
