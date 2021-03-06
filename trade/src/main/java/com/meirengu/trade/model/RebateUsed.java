package com.meirengu.trade.model;

import com.meirengu.model.BaseObject;
import java.math.BigDecimal;
import java.util.Date;
 /*
  * RebateUsed 实体类
  * Thu Mar 23 18:18:22 CST 2017 建新
  */
public class RebateUsed  extends BaseObject {
	/** 抵扣券领取索引ID */
	private Integer id;
	/** 领取用户ID */
	private Integer userId;
	/** 领取手机号ID */
	private String userPhone;
	/** 抵扣券编号 */
	private String rebateSn;
	/** 抵扣券批次ID */
	private Integer rebateBatchId;
	/** 活动标识 */
	private String activityIdentification;
	/** 订单编号 */
	private String orderSn;
	/** 抵扣券金额 */
	private BigDecimal rebateAmount;
	/** 使用时间 */
	private Date createTime;
	/** 核销状态 */
	private Integer verifyStatus;
	 /** 核销时间 */
	 private Date verifyTime;

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

	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}

	public String getUserPhone(){
		return userPhone;
	}

	public void setRebateSn(String rebateSn){
		this.rebateSn = rebateSn;
	}

	public String getRebateSn(){
		return rebateSn;
	}

	public void setRebateBatchId(Integer rebateBatchId){
		this.rebateBatchId = rebateBatchId;
	}

	public Integer getRebateBatchId(){
		return rebateBatchId;
	}

	public void setActivityIdentification(String activityIdentification){
		this.activityIdentification = activityIdentification;
	}

	public String getActivityIdentification(){
		return activityIdentification;
	}

	public void setOrderSn(String orderSn){
		this.orderSn = orderSn;
	}

	public String getOrderSn(){
		return orderSn;
	}

	public void setRebateAmount(BigDecimal rebateAmount){
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getRebateAmount(){
		return rebateAmount;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTimeTime(){
		return createTime;
	}

	 public Integer getVerifyStatus() {
		 return verifyStatus;
	 }

	 public void setVerifyStatus(Integer verifyStatus) {
		 this.verifyStatus = verifyStatus;
	 }

	 public Date getVerifyTime() {
		 return verifyTime;
	 }

	 public void setVerifyTime(Date verifyTime) {
		 this.verifyTime = verifyTime;
	 }
 }
