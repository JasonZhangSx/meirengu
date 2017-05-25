package com.meirengu.news.service.impl;
import com.meirengu.news.dao.SignUpActivityDao;
import com.meirengu.news.model.SignUpActivity;
import com.meirengu.news.service.SignUpActivityService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SignUpActivity服务实现层 
 * @author 建新
 * @create Wed May 24 14:48:02 CST 2017
 */
@Service
public class SignUpActivityServiceImpl extends BaseServiceImpl<SignUpActivity> implements SignUpActivityService{

    @Autowired
    SignUpActivityDao signUpActivityDao;

    @Override
    public int check(String telphone, int type) {
        SignUpActivity signUpActivity = new SignUpActivity();
        signUpActivity.setTelphone(telphone);
        signUpActivity.setType(type);
        return signUpActivityDao.check(signUpActivity);
    }
}
