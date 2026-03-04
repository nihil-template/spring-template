package dev.nihilncunia.fa_campaign_manager.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 인증되지 않은 사용자가 인증이 필요한 리소스에 접근할 때 처리 로직을 정의합니다.
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param authException 인증 예외 객체
     * @throws IOException 입출력 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
      throws IOException, ServletException {
        
        BaseResponse<Void> baseResponse =
          BaseResponse.fail(null, RESPONSE_CODE.UNAUTHORIZED, RESPONSE_MESSAGE.DEFAULT_UNAUTHORIZED);
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        response.setStatus(HttpServletResponse.SC_OK);
        
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}

