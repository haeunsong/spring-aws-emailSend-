package com.meyame.email.demo.common.exception;

import com.meyame.email.demo.common.code.CInterface;
import lombok.Getter;

/*
커스텀 예외를 만든다.
 */
@Getter
public class CustomException extends RuntimeException {
    // codeInterface : CInterface 타입의 final 필드로,
    // 예외와 관련된 에러 코드나 메시지 정보를 담을 객체를 저장한다.
    private final CInterface codeInterface;

    // return new CustomException(NOT_VAILD_EMAIL_REQUEST); 이런식으로 활용

    public CustomException(CInterface codeInterface) {
        super(codeInterface.getMessage());
        this.codeInterface = codeInterface;
    }
    public CustomException(CInterface codeInterface, String message) {
        super(codeInterface.getMessage() + message);
        this.codeInterface = codeInterface;
    }
}
