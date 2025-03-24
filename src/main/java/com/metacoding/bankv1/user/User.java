package com.metacoding.bankv1.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

// 기본
@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 12)
    private String username;

    @Column(nullable = false, length = 12)
    private String password;

    @Column(nullable = false)
    private String fullname;

    private Timestamp createdAt; // 생성날짜 (insert 된 시간)
}
