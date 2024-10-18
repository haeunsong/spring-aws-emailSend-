package com.meyame.email.demo.domain.auth.controller;

import com.meyame.email.demo.domain.auth.model.request.SendOtpRequest;
import com.meyame.email.demo.domain.auth.model.request.VerifyOtpRequest;
import com.meyame.email.demo.domain.auth.model.response.SendOtpResponse;
import com.meyame.email.demo.domain.auth.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Account API", description="계정 관련 API") // for. swagger
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final OtpService otpService;

    // OTP QR Code 로 인증
    @Operation(
            summary="Email 에 OTP 전송",
            description = "Email에 대해서 OTP를 전송합니다."
    )
    @PostMapping("/make-user")
    public SendOtpResponse sendOTP(@RequestBody @Valid SendOtpRequest request) {
        return otpService.sendOtp(request);
    }

    // 인증번호 6자리로 인증
    @Operation(
            summary = "Email 에 인증번호 6자리 전송",
            description = "Email 로 인증을 위한 인증번호 6자리를 전송합니다."
    )
    @PostMapping("/send-code")
    public SendOtpResponse sendOTPCode(@RequestBody @Valid SendOtpRequest request) {
        return otpService.sendOTPCode(request);
    }

    // 사용자가 입력한 인증번호와 이메일로 전송한 인증번호가 일치하는지 확인
    // api 를 호출하면, 클라이언트가 작성한 인증번호와 서버의 인증번호가 일치하는지 확인한다.
    // 일치하면 SUCCESS, 다르면 오류
    @Operation(
            summary = "인증번호 검증",
            description = "사용자가 입력한 인증번호와 서버의 인증번호가 일치하지 검증합니다."
    )
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyOTPCode(@RequestBody @Valid VerifyOtpRequest request) {
        boolean isVerified = otpService.verifyOtp(request.getEmail(), request.getOtpCode());

        if(isVerified) {
            return ResponseEntity.ok("인증성공");
        }else {
            return ResponseEntity.status(400).body("인증실패");
        }
    }

}
