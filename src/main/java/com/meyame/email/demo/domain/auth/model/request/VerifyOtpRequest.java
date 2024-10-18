package com.meyame.email.demo.domain.auth.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description="OTP 인증번호 검증을 위한 요청")
public class VerifyOtpRequest {

    @Email(message = "유효한 이메일 주소여야합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Schema(description="사용자가 입력한 인증번호")
    @NotBlank
    private String otpCode;

}
