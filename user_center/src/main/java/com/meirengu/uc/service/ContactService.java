package com.meirengu.uc.service;

import com.meirengu.model.Result;

import java.util.Map;

/**
 * Created by huoyan403 on 4/11/2017.
 */
public interface ContactService {

    Result CreateContactFile(Map<String,String> map);
}
