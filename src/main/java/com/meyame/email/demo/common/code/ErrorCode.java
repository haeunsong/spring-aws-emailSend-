package com.meyame.email.demo.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements CInterface {

    // 이런식으로 에러코드를 따로 선언해두는게 좋다.
    NOT_VAILD_EMAIL_REQUEST(-1, "잘못된 이메일 형식 입니다."),
    MAIL_RECEIVER_REQUIRED(-2, "전송에 필요한 이메일 주소가 없습니다."),
    MAIL_SEND_FAILED(-3,"메일 전송에 실패하였습니다."),

    OTP_NOT_FOUND(-4, "DB 에 OTP 가 존재하지 않습니다."),
    OPT_NUMBER_INCORRECT(-5, "인증번호가 다릅니다. 다시 확인해주세요."),
    OTP_EXPIRED(-6, "인증번호가 만료되었습니다.");

    private final Integer code;
    private final String message;

}
