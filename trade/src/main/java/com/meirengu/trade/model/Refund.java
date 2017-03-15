package com.meirengu.trade.model;

import java.util.Date;
import java.sql.*;
 /*
  * Refund 实体类
  * Tue Mar 14 17:15:51 CST 2017 建新
  */
public class Refund{
	/** 退款记录ID */
	private int refundId;
	/** 退款编号 */
	private String refundSn;
	/** 订单ID */
	private int orderId;
	/** 订单编号 */
	private String orderSn;
	/** 第三方订单号 */
	private String thirdOrderSn;
	/** 第三方退单号 */
	private String thirdRefundSn;
	/** 项目ID */
	private int itemId;
	/** 关联合作方ID */
	private int partnerId;
	/** 买家ID */
	private int userId;
	/** 买家会员名 */
	private String userName;
	/** 买家手机号 */
	private String userPhone;
	/** 添加时间 */
	private Date addTime;
	/** 订单金额 */
	private double orderAmount;
	/** 退款金额 */
	private double orderRefund;
	/** 支付方式名称 */
	private String refundPaymentname;
	/** 支付方式代码 */
	private String refundPaymentcode;
	/** 退款备注 */
	private String refundMessage;
	/** 退款原因 */
	private String userMessage;
	/** 管理员处理原因 */
	private String adminMessage;
	/** 合作方处理时间 */
	private Date partnerTime;
	/** 管理员处理时间 */
	private Date adminTime;
	/** 买家确认收款时间 */
	private Date confirmTime;
	/** 类型:1为买家,2为卖家,默认为2 */
	private byte refundType;
	/** 状态:1为待处理,2为同意,3为拒绝,默认为2 */
	private byte refundState;
	/** 确认收款状态:1为待确认,2为已确认,默认为2 */
	private byte userConfirm;

	public void setRefundId(int refundId){
		this.refundId = refundId;
	}

	public int getRefundId(){
		return refundId;
	}

	public void setRefundSn(String refundSn){
		this.refundSn = refundSn;
	}

	public String getRefundSn(){
		return refundSn;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setOrderSn(String orderSn){
		this.orderSn = orderSn;
	}

	public String getOrderSn(){
		return orderSn;
	}

	public void setThirdOrderSn(String thirdOrderSn){
		this.thirdOrderSn = thirdOrderSn;
	}

	public String getThirdOrderSn(){
		return thirdOrderSn;
	}

	public void setThirdRefundSn(String thirdRefundSn){
		this.thirdRefundSn = thirdRefundSn;
	}

	public String getThirdRefundSn(){
		return thirdRefundSn;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setPartnerId(int partnerId){
		this.partnerId = partnerId;
	}

	public int getPartnerId(){
		return partnerId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}

	public String getUserPhone(){
		return userPhone;
	}

	public void setAddTime(Date addTime){
		this.addTime = addTime;
	}

	public Date getAddTime(){
		return addTime;
	}

	public void setOrderAmount(double orderAmount){
		this.orderAmount = orderAmount;
	}

	public double getOrderAmount(){
		return orderAmount;
	}

	public void setOrderRefund(double orderRefund){
		this.orderRefund = orderRefund;
	}

	public double getOrderRefund(){
		return orderRefund;
	}

	public void setRefundPaymentname(String refundPaymentname){
		this.refundPaymentname = refundPaymentname;
	}

	public String getRefundPaymentname(){
		return refundPaymentname;
	}

	public void setRefundPaymentcode(String refundPaymentcode){
		this.refundPaymentcode = refundPaymentcode;
	}

	public String getRefundPaymentcode(){
		return refundPaymentcode;
	}

	public void setRefundMessage(String refundMessage){
		this.refundMessage = refundMessage;
	}

	public String getRefundMessage(){
		return refundMessage;
	}

	public void setUserMessage(String userMessage){
		this.userMessage = userMessage;
	}

	public String getUserMessage(){
		return userMessage;
	}

	public void setAdminMessage(String adminMessage){
		this.adminMessage = adminMessage;
	}

	public String getAdminMessage(){
		return adminMessage;
	}

	public void setPartnerTime(Date partnerTime){
		this.partnerTime = partnerTime;
	}

	public Date getPartnerTime(){
		return partnerTime;
	}

	public void setAdminTime(Date adminTime){
		this.adminTime = adminTime;
	}

	public Date getAdminTime(){
		return adminTime;
	}

	public void setConfirmTime(Date confirmTime){
		this.confirmTime = confirmTime;
	}

	public Date getConfirmTime(){
		return confirmTime;
	}

	public void setRefundType(byte refundType){
		this.refundType = refundType;
	}

	public byte getRefundType(){
		return refundType;
	}

	public void setRefundState(byte refundState){
		this.refundState = refundState;
	}

	public byte getRefundState(){
		return refundState;
	}

	public void setUserConfirm(byte userConfirm){
		this.userConfirm = userConfirm;
	}

	public byte getUserConfirm(){
		return userConfirm;
	}

}
