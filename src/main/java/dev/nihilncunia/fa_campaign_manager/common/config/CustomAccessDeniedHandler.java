package dev.nihilncunia.fa_campaign_manager.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 인가 실패 시 처리 로직을 정의합니다.
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param accessDeniedException 인가 예외 객체
     * @throws IOException 입출력 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
        
        BaseResponse<Void> baseResponse =
          BaseResponse.fail(null, RESPONSE_CODE.FORBIDDEN, RESPONSE_MESSAGE.DEFAULT_FORBIDDEN);
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        response.setStatus(HttpServletResponse.SC_OK);
        
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}

