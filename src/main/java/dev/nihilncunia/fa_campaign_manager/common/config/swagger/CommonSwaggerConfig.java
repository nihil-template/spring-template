package dev.nihilncunia.fa_campaign_manager.common.config.swagger;

import dev.nihilncunia.fa_campaign_manager.common.annotation.ApiExampleExclude;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.domains.health.HealthExample;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class CommonSwaggerConfig {

  private final SwaggerExample swaggerExample;
  private final HealthExample healthExample;

  /**
   * 메인 API 그룹을 생성합니다.
   * @return GroupedOpenApi 객체
   */
  @Bean
  public GroupedOpenApi mainApi() {
    return GroupedOpenApi.builder()
      .group("Main API")
      .pathsToMatch("/**")
      .addOpenApiCustomizer(openApiCustomizer())
      .build();
  }

  /**
   * OpenAPI 객체에 공통 컴포넌트(스키마, 예시 등) 및 기본 정보를 설정합니다.
   * @return OpenApiCustomizer 객체
   */
  private OpenApiCustomizer openApiCustomizer() {
    return openApi -> {
      if (openApi.getComponents() == null) {
        openApi.setComponents(new Components());
      }
      Components components = openApi.getComponents();

      // 기본 정보 설정
      openApi.info(new Info()
          .title("창작 지원 API")
          .description("""
                  창작자들을 위한 통합 창작 지원 서비스입니다.

                  **인증 안내**: `/auth/sign-in` API를 통해 로그인하면 쿠키가 자동 설정되며,\
                   이후 API 호출 시 인증이 자동으로 적용됩니다.""")
          .version("v1.0.0"));

      // 태그 설정
      Tag healthTag = new Tag().name("Health").description("시스템 상태 API");
      Tag userTag = new Tag().name("User").description("사용자 관리 API");
      Tag authTag = new Tag().name("Auth").description("인증 관련 API");
      openApi.setTags(List.of(healthTag, userTag, authTag));

      // 1. 공통 스키마 등록
      ModelConverters.getInstance().read(BaseResponse.class).forEach(components::addSchemas);

      // 2. 공통 에러 예시 등록 (SwaggerExample)
      components.addExamples("badRequest", new Example().value(swaggerExample.badRequest()));
      components.addExamples("unauthorized", new Example().value(swaggerExample.unauthorized()));
      components.addExamples("forbidden", new Example().value(swaggerExample.forbidden()));
      components.addExamples("internal_error", new Example().value(swaggerExample.internal_error()));
      
      // 3. 인증 관련 예시 (SwaggerExample)
      components.addExamples("authSignInSuccess", new Example().value(swaggerExample.authSignInSuccess()));
      components.addExamples("authRefreshSuccess", new Example().value(swaggerExample.authRefreshSuccess()));
      components.addExamples("authSignOutSuccess", new Example().value(swaggerExample.authSignOutSuccess()));
      components.addExamples("authFailUnauthorized", new Example().value(swaggerExample.authFailUnauthorized()));
      components.addExamples("authFailInvalidCredentials", new Example().value(swaggerExample.authFailInvalidCredentials()));
      components.addExamples("authFailUserNotFound", new Example().value(swaggerExample.authFailUserNotFound()));

      // 4. 시스템 상태 예시 (HealthExample)
      components.addExamples("healthSuccess", new Example().value(healthExample.healthSuccess()));
      components.addExamples("healthFail", new Example().value(healthExample.healthFail()));

      // 5. 사용자 관련 예시 (UserDataExample)
    };
  }

  /**
   * 모든 API 오퍼레이션에 공통 응답 예시를 주입합니다.
   * @return OperationCustomizer 객체
   */
  @Bean
  public OperationCustomizer operationCustomizer() {
    return (operation, handlerMethod) -> {
      ApiResponses responses = operation.getResponses();
      ApiResponse okResponse = responses.computeIfAbsent("200", key -> new ApiResponse().description("성공"));
      
      if (okResponse.getContent() == null) okResponse.setContent(new Content());
      Content content = okResponse.getContent();
      
      MediaType mediaType = Optional.ofNullable(content.get("application/json")).orElseGet(MediaType::new);
      if (mediaType.getExamples() == null) mediaType.setExamples(new java.util.LinkedHashMap<>());
      
      // 전역 공통 에러 응답 주입 (refs)
      mediaType.addExamples("잘못된 요청", new Example().$ref("#/components/examples/badRequest"));
      mediaType.addExamples("인증 실패", new Example().$ref("#/components/examples/unauthorized"));
      mediaType.addExamples("권한 부족", new Example().$ref("#/components/examples/forbidden"));
      mediaType.addExamples("서비스 에러", new Example().$ref("#/components/examples/internal_error"));
      
      content.put("application/json", mediaType);

      ApiExampleExclude excludeAnnotation = handlerMethod.getMethodAnnotation(ApiExampleExclude.class);
      if (excludeAnnotation != null) {
        for (String key : excludeAnnotation.keys()) {
          mediaType.getExamples().remove(key);
        }
      }

      return operation;
    };
  }
}
