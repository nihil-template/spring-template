package dev.nihilncunia.fa_campaign_manager.common.exception;

import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 커스텀 예외(CustomException) 발생 시 처리 로직입니다.
     * @param e 발생한 CustomException 객체
     * @return 실패 정보가 담긴 BaseResponse 객체
     */
    @ExceptionHandler(CustomException.class)
    public BaseResponse<Void> handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage());
        return BaseResponse.fail(null, e.getResponseCode(), e.getMessage());
    }

    /**
     * 처리되지 않은 일반 예외(Exception) 발생 시 처리 로직입니다.
     * @param e 발생한 Exception 객체
     * @return 500 오류 정보가 담긴 BaseResponse 객체
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception e) {
        log.error("Exception: ", e);
        return BaseResponse.fail(null, RESPONSE_CODE.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
