package com.meyame.email.demo.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.meyame.email.demo.common.constrants.Constrants.OTP_ISSUER;
import static com.meyame.email.demo.common.constrants.Constrants.QR_SERVER;

/*
사용자에게 QR 코드를 생성하는 URL 을 제공하는 기능을 수행한다.
 */
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class OTP {
    public static String generateQRCodeURL(String email, String secretKey){
        return String.format(
            QR_SERVER,
                URLEncoder.encode(OTP_ISSUER, StandardCharsets.UTF_8),
                URLEncoder.encode(email, StandardCharsets.UTF_8),
                URLEncoder.encode(secretKey, StandardCharsets.UTF_8),
                URLEncoder.encode(OTP_ISSUER, StandardCharsets.UTF_8)
        );
    }
}
