package com.metacoding.bankv1.account;

import lombok.Data;

public class AccountRequest {

    @Data
    public static class SaveDTO {
        private Integer number;
        private String password;
        private Integer balance;
    }
}
