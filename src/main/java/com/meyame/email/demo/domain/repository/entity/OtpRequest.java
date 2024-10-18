package com.meyame.email.demo.domain.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name="otp_requests")
public class OtpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    @Column(name = "otp_code")
    private String otpCode; // otp_code 컬럼과 매핑된 필드

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // 만료 시간

    @Column(name = "is_verified")
    private Boolean isVerified; // 인증 여부 (true or false)

    public OtpRequest(){
        this.isVerified = false; // 기본값 설정
    }
    // isVerified 수정할 수 있도록 메서드 추가 (Setter 안쓰려고)
    public OtpRequest withVerified(Boolean isVerified) {
        return OtpRequest.builder()
                .id(this.id)
                .email(this.email)
                .otpCode(this.otpCode)
                .createdAt(this.createdAt)
                .expiresAt(this.expiresAt)
                .isVerified(isVerified)
                .build();
    }

}
