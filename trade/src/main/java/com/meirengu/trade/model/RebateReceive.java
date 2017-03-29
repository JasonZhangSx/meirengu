package com.meirengu.trade.model;

import com.meirengu.model.BaseObject;

import java.util.Date;
 /*
  * RebateReceive 实体类
  * Thu Mar 23 18:18:22 CST 2017 建新
  */
public class RebateReceive  extends BaseObject {
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
	/** 抵扣券标识 */
	private Integer rebateMark;
	/** 活动标识 */
	private String activityIdentification;
	/** 领取时间 */
	private Date receiveTime;
	/** 状态：1未使用，2已使用，3已失效 */
	private Integer status;

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

	 public Integer getRebateMark() {
		 return rebateMark;
	 }

	 public void setRebateMark(Integer rebateMark) {
		 this.rebateMark = rebateMark;
	 }

	 public void setActivityIdentification(String activityIdentification){
		this.activityIdentification = activityIdentification;
	}

	public String getActivityIdentification(){
		return activityIdentification;
	}

	public void setReceiveTime(Date receiveTime){
		this.receiveTime = receiveTime;
	}

	public Date getReceiveTime(){
		return receiveTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return status;
	}

}
