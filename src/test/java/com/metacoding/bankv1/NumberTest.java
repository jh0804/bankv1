package com.metacoding.bankv1;

import org.junit.jupiter.api.Test;

public class NumberTest {

    @Test
    public void num_test() {
        // given
        Integer withdrawUserId = 200;
        Integer sessionUserId = 200;

        // when
        if (withdrawUserId.equals(sessionUserId)) {
            throw new RuntimeException("출금계좌의 권한이 없습니다");
        }
    }
}