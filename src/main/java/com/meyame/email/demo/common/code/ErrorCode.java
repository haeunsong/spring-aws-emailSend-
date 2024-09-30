package com.meyame.email.demo.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements CInterface {

    // 이런식으로 에러코드를 따로 선언해두는게 좋다.
    NOT_VAILD_EMAIL_REQUEST(-1, "잘못된 이메일 형식 입니다.");

    private final Integer code;
    private final String message;

}
