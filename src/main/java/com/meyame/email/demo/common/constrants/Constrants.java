package com.meyame.email.demo.common.constrants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// 싱글톤 패턴을 지킨다는게 뭐지..?
// 접근제어자를 private 하게 선언한다
/*
싱글톤 패턴?
클래스의 인스턴스를 오직 하나만 생성하고, 어디서든 그 인스턴스에 접근할 수 있도록 보장하는 디자인 패턴.
주로 공유자원이나 전역적으로 사용하는 객체가 있을 때 이 패턴을 사용한다.

그런데 Constrants 클래스는 인스턴스화(객체 생성) 자체가 필요하지 않으므로,
이 상황에서 싱글톤 패턴을 지킨다 = 인스턴스를 만들지 않고 모든 필드를 static 으로 선언해 전역적으로 사용할 수 있도록 하겠다

밑에서 accessLevel 을 PRIVATE 으로 막아 외부에서 이 클래스의 객체를 생성할 수 없게 막는다.
그래서 이 클래스는 객체로 생성되지 않고, 모든 필드를 static 으로만 접근할 수 있도록 만들어지는 것이다.
 */
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
