package com.meirengu.wxcs.vo;

/**
 *  <p>access_token实体类</p>
 *  access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
 */
public class AccessToken {

    /** 获取到的凭证*/
    String access_token;
    /** 凭证有效时间，单位：秒*/
    long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

}
