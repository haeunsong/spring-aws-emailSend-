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

    public void sendTemplatedEmail(
            String templateName,
            Map<String, String> templateData,
            String... receivers
    ){
        if(receivers.length==0) {
            throw new CustomException(ErrorCode.MAIL_RECEIVER_REQUIRED);
        }
        SendTemplatedEmailResponse response = sesClient.sendTemplatedEmail(
                SendTemplatedEmailRequest.builder()
                        .source(COMMON_MAIL_SENDER)
                        .destination(Destination.builder().toAddresses(receivers).build())
                        .template(templateName)
                        .templateData(new Gson().toJson(templateData))
                        .build()
        );
        log.info("send Templated({}) email to : {}", templateName, String.join(",",receivers));

        if(!response.sdkHttpResponse().isSuccessful()) {
            throw new CustomException(ErrorCode.MAIL_SEND_FAILED, response.sdkHttpResponse().toString());
        }
    }


}
