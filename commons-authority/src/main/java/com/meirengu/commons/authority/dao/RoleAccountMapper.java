package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.RoleAccount;

import java.util.List;

public interface RoleAccountMapper {
    int insertList(List<RoleAccount> list);
    int deleteByAccountId(Long accountId);
}