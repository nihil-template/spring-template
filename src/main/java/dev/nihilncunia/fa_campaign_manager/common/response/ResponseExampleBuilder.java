package dev.nihilncunia.fa_campaign_manager.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseExampleBuilder {
  
  private final ObjectMapper objectMapper;
  
  public BaseResponse<Object> buildObject(Object data,
                                        boolean error,
                                        RESPONSE_CODE code,
                                        RESPONSE_MESSAGE message) {
    return new BaseResponse<>(data, error, code, message.getMessage());
  }

  /**
   * 페이징 응답 데이터를 생성합니다.
   * @param content 데이터 리스트
   * @param totalElements 전체 데이터 수
   * @param totalPages 전체 페이지 수
   * @param size 페이지 당 데이터 수
   * @param number 현재 페이지 번호 (0부터 시작)
   * @param code 응답 코드
   * @param message 응답 메시지 Enum
   * @return 페이징 구조의 BaseResponse 객체
   */
  public BaseResponse<Object> buildPage(List<?> content,
                                        long totalElements,
                                        int totalPages,
                                        int size,
                                        int number,
                                        RESPONSE_CODE code,
                                        RESPONSE_MESSAGE message) {
    Map<String, Object> pageData = new HashMap<>();
    pageData.put("content", content);
    pageData.put("totalElements", totalElements);
    pageData.put("totalPages", totalPages);
    pageData.put("size", size);
    pageData.put("number", number);
    pageData.put("numberOfElements", content.size());
    pageData.put("first", number == 0);
    pageData.put("last", number == totalPages - 1 || totalPages == 0);
    pageData.put("empty", content.isEmpty());
    
    return new BaseResponse<>(pageData, false, code, message.getMessage());
  }

  /**
   * 응답 정보를 바탕으로 예시 JSON 문자열을 생성합니다.
   * @param data 응답 데이터 객체
   * @param error 오류 발생 여부
   * @param code 응답 코드
   * @param message 응답 메시지 Enum
   * @return 포맷팅된 JSON 문자열
   */
  public String build(Object data,
                      boolean error,
                      RESPONSE_CODE code,
                      RESPONSE_MESSAGE message) {
    
    try {
      BaseResponse<Object> response =
        new BaseResponse<>(data, error, code, message.getMessage());
      
      return objectMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(response);
      
    } catch (Exception e) {
      return """
                {
                  "data": null,
                  "error": true,
                  "code": "INTERNAL_SERVER_ERROR",
                  "message": "서버 내부 오류가 발생했습니다."
                }
                """;
    }
  }
}
