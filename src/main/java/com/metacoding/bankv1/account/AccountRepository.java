package com.metacoding.bankv1.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AccountRepository {

    private final EntityManager em;

    public void updateByNumber(String password, int balance, int number) {
        // 여기서는 받은 걸 그대로 넣기만 한다 (서비스에서 연산 등을 하도록 넘긴다)
        // pw 변경 당장은 안하더라도 나중에 재활용 할 수 있도록 update할 수 있는 컬럼 전부 포함 (비지니스에 맞춰서 만들면 재사용 불가)
        Query query = em.createNativeQuery("update account_tb set password=?, balance=? where number=?");
        query.setParameter(1, password);
        query.setParameter(2, balance);
        query.setParameter(3, number);
        query.executeUpdate();
    }

    public void save(Integer number, String password, Integer balance, int userId) {
        Query query = em.createNativeQuery("insert into account_tb(number, password, balance, user_id, created_at) values (?, ?, ?, ?, now())");
        query.setParameter(1, number);
        query.setParameter(2, password);
        query.setParameter(3, balance);
        query.setParameter(4, userId);
        query.executeUpdate();
    }

    public List<Account> findAllByUserId(Integer userId) {
        Query query = em.createNativeQuery("select * from account_tb where user_id = ? order by created_at desc", Account.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public Account findByNumber(Integer number) {
        Query query = em.createNativeQuery("select * from account_tb where number = ?", Account.class);
        query.setParameter(1, number);
        try {
            return (Account) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /* 이 방식도 가능하지만 되도록 하나로 재사용 가능하도록 만드는 게 좋다
    public void updateWithdraw(int amount, int number) {
        Query query = em.createNativeQuery("update account_tb set balance = balance - ? where number = ?");
        query.setParameter(1, amount);
        query.setParameter(2, number);
        query.executeUpdate();
    }

    public void updateDeposit(int amount, int number) {
        Query query = em.createNativeQuery("update account_tb set balance = balance + ? where number = ?");
        query.setParameter(1, amount);
        query.setParameter(2, number);
        query.executeUpdate();
    }

    public void updatePassword(String password, int number) {
        Query query = em.createNativeQuery("update account_tb set password = ? where number = ?");
        query.setParameter(1, password);
        query.setParameter(2, number);
        query.executeUpdate();
    } */
}
