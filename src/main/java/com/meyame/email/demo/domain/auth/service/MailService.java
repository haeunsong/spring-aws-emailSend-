package com.meyame.email.demo.domain.auth.service;

import com.google.gson.Gson;
import com.meyame.email.demo.common.code.ErrorCode;
import com.meyame.email.demo.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailResponse;

import java.util.Map;

import static com.meyame.email.demo.common.constrants.Constrants.COMMON_MAIL_SENDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    // SESConfig 에서 빈으로 선언이 되어있기 때문에 자동으로 빈 객체를 스프링부트가 시작될 때 주입된다.
    private final SesClient sesClient;

    // 템플릿 이메일을 전송하는 역할
    /*
    템플릿 이름, 템플릿 데이터, 수신자 목록을 파라미터로 받는다.
     */
    public void sendTemplatedEmail(
            String templateName,
            Map<String, String> templateData,
            String... receivers
    ){
        // 수신자 목록이 비어있는 경우 예외 발생
        if(receivers.length==0) {
            throw new CustomException(ErrorCode.MAIL_RECEIVER_REQUIRED);
        }
        SendTemplatedEmailResponse response = sesClient.sendTemplatedEmail(
                SendTemplatedEmailRequest.builder()
                        .source(COMMON_MAIL_SENDER) // 발신자 설정
                        .destination(Destination.builder().toAddresses(receivers).build()) // 수신자 목록 설정
                        .template(templateName) // AWS SES 에서 사용할 템플릿 이름 설정
                        // GSON 라이브러리로 Map<String,String> 형식의 데이터를 JSON 으로 직렬화
                        .templateData(new Gson().toJson(templateData)) // 동적으로 채워질 데이터. 템플릿에서 사용할 데이터를 JSON 형식으로 변환하여 전달
                        .build()
        );
        log.info("send Templated({}) email to : {}", templateName, String.join(",",receivers));

        // 이메일 전송 실패 시
        if(!response.sdkHttpResponse().isSuccessful()) {
            throw new CustomException(ErrorCode.MAIL_SEND_FAILED, response.sdkHttpResponse().toString());
        }
    }


}
