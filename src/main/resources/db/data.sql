insert into user_tb(username, password, fullname, created_at)
values ('ssar', '1234', '쌀', now());
insert into user_tb(username, password, fullname, created_at)
values ('cos', '1234', '코스', now());

insert into account_tb(number, password, balance, user_id, created_at)
values (1111, '1234', 900, 1, now());
insert into account_tb(number, password, balance, user_id, created_at)
values (2222, '1234', 1100, 1, now());
insert into account_tb(number, password, balance, user_id, created_at)
values (3333, '1234', 1000, 2, now());

insert into history_tb(withdraw_number, deposit_number, amount, withdraw_balance, deposit_balance, created_at)
values (1111, 2222, 100, 900, 1100, now());
insert into history_tb(withdraw_number, deposit_number, amount, withdraw_balance, deposit_balance, created_at)
values (1111, 3333, 100, 800, 1100, now());
insert into history_tb(withdraw_number, deposit_number, amount, withdraw_balance, deposit_balance, created_at)
values (3333, 1111, 100, 1000, 900, now());

