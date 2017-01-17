package com.meirengu.mall.model;

import java.math.BigInteger;
import java.util.Date;

/**
 * 订单实体类
 * @author 建新
 * @create 2017-01-10 17:15
 */
public class Order extends BaseEntity{

    private int id;
    /** 订单编号， 商品内部使用 **/
    private String orderSN;
    /** 关联医生id **/
    private int doctorId;
    /** 医院id **/
    private int hospitalId;
    /** 用户id **/
    private int userId;
    /** 买家姓名 **/
    private String userName;
    /** 买家手机号 **/
    private String userPhone;
    /** 买家电子邮箱 **/
    private String userEmail;
    /** 订单生成时间 **/
    private Date addTime;
    /** 订单类型 0.普通 1.团购 **/
    private int orderType;
    /** 支付方式id **/
    private int paymentId;
    /** 支付方式名称 **/
    private String paymentName;
    /** 支付方式code **/
    private String paymentCode;
    /** 支付类型:1是即时到帐,2是担保交易 **/
    private int paymentDirect;
    /** 订单联合编号  供合并订单付款使用 **/
    private String unionSN;
    /** 订单编号，外部支付时使用，有些外部支付系统要求特定的订单编号 **/
    private String outSN;
    /** 支付（付款）时间 **/
    private Date paymentTime;
    /** 支付留言 **/
    private String payMessage;
    /** 订单完成时间 **/
    private Date finishedTime;
    /** 发票信息 暂时没有 **/
    private String invoice;
    /** 项目总价格 **/
    private double itemAmount;
    /** 折扣价格 **/
    private double discount;
    /** 订单总价格 **/
    private double orderAmount;
    /** 订单留言 **/
    private String orderMessage;
    /** 订单状态 **/
    private int orderState;
    /** 退款状态 **/
    private int refundState;
    /** 退款总金额 **/
    private double refundAmount;
    /** 订单来源 1:pc，2:h5，3:ios，4:android，5:三方**/
    private int orderFrom;
    /** 删除状态 0为删除，1为未删除 **/
    private int flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public int getPaymentDirect() {
        return paymentDirect;
    }

    public void setPaymentDirect(int paymentDirect) {
        this.paymentDirect = paymentDirect;
    }

    public String getOutSN() {
        return outSN;
    }

    public void setOutSN(String outSN) {
        this.outSN = outSN;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPayMessage() {
        return payMessage;
    }

    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getUnionSN() {
        return unionSN;
    }

    public void setUnionSN(String unionSN) {
        this.unionSN = unionSN;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
