package com.meyame.email.demo.domain.repository;

import com.meyame.email.demo.domain.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email); // 유저가 존재할지 안할지 모르므로 Optional
}
