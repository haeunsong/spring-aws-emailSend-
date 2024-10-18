package com.meyame.email.demo.domain.auth.service;

import com.meyame.email.demo.common.code.ErrorCode;
import com.meyame.email.demo.common.exception.CustomException;
import com.meyame.email.demo.common.validator.EmailValidator;
import com.meyame.email.demo.domain.auth.model.request.SendOtpRequest;
import com.meyame.email.demo.domain.auth.model.response.SendOtpResponse;
import com.meyame.email.demo.domain.repository.OtpRepository;
import com.meyame.email.demo.domain.repository.UserRepository;
import com.meyame.email.demo.domain.repository.entity.OtpRequest;
import com.meyame.email.demo.domain.repository.entity.User;
import com.meyame.email.demo.security.OTP;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import static com.meyame.email.demo.common.constrants.Constrants.INVITE_QR_TEMPLATE;
import static com.meyame.email.demo.common.constrants.Constrants.SECRET;


@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final MailService mailService;

    // 이메일로 QR 코드 이미지를 보낸다.
    public SendOtpResponse sendOtp(SendOtpRequest request) {

        String email = request.email();

        // 1. 들어오는 요청이 유효한지 --> validator
        // 이메일이 유효하지 않으면 예외 발생
        if(!EmailValidator.isValidEmail(email)) {
            throw new CustomException(ErrorCode.NOT_VAILD_EMAIL_REQUEST);
        }else {
            // 유효하면 user 불러오기
            // email 에 매칭되면 바로 가져오지만, 매칭이 안되면 새롭게 user 를 만들어서 반환해준다.
            /*
            orElseGet() : findByEmail(email) 이 반환한 값이 Optional.empty() 면 실행된다.
            파라미터로 람다식을 받는다.
             */
            User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.save(
                    User.builder()
                            .email(email)
                            .is_valid(false)
                            .build()
            ));
            log.info("Get From DB {}", user.getEmail());

            // user 가 아직 유효하지 않다면 인증을 위해 OTP 전송
            if(!user.getIs_valid()) {
                // OTP 에 대한 Link 값을 생성하고
                String link = OTP.generateQRCodeURL(email,SECRET);

                // 생성된 link 를 AWS SES 에 담아서
                Map<String,String> data = Map.of(
                        "email",email,
                        "link",link
                );
                // 전송한다.
                mailService.sendTemplatedEmail(INVITE_QR_TEMPLATE,data,email);
            }
            return new SendOtpResponse(email);

        }

    }

    // 6자리 인증번호 생성 메서드
    private String generateOTPCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // 6자리 인증번호 검증
    public boolean verifyOtp(String email, String otpCode) {
        // 서버에 저장되어있는 optCode 와 파라미터로 받아온 otpCode 가 일치한지 확인한다.
        log.info("Verify OTP code {}", otpCode);
        log.info("Verify email {}", email);
        OtpRequest otpRequest = otpRepository.findByEmailAndOtpCode(email,otpCode)
                .orElseThrow(() -> new CustomException(ErrorCode.OTP_NOT_FOUND));

        // 만료 여부 확인
        if (otpRequest.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.OTP_EXPIRED);
        }

        // 같은지 확인
        if(!otpRequest.getOtpCode().equals(otpCode)) {
            throw new CustomException(ErrorCode.OPT_NUMBER_INCORRECT);
        }

        // 인증 여부를 true 로 변경
        OtpRequest updatedOtpRequest = otpRequest.withVerified(true);

        otpRepository.save(updatedOtpRequest);

        return true; // 인증 성공
    }

    // 이메일로 6자리 인증번호를 보낸다.
    public SendOtpResponse sendOTPCode(@Valid SendOtpRequest request) {
        // 먼저 request 에 담긴 email 을 뽑아온다.
        String email = request.email();

        // 들어오는 이메일이 유효한지 체크한다.
        if(!EmailValidator.isValidEmail(email)) {
            throw new CustomException(ErrorCode.NOT_VAILD_EMAIL_REQUEST);
        }else {
            User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.save(
                    User.builder()
                            .email(email)
                            .is_valid(false)
                            .build()
            ));
            log.info("GET FROM DB {}", user.getEmail());

            if(!user.getIs_valid()) {
                // 인증번호 6자리 생성 후 전송
                String otpCode = generateOTPCode();

                // 후에 인증번호 확인을 위해 DB 의 otp_requests 테이블에도 담아놓는다.
                // 우선 OtpRequest 객체를 만들어서 그 안에 담자.
//                OtpRequest otpRequest =  new OtpRequest.builder()
//                        .email(email)
//                        .otpCode(otpCode)
//                        .createdAt(LocalDateTime.now())
//                        .expiresAt(LocalDateTime.now().plusYears(3)) // 3 년후 만료
//                        .build();

                // DB 에 OTP 저장
                otpRepository.save(OtpRequest.builder()
                        .email(email)
                        .otpCode(otpCode)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusYears(3)) // 3 년후 만료
                        .build()
                );


                // 생성된 인증번호를 AWS SES 에 담는다
                Map<String, String> data = Map.of(
                        "email",email,
                        "sixOTPCode",otpCode
                );

                // 이메일 템플릿에 인증번호 포함하여 전송
                mailService.sendTemplatedEmail("verification-otp-code",data,email);
            }
            return new SendOtpResponse(email);
        }

    }
}
