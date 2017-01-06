package com.meirengu.news.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 建新 on 2016/12/29.
 */
public class BaseEntity implements Serializable{

    /** 删除标识，0为删除，1为未删除 **/
    private int flag;
    /** 创建时间 **/
    private Date createTime;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
