package com.meirengu.trade.model;

import java.util.Date;
import java.math.BigDecimal;
 /*
  * OrderCandidate 实体类
  * Tue Mar 14 17:15:51 CST 2017 建新
  */
public class OrderCandidate{
	/** 候补预约索引id */
	private Integer id;
	/** 用户id */
	private Integer userId;
	/** 用户名称 */
	private String userName;
	/** 用户手机号 */
	private String userPhone;
	/** 用户微信号 */
	private String userWeixin;
	/** 预约项目id */
	private Integer itemId;
	/** 预约项目名称 */
	private String itemName;
	/** 项目回报档位id */
	private Integer itemLevelId;
	/** 项目汇报档位名称 */
	private String itemLevelName;
	/** 预约份数 */
	private Integer itemNum;
	/** 订单总额 */
	private BigDecimal orderAmount;
	/** 状态：0未处理；1已处理 */
	private Integer status;
	/** 预约时间 */
	private Date createTime;
	/** 处理时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
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

	public void setUserWeixin(String userWeixin){
		this.userWeixin = userWeixin;
	}

	public String getUserWeixin(){
		return userWeixin;
	}

	public void setItemId(Integer itemId){
		this.itemId = itemId;
	}

	public Integer getItemId(){
		return itemId;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setItemLevelId(Integer itemLevelId){
		this.itemLevelId = itemLevelId;
	}

	public Integer getItemLevelId(){
		return itemLevelId;
	}

	public void setItemLevelName(String itemLevelName){
		this.itemLevelName = itemLevelName;
	}

	public String getItemLevelName(){
		return itemLevelName;
	}

	public void setItemNum(Integer itemNum){
		this.itemNum = itemNum;
	}

	public Integer getItemNum(){
		return itemNum;
	}

	public void setOrderAmount(BigDecimal orderAmount){
		this.orderAmount = orderAmount;
	}

	public BigDecimal getOrderAmount(){
		return orderAmount;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
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
