package com.meirengu.uc.dao;

import com.meirengu.uc.model.Contract;

import java.util.List;

/**
 * Created by huoyan403 on 4/13/2017.
 */
public interface ContractDao {

    int insert(Contract contract);

    List<Contract>  select(Contract contract);
}
