package com.meyame.email.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

// Amazon Simple Email Service => AWS SDK for Java 사용
// 애플리케이션이 AWS SES 를 통해 이메일을 보낼 수 있게 설정하는 것이다.
@Configuration
public class SESConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public SesClient sesClient() {
        return SesClient.builder() // Amazon SES 클라이언트를 빌드
                .region(Region.of(region)) // SES 클라이언트가 어떤 AWS 리전에 연결할 지 설정
                .credentialsProvider( // Aws 자격증명을 제공하는 코드
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                ).build();
    }
}
