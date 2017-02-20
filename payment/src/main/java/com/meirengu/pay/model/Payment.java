package com.meirengu.pay.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/**
 * 支付表
 * @author 建新
 * @create 2017-02-20 11:14
 */
public class Payment extends BaseObject{

    private Integer id;
    /** 商城订单号 */
    private String orderSN;
    /** 第三方订单号 */
    private String transactionSN;
    /** 支付类型 1.微信 */
    private Integer payType;
    /** 订单总金额 单位为元 */
    private Double totalFee;
    /** 支付银行 */
    private String bankType;
    /** 资金类型 1 付款 2 退款 */
    private Integer paymentType;
    /** 1 待支付 2 支付成功 3 支付失败 4 待退款 5 退款成功 6 退款失败 */
    private Integer status;
    /** 1 原路返回 */
    private Integer refundType;
    /** 设备号，WEB */
    private String deviceInfo;
    /** 支付回调返回信息 */
    private String returnMsg;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getTransactionSN() {
        return transactionSN;
    }

    public void setTransactionSN(String transactionSN) {
        this.transactionSN = transactionSN;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
