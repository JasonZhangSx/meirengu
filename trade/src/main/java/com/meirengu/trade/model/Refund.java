package com.meirengu.trade.model;

import java.util.Date;
import java.math.BigDecimal;
 /*
  * Refund 实体类
  * Tue Mar 14 17:15:51 CST 2017 建新
  */
public class Refund{
	/** 退款记录ID */
	private Integer refundId;
	/** 退款编号 */
	private String refundSn;
	/** 订单ID */
	private Integer orderId;
	/** 订单编号 */
	private String orderSn;
	/** 第三方订单号 */
	private String thirdOrderSn;
	/** 第三方退单号 */
	private String thirdRefundSn;
	/** 项目ID */
	private Integer itemId;
	/** 关联合作方ID */
	private Integer partnerId;
	/** 买家ID */
	private Integer userId;
	/** 买家会员名 */
	private String userName;
	/** 买家手机号 */
	private String userPhone;
	/** 添加时间 */
	private Date createTime;
	/** 订单金额 */
	private BigDecimal orderAmount;
	/** 退款金额 */
	private BigDecimal orderRefund;
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
	private Integer refundType;
	/** 状态:1为待处理,2为同意,3为拒绝,默认为2 */
	private Integer refundState;
	/** 确认收款状态:1为待确认,2为已确认,默认为2 */
	private Integer userConfirm;

	public void setRefundId(Integer refundId){
		this.refundId = refundId;
	}

	public Integer getRefundId(){
		return refundId;
	}

	public void setRefundSn(String refundSn){
		this.refundSn = refundSn;
	}

	public String getRefundSn(){
		return refundSn;
	}

	public void setOrderId(Integer orderId){
		this.orderId = orderId;
	}

	public Integer getOrderId(){
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

	public void setItemId(Integer itemId){
		this.itemId = itemId;
	}

	public Integer getItemId(){
		return itemId;
	}

	public void setPartnerId(Integer partnerId){
		this.partnerId = partnerId;
	}

	public Integer getPartnerId(){
		return partnerId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
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

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setOrderAmount(BigDecimal orderAmount){
		this.orderAmount = orderAmount;
	}

	public BigDecimal getOrderAmount(){
		return orderAmount;
	}

	public void setOrderRefund(BigDecimal orderRefund){
		this.orderRefund = orderRefund;
	}

	public BigDecimal getOrderRefund(){
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

	public void setRefundType(Integer refundType){
		this.refundType = refundType;
	}

	public Integer getRefundType(){
		return refundType;
	}

	public void setRefundState(Integer refundState){
		this.refundState = refundState;
	}

	public Integer getRefundState(){
		return refundState;
	}

	public void setUserConfirm(Integer userConfirm){
		this.userConfirm = userConfirm;
	}

	public Integer getUserConfirm(){
		return userConfirm;
	}

}
