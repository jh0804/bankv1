package com.metacoding.bankv1.account;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AccountResponse {

    @AllArgsConstructor
    @Data
    public static class DetailDTO {
        private int accountNumber;
        private int accountBalance;
        private String accountOwner;
        private String createdAt;
        private int wNumber;
        private int dNumber;
        private int amount;
        private int balance;
        private String type;
    }
}