package com.meirengu.trade.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * Rebate 实体类
  * Thu Mar 23 18:18:22 CST 2017 建新
  */
public class Rebate  extends BaseObject {
	/** 抵扣券索引ID */
	private Integer rebateId;
	/** 抵扣券编号 */
	private String rebateSn;
	/** 抵扣券批次 */
	private Integer rebateBatch;
	/** 来源渠道 */
	private String channel;
	/** 有效期开始时间 */
	private Date validStartTime;
	/** 有效期截止时间 */
	private Date validEndTime;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setRebateId(Integer rebateId){
		this.rebateId = rebateId;
	}

	public Integer getRebateId(){
		return rebateId;
	}

	public void setRebateSn(String rebateSn){
		this.rebateSn = rebateSn;
	}

	public String getRebateSn(){
		return rebateSn;
	}

	public void setRebateBatch(Integer rebateBatch){
		this.rebateBatch = rebateBatch;
	}

	public Integer getRebateBatch(){
		return rebateBatch;
	}

	public void setChannel(String channel){
		this.channel = channel;
	}

	public String getChannel(){
		return channel;
	}

	public void setValidStartTime(Date validStartTime){
		this.validStartTime = validStartTime;
	}

	public Date getValidStartTime(){
		return validStartTime;
	}

	public void setValidEndTime(Date validEndTime){
		this.validEndTime = validEndTime;
	}

	public Date getValidEndTime(){
		return validEndTime;
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
