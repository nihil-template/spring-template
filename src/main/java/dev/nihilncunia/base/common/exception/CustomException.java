package dev.nihilncunia.base.common.exception;

import dev.nihilncunia.base.common.constant.RESPONSE_CODE;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final RESPONSE_CODE responseCode;

    /**
     * 응답 코드 정보만으로 예외를 생성합니다.
     * @param responseCode 응답 코드
     */
    public CustomException(RESPONSE_CODE responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    /**
     * 응답 코드와 커스텀 메시지로 예외를 생성합니다.
     * @param responseCode 응답 코드
     * @param message 커스텀 오류 메시지
     */
    public CustomException(RESPONSE_CODE responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
