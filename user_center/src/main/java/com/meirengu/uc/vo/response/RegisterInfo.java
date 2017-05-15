package com.meirengu.uc.vo.response;

/**
 * Created by huoyan403 on 3/14/2017.
 */
public class RegisterInfo {

    public UserVO userVO;
    public String token;

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
