package dev.nihilncunia.fa_campaign_manager.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

  /**
   * 전역적으로 사용할 ObjectMapper 빈을 생성합니다.
   * 날짜 포맷(ISO 8601 등) 및 Java 8 시간 모듈 등을 설정합니다.
   * 
   * @return 설정된 ObjectMapper 객체
   */
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return JsonMapper.builder()
        // 날짜를 배열이 아닌 문자열로 표현하도록 설정.
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        // java 8 날짜 모듈을 등록
        .addModule(new JavaTimeModule())
        .build();
  }
}
