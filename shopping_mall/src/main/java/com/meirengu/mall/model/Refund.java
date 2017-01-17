package com.meirengu.mall.model;

import java.util.Date;

/**
 * 退款实体类
 *
 * @author 建新
 * @create 2017-01-12 17:07
 */
public class Refund extends BaseEntity {

    private int id;
    /** 订单id **/
    private int orderId;
    /** 退款编号 **/
    private String refundSN;
    /** 订单编号 **/
    private String orderSN;
    /** 关联医生id **/
    private int doctorId;
    /** 关联医院id **/
    private int hospitalId;
    /** 用户id **/
    private int userId;
    /** 买家名称 **/
    private String userName;
    /** 用户电话 **/
    private String userPhone;
    /** 添加时间 **/
    private Date addTime;
    /** 订单金额 **/
    private double orderAmount;
    /** 退款金额 **/
    private double orderRefund;
    /** 支付方式名称 **/
    private String refundPaymentname;
    /** 支付方式代码 **/
    private String refundPaymentcode;
    /** 退款备注 **/
    private String refundMessage;
    /** 退款原因 **/
    private String userMessage;
    /** 管理员处理原因 **/
    private String adminMessage;
    /** 管理员处理时间 **/
    private Date adminTime;
    /** **/
    private Date confirmTime;

    private int refundType;

    private int refundState;

    private int userConfirm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getRefundSN() {
        return refundSN;
    }

    public void setRefundSN(String refundSN) {
        this.refundSN = refundSN;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getOrderRefund() {
        return orderRefund;
    }

    public void setOrderRefund(double orderRefund) {
        this.orderRefund = orderRefund;
    }

    public String getRefundPaymentname() {
        return refundPaymentname;
    }

    public void setRefundPaymentname(String refundPaymentname) {
        this.refundPaymentname = refundPaymentname;
    }

    public String getRefundPaymentcode() {
        return refundPaymentcode;
    }

    public void setRefundPaymentcode(String refundPaymentcode) {
        this.refundPaymentcode = refundPaymentcode;
    }

    public String getRefundMessage() {
        return refundMessage;
    }

    public void setRefundMessage(String refundMessage) {
        this.refundMessage = refundMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public Date getAdminTime() {
        return adminTime;
    }

    public void setAdminTime(Date adminTime) {
        this.adminTime = adminTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getRefundType() {
        return refundType;
    }

    public void setRefundType(int refundType) {
        this.refundType = refundType;
    }

    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }

    public int getUserConfirm() {
        return userConfirm;
    }

    public void setUserConfirm(int userConfirm) {
        this.userConfirm = userConfirm;
    }
}
