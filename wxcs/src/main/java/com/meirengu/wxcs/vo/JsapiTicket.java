package com.meirengu.wxcs.vo;

/**
 *  <p>jsapi_ticket实体类</p>
 *  jsapi_ticket是公众号用于调用微信JS接口的临时票据，通过access_token来获取。
 */
public class JsapiTicket {

    int errcode;
    String errmsg;
    String ticket;
    long expires_in;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

}
