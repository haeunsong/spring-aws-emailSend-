package com.meyame.email.demo.domain.repository;

import com.meyame.email.demo.domain.repository.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpRequest, Integer> {

    Optional<OtpRequest> findByEmailAndOtpCode(String email, String otpCode);
}
