package com.meirengu.uc.service;

import com.meirengu.model.Result;

/**
 * Created by huoyan403 on 3/24/2017.
 */
public interface VerityService {


    Boolean selectPayAccountByUserId(Integer userId);


    Result verityUser(Integer userId, String bankCode, String bankIdcard, String bankPhone, String idcard, String realname, String password, Integer investConditions);
}
