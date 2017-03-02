package com.meirengu.mall.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服务码
 * @author 建新
 * @create 2017-02-23 17:45
 */
public class ServiceCode {

    private Integer id;
    /** 服务码 */
    private String code;
    /** 医院id */
    private Integer hospitalId;
    /** 医院电话 */
    private String hospitalTel;
    /** 项目id */
    private Integer itemId;
    /** 订单号 */
    private String orderSN;
    /** 用户手机号 */
    private String userPhone;
    /** 项目描述（标题、简介等） */
    private String itemDesc;
    /** 医院价格 */
    private BigDecimal originalPrice;
    /** 预约金 */
    private BigDecimal orderPrice;
    /** 到院再支付 */
    private BigDecimal restPrice;
    /** 创建时间 */
    private Date createTime;
    /** 失效时间 */
    private Date expireTime;
    /** 是否使用 */
    private Integer isUse;
    /** 使用时间 */
    private Date usingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalTel() {
        return hospitalTel;
    }

    public void setHospitalTel(String hospitalTel) {
        this.hospitalTel = hospitalTel;
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getRestPrice() {
        return restPrice;
    }

    public void setRestPrice(BigDecimal restPrice) {
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

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Date getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Date usingTime) {
        this.usingTime = usingTime;
    }
}
