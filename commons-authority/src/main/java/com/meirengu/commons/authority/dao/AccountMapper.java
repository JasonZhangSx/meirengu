package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Account;

public interface AccountMapper {
    Account findUser(Account account);
}