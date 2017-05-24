package com.meirengu.news.dao;
import com.meirengu.news.model.SignUpActivity;
import com.meirengu.dao.BaseDao;
/**
 * SignUpActivityDao 
 * @author 建新
 * @create Wed May 24 14:48:02 CST 2017
 */
public interface SignUpActivityDao extends BaseDao<SignUpActivity>{

    /**
     * 通过手机号和活动类型查重
     * @param signUpActivity
     * @return
     */
    int check(SignUpActivity signUpActivity);
}
