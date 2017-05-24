package com.meirengu.news.service;
import com.meirengu.news.model.SignUpActivity;
import com.meirengu.service.BaseService;

/**
 * SignUpActivity服务接口 
 * @author 建新
 * @create Wed May 24 14:48:02 CST 2017
 */
public interface SignUpActivityService extends BaseService<SignUpActivity>{

    /**
     * 通过手机号和活动类型查重
     * @param telphone
     * @param type
     * @return
     */
    int check(String telphone, int type);
}
