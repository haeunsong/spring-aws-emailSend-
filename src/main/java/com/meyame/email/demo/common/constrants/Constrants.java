package com.meyame.email.demo.common.constrants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// 싱글톤 패턴을 지킨다는게 뭐지..?
// 접근제어자를 private 하게 선언한다
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class Constrants {
    public static final String COMMON_MAIL_SENDER = "jamy022576@gmail.com";

    public static final String OTP_ISSUER = "haeun"; // otp 를 제공하는 사람
    public static final String OTP_PASSWORD = "123456";

    // QR 생성할 때 사용하는 것
    public static final String QR_SERVER = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=otpauth://totp/%s:%s?secret=%s&issuer=%s";
    public static final String INVITE_QR_TEMPLATE = "invite-opt-code";
    public static final String SECRET = "SECRET";

}
