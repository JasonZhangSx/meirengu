package com.meirengu.trade.model;

import java.util.Date;
 /*
  * Order 实体类
  * Tue Mar 14 17:15:51 CST 2017 建新
  */
public class Order{
	/** 众筹订单索引id */
	private int orderId;
	/** 众筹订单编号，内部使用编号 */
	private String orderSn;
	/** 众筹项目id */
	private int itemId;
	/** 众筹项目名称 */
	private String itemName;
	/** 众筹档位id */
	private int itemLevelId;
	/** 档位名称 */
	private String itemLevelName;
	/** 档位价格 */
	private double itemLevelAmount;
	/** 众筹份数 */
	private int itemNum;
	/** 订单总价格 */
	private double orderAmount;
	/** 本金（本金=订单总价格-抵扣券金额） */
	private double costAmount;
	/** 抵扣券金额 */
	private double rebateAmount;
	/** 买家id */
	private int userId;
	/** 买家姓名 */
	private String userName;
	/** 买家手机号 */
	private String userPhone;
	/** 买家电子邮箱 */
	private String userEmail;
	/** 买家收货地址 */
	private int userAddressId;
	/** 订单生成时间 */
	private Date createTime;
	/** 订单类型 1.普通 2.其他 */
	private int orderType;
	/** 订单编号，外部支付时使用，有些外部支付系统要求特定的订单编号 */
	private String outSn;
	/** 订单完成时间 */
	private Date finishedTime;
	/** 发票信息 */
	private String receipt;
	/** 订单留言 */
	private String orderMessage;
	/** 订单状态：1预约待审核；2预约审核通过（前端显示未付款状态）3预约审核未通过（前端显示已关闭状态） 4未付款；5已失效（前端显示已关闭状态） 6已付款 7未消费失效 8已消费  9申请退款  10确认退款  11拒绝退款 12退款成功 13支付失败 */
	private int orderState;
	/** 逻辑删除状态 0为删除，1为未删除 */
	private int flag;
	/** 1:pc，2:h5，3:ios，4:android，5：三方 */
	private String orderFrom;
	/** 修改时间 */
	private Date updateTime;
	/** 审核操作人账号 */
	private String operateAccount;
	/** 用户微信号 */
	private String userWeixin;

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

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setItemLevelId(int itemLevelId){
		this.itemLevelId = itemLevelId;
	}

	public int getItemLevelId(){
		return itemLevelId;
	}

	public void setItemLevelName(String itemLevelName){
		this.itemLevelName = itemLevelName;
	}

	public String getItemLevelName(){
		return itemLevelName;
	}

	public void setItemLevelAmount(double itemLevelAmount){
		this.itemLevelAmount = itemLevelAmount;
	}

	public double getItemLevelAmount(){
		return itemLevelAmount;
	}

	public void setItemNum(int itemNum){
		this.itemNum = itemNum;
	}

	public int getItemNum(){
		return itemNum;
	}

	public void setOrderAmount(double orderAmount){
		this.orderAmount = orderAmount;
	}

	public double getOrderAmount(){
		return orderAmount;
	}

	public void setCostAmount(double costAmount){
		this.costAmount = costAmount;
	}

	public double getCostAmount(){
		return costAmount;
	}

	public void setRebateAmount(double rebateAmount){
		this.rebateAmount = rebateAmount;
	}

	public double getRebateAmount(){
		return rebateAmount;
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

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public void setUserAddressId(int userAddressId){
		this.userAddressId = userAddressId;
	}

	public int getUserAddressId(){
		return userAddressId;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setOrderType(int orderType){
		this.orderType = orderType;
	}

	public int getOrderType(){
		return orderType;
	}

	public void setOutSn(String outSn){
		this.outSn = outSn;
	}

	public String getOutSn(){
		return outSn;
	}

	public void setFinishedTime(Date finishedTime){
		this.finishedTime = finishedTime;
	}

	public Date getFinishedTime(){
		return finishedTime;
	}

	public void setReceipt(String receipt){
		this.receipt = receipt;
	}

	public String getReceipt(){
		return receipt;
	}

	public void setOrderMessage(String orderMessage){
		this.orderMessage = orderMessage;
	}

	public String getOrderMessage(){
		return orderMessage;
	}

	public void setOrderState(int orderState){
		this.orderState = orderState;
	}

	public int getOrderState(){
		return orderState;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}

	public void setOrderFrom(String orderFrom){
		this.orderFrom = orderFrom;
	}

	public String getOrderFrom(){
		return orderFrom;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return updateTime;
	}

	public void setOperateAccount(String operateAccount){
		this.operateAccount = operateAccount;
	}

	public String getOperateAccount(){
		return operateAccount;
	}

	public void setUserWeixin(String userWeixin){
		this.userWeixin = userWeixin;
	}

	public String getUserWeixin(){
		return userWeixin;
	}

}
