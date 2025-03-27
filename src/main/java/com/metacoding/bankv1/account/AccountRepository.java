package com.metacoding.bankv1.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AccountRepository {

    private final EntityManager em;

    public List<AccountResponse.DetailDTO> findAllByNumber(int number, String type) {
        String allSql = """
                select 
                dt.account_number,
                dt.account_balance,
                dt.account_owner,
                substr(created_at, 1, 16) created_at,
                withdraw_number w_number,
                deposit_number d_number,
                amount amount,
                case when withdraw_number = ? then withdraw_balance 
                else deposit_balance 
                end balance,
                case when withdraw_number = ? then '출금' 
                else '입금' 
                end type 
                from history_tb ht 
                inner join (select at.number account_number, at.balance account_balance, ut.fullname account_owner 
                from account_tb at 
                inner join user_tb ut on at.user_id = ut.id 
                where at.number = ?) dt on 1=1 
                where deposit_number = ? or withdraw_number = ?;
                """;

        String withdrawSql = """
                select 
                dt.account_number,
                dt.account_balance,
                dt.account_owner,
                substr(created_at, 1, 16) created_at,
                withdraw_number w_number,
                deposit_number d_number,
                amount amount,
                withdraw_balance balance,
                '출금' type
                from history_tb ht 
                inner join (select at.number account_number, at.balance account_balance, ut.fullname account_owner 
                from account_tb at 
                inner join user_tb ut on at.user_id = ut.id 
                where at.number = ?) dt on 1=1 
                where withdraw_number = ?;
                """;

        String depositSql = """
                select 
                dt.account_number,
                dt.account_balance,
                dt.account_owner,
                substr(created_at, 1, 16) created_at,
                withdraw_number w_number,
                deposit_number d_number,
                amount amount,
                deposit_balance balance,
                '입금' type 
                from history_tb ht 
                inner join (select at.number account_number, at.balance account_balance, ut.fullname account_owner 
                from account_tb at 
                inner join user_tb ut on at.user_id = ut.id 
                where at.number = ?) dt on 1=1 
                where deposit_number = ?;
                """;

        Query query = null;
        if (type.equals("입금")) {
            query = em.createNativeQuery(depositSql);
            query.setParameter(1, number);
            query.setParameter(2, number);
        } else if (type.equals("출금")) {
            query = em.createNativeQuery(withdrawSql);
            query.setParameter(1, number);
            query.setParameter(2, number);
        } else {
            query = em.createNativeQuery(allSql);
            query.setParameter(1, number);
            query.setParameter(2, number);
            query.setParameter(3, number);
            query.setParameter(4, number);
            query.setParameter(5, number);
        }

        List<Object[]> obsList = query.getResultList();
        List<AccountResponse.DetailDTO> detailList = new ArrayList<>();

        for (Object[] obs : obsList) {
            AccountResponse.DetailDTO detail =
                    new AccountResponse.DetailDTO(
                            (int) obs[0],
                            (int) obs[1],
                            (String) obs[2],
                            (String) obs[3],
                            (int) obs[4],
                            (int) obs[5],
                            (int) obs[6],
                            (int) obs[7],
                            (String) obs[8]
                    );
            detailList.add(detail);
        }
        return detailList;
    }

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
