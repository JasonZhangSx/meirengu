package com.meirengu.mall.model;

import java.util.Date;

/**
 * 购物车实体类
 * @author 建新
 * @create 2017-01-10 17:15
 */
public class Cart extends BaseEntity{

    private int id;
    /** 用户id **/
    private int userId;
    /** 医院id **/
    private int hospitalId;
    /** 项目商品id **/
    private int itemId;
    /** 购买数量 **/
    private int itemNum;
    /** 状态  0未生成订单 1生成订单 2删除 **/
    private int state;
    /** 创建时间 **/
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
