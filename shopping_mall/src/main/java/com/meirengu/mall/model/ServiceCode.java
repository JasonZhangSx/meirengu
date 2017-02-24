package com.meirengu.mall.model;

import java.util.Date;

/**
 * 服务码
 * @author 建新
 * @create 2017-02-23 17:45
 */
public class ServiceCode {

    private Integer id;
    /** 服务码 */
    private Integer code;
    /** 医院id */
    private Integer hospitalId;
    /** 项目id */
    private Integer itemId;
    /** 订单号 */
    private String orderSN;
    /** 用户手机号 */
    private String userPhone;
    /** 项目描述（标题、简介等） */
    private String itemDesc;
    /** 医院价格 */
    private Double originalPrice;
    /** 预约金 */
    private Double orderPrice;
    /** 到院再支付 */
    private Double restPrice;
    /** 创建时间 */
    private Date createTime;
    /** 失效时间 */
    private Date expireTime;
    /** 是否使用 */
    private Integer is_use;
    /** 使用时间 */
    private Date usingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getRestPrice() {
        return restPrice;
    }

    public void setRestPrice(Double restPrice) {
        this.restPrice = restPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getIs_use() {
        return is_use;
    }

    public void setIs_use(Integer is_use) {
        this.is_use = is_use;
    }

    public Date getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Date usingTime) {
        this.usingTime = usingTime;
    }
}
