package com.meyame.email.demo.domain.auth.service;

import com.meyame.email.demo.common.code.ErrorCode;
import com.meyame.email.demo.common.exception.CustomException;
import com.meyame.email.demo.common.validator.EmailValidator;
import com.meyame.email.demo.domain.auth.model.request.SendOtpRequest;
import com.meyame.email.demo.domain.auth.model.response.SendOtpResponse;
import com.meyame.email.demo.domain.repository.UserRepository;
import com.meyame.email.demo.domain.repository.entity.User;
import com.meyame.email.demo.security.OTP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.meyame.email.demo.common.constrants.Constrants.INVITE_QR_TEMPLATE;
import static com.meyame.email.demo.common.constrants.Constrants.SECRET;


@Slf4j
@Service
@RequiredArgsConstructor
public class OTPService {
    private final UserRepository userRepository;
    private final MailService mailService;

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

        // 2. @Transactional 을 활용한 Tx 처리

        // 3. OTP 전송하기


    }
}
