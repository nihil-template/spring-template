package dev.nihilncunia.fa_campaign_manager.common.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 필터 체인 실행 중 발생하는 예외를 캡처하여 처리합니다.
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param filterChain 필터 체인 객체
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
      throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
            
        } catch (Exception e) {
            log.error("Filter Exception: ", e);
            
            sendErrorResponse(response, e);
        }
    }
    
    /**
     * 예외 정보를 표준 응답 포맷으로 변환하여 클라이언트에 전송합니다.
     * @param response HTTP 응답 객체
     * @param e 발생한 예외 객체
     * @throws IOException 입출력 예외
     */
    private void sendErrorResponse(HttpServletResponse response, Exception e)
      throws IOException {
        
        BaseResponse<Void> baseResponse =
          BaseResponse.fail(null,
            RESPONSE_CODE.INTERNAL_SERVER_ERROR,
            e.getMessage());
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        response.setStatus(HttpServletResponse.SC_OK);
        
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}
