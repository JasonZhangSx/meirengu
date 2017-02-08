package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.CheckCodeDao;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.service.CheckCodeService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午7:24
 */
@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    @Autowired
    CheckCodeDao checkCodeDao;


    @Override
    public int generate() {
        return RandomUtils.nextInt(100000,999999);
    }

    @Override
    public int create(CheckCode checkCode) {
        return checkCodeDao.create(checkCode);
    }

    @Override
    public int update(CheckCode checkCode) {
        return checkCodeDao.update(checkCode);
    }

    @Override
    public CheckCode retrieve(String mobile, int code) {
        Map params = new HashMap();
        params.put("mobile", mobile);
        params.put("code", code);
        return checkCodeDao.retrieve(params);
    }
}
