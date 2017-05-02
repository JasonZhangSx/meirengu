package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Account;

import java.util.List;

public interface AccountMapper {
    int insert(Account account);
    Account findUser(Account account);
    List<Account> getAllUser(Account account);
    int updateAccount(Account account);
}