package com.meyame.email.demo.domain.auth.controller;

import com.meyame.email.demo.domain.auth.model.request.SendOtpRequest;
import com.meyame.email.demo.domain.auth.model.response.SendOtpResponse;
import com.meyame.email.demo.domain.auth.service.OTPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name="Account API", description="계정 관련 API") // for. swagger
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final OTPService otpService;

    @Operation(
            summary="Email 에 OTP 전송",
            description = "Email에 대해서 OTP를 전송합니다."
    )
    @GetMapping("/make-user/{email}")
    public SendOtpResponse sendOTP(@RequestParam @Valid SendOtpRequest request) {
        return otpService.sendOtp(request);
    }
}
