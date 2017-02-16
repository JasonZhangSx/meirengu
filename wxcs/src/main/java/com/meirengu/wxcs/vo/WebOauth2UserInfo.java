package com.meirengu.wxcs.vo;

import java.util.List;

public class WebOauth2UserInfo {
    String openid;
    String nickname;
    String sex;
    String language;
    String city;
    String province;
    String country;
    String headimgurl;
    List<WxPrivilege> privilege;
    String unionid;
    
    
    public String getOpenid() {
        return openid;
    }


    public void setOpenid(String openid) {
        this.openid = openid;
    }


    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getProvince() {
        return province;
    }


    public void setProvince(String province) {
        this.province = province;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getHeadimgurl() {
        return headimgurl;
    }


    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }


    public List<WxPrivilege> getPrivilege() {
        return privilege;
    }


    public void setPrivilege(List<WxPrivilege> privilege) {
        this.privilege = privilege;
    }


    public String getUnionid() {
        return unionid;
    }


    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }


    class WxPrivilege{
        String privilege;

        public String getPrivilege() {
            return privilege;
        }

        public void setPrivilege(String privilege) {
            this.privilege = privilege;
        }
        
    }
}
