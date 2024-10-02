package com.meyame.email.demo.domain.auth.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/*
record? dto 를 굉장히 간단하게 구현할 수 있다. 자동으로 생성해주는 메서드들이 있음.
생성자, getter, toString(), equals(), hashCode() 를 자동으로 생성.

즉, 아래 코드는 SendOtpRequest 라는 불변 객체를 정의하고 있다.
email 이라는 하나의 필드를 가진 객체이다.
 */
@Schema(description = "OTP 전송 요청")
public record SendOtpRequest(
        @Schema(description = "이메일")
        @NotBlank String email
){}
