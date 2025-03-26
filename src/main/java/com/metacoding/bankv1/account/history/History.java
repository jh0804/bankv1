package com.metacoding.bankv1.account.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "history_tb")
@Entity
public class History { // 어느 계좌가 어느 계좌에게

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer withdrawNumber; // 1111 (FK)
    private Integer depositNumber; // 2222 (FK)
    private Integer amount; // 100원
    private Integer withdrawBalance; // 900원 이체되는 그 시점의 잔액 의미
    private Timestamp createdAt;
}
