package com.meirengu.trade.model;

import java.util.Date;
import java.sql.*;
 /*
  * OrderCandidate 实体类
  * Tue Mar 14 17:15:51 CST 2017 建新
  */
public class OrderCandidate{
	/** 候补预约索引id */
	private int id;
	/** 用户id */
	private int userId;
	/** 用户名称 */
	private String userName;
	/** 用户手机号 */
	private String userPhone;
	/** 用户微信号 */
	private String userWeixin;
	/** 预约项目id */
	private int itemId;
	/** 预约项目名称 */
	private String itemName;
	/** 项目回报档位id */
	private int itemLevelId;
	/** 项目汇报档位名称 */
	private String itemLevelName;
	/** 预约份数 */
	private int itemNum;
	/** 订单总额 */
	private double orderAmount;
	/** 状态：0未处理；1已处理 */
	private int status;
	/** 预约时间 */
	private Date createTime;
	/** 处理时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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

	public void setUserWeixin(String userWeixin){
		this.userWeixin = userWeixin;
	}

	public String getUserWeixin(){
		return userWeixin;
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

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
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

}
