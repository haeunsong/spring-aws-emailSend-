package com.meyame.email.demo.domain.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED) // JPA 에서는 entity 를 사용할 때 기본적으로 기본생성자를 필요로 한다.
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long t_id;

    @Column
    private String email;

    @Column(nullable = false)
    private Boolean is_valid;


}
