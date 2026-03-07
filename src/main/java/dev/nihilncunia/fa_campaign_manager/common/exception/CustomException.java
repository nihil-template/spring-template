package dev.nihilncunia.fa_campaign_manager.common.exception;

import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_CODE;
import dev.nihilncunia.fa_campaign_manager.common.constant.RESPONSE_MESSAGE;
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

    /**
     * 응답 코드와 RESPONSE_MESSAGE Enum으로 예외를 생성합니다.
     * @param responseCode 응답 코드
     * @param message 응답 메시지 Enum
     */
    public CustomException(RESPONSE_CODE responseCode, RESPONSE_MESSAGE message) {
        super(message.getMessage());
        this.responseCode = responseCode;
    }

    /**
     * 응답 코드와 RESPONSE_MESSAGE Enum(메시지 인자 포함)으로 예외를 생성합니다.
     * @param responseCode 응답 코드
     * @param message 응답 메시지 Enum
     * @param args 메시지 인자
     */
    public CustomException(RESPONSE_CODE responseCode, RESPONSE_MESSAGE message, Object... args) {
        super(message.getMessage(args));
        this.responseCode = responseCode;
    }
}
