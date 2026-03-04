package dev.nihilncunia.fa_campaign_manager.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
  private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  
  /**
   * 전역적으로 사용할 ObjectMapper 빈을 생성합니다.
   * 날짜 포맷(ISO 8601 등) 및 Java 8 시간 모듈 등을 설정합니다.
   * @return 설정된 ObjectMapper 객체
   */
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    
    return JsonMapper.builder()
      // 날짜를 배열이 아닌 문자열로 표현하도록 설정.
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      // java 8 날짜 모듈을 등록 및 포맷 설정
      .addModule(new JavaTimeModule()
        .addSerializer(LocalDateTime.class,
          new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
        .addDeserializer(LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(formatter)))
      .build();
  }
}
