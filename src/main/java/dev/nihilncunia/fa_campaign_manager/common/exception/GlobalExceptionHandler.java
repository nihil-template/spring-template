package dev.nihilncunia.fa_campaign_manager.common.exception;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
import dev.nihilncunia.fa_campaign_manager.common.response.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 커스텀 예외(CustomException) 발생 시 처리 로직입니다.
   */
  @ExceptionHandler(CustomException.class)
  public BaseResponse<Void> handleCustomException(CustomException e) {
    log.error("CustomException: {}", e.getMessage());
    return BaseResponse.fail(null, e.getResponseCode(), e.getMessage());
  }

  /**
   * 권한 부족 예외 처리 (AccessDeniedException)
   */
  @ExceptionHandler(AccessDeniedException.class)
  public BaseResponse<Void> handleAccessDeniedException(AccessDeniedException e) {
    log.error("Access Denied: {}", e.getMessage());
    return BaseResponse.fail(null, RESPONSE_CODE.FORBIDDEN, RESPONSE_MESSAGE.DEFAULT_FORBIDDEN);
  }

  /**
   * @Validated 또는 @Valid 검증 실패 시 (MethodArgumentNotValidException)
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public BaseResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    String errorMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
    log.error("Validation Error: {}", errorMessage);

    return BaseResponse.fail(null, RESPONSE_CODE.BAD_REQUEST,
        RESPONSE_MESSAGE.DEFAULT_VALIDATION_ERROR_DETAILS.getMessage(errorMessage));
  }

  /**
   * 파라미터 검증 실패 시 (ConstraintViolationException)
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public BaseResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
    log.error("Constraint Violation: {}", e.getMessage());
    return BaseResponse.fail(null, RESPONSE_CODE.BAD_REQUEST,
        RESPONSE_MESSAGE.DEFAULT_VALIDATION_ERROR);
  }

  /**
   * 요청 바디가 없거나 형식이 잘못된 경우 (HttpMessageNotReadableException)
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public BaseResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    log.error("Message Not Readable: {}", e.getMessage());
    return BaseResponse.fail(null, RESPONSE_CODE.BAD_REQUEST,
        RESPONSE_MESSAGE.DEFAULT_MESSAGE_NOT_READABLE);
  }

  /**
   * 지원되지 않는 Content-Type으로 요청한 경우 (HttpMediaTypeNotSupportedException)
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public BaseResponse<Void> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    log.error("Media Type Not Supported: {}", e.getMessage());
    return BaseResponse.fail(null, RESPONSE_CODE.BAD_REQUEST,
        RESPONSE_MESSAGE.DEFAULT_MEDIA_TYPE_NOT_SUPPORTED);
  }

  /**
   * 처리되지 않은 일반 예외(Exception) 발생 시 처리 로직입니다.
   */
  @ExceptionHandler(Exception.class)
  public BaseResponse<Void> handleException(Exception e) {
    log.error("Unhandled Exception: ", e);
    return BaseResponse.fail(null, RESPONSE_CODE.INTERNAL_SERVER_ERROR,
        RESPONSE_MESSAGE.DEFAULT_INTERNAL_SERVER_ERROR);
  }
}
