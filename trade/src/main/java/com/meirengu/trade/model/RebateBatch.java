package com.meirengu.trade.model;

import com.meirengu.model.BaseObject;
import java.math.BigDecimal;
import java.util.Date;
 /*
  * RebateBatch 实体类
  * Thu Mar 23 18:18:22 CST 2017 建新
  */
public class RebateBatch  extends BaseObject {
	/** 抵扣券批次索引ID */
	private Integer batchId;
	/** 抵扣券类别：1通用型，2满减型，3固定项目抵扣券 */
	private Integer rebateType;
	/** 抵扣券名称 */
	private String rebateName;
	/** 抵扣券适用范围 */
	private String rebateScope;
	/** 抵扣券金额 */
	private BigDecimal rebateAmount;
	/** 抵扣券使用限额 */
	private Integer rebateLimit;
	/** 抵扣券有效天数 */
	private Integer validDays;
	/** 有效期开始时间 */
	private Date validStartTime;
	/** 有效期截止时间 */
	private Date validEndTime;
	/** 有效期判断类型：1、按有效期截止时间过期，2按有效天数计算过期截止时间 */
	private Integer validType;
	/** 批次状态：1有效，0无效 */
	private Integer batchStatue;
	/** 批次抵扣券发放数量 */
	private Integer batchCount;
	/** 来源渠道 */
	private String channel;
	/** 预算金额 */
	private BigDecimal budgetAmount;
	/** 备注 */
	private String remarks;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setBatchId(Integer batchId){
		this.batchId = batchId;
	}

	public Integer getBatchId(){
		return batchId;
	}

	public void setRebateType(Integer rebateType){
		this.rebateType = rebateType;
	}

	public Integer getRebateType(){
		return rebateType;
	}

	public void setRebateName(String rebateName){
		this.rebateName = rebateName;
	}

	public String getRebateName(){
		return rebateName;
	}

	public void setRebateScope(String rebateScope){
		this.rebateScope = rebateScope;
	}

	public String getRebateScope(){
		return rebateScope;
	}

	public void setRebateAmount(BigDecimal rebateAmount){
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getRebateAmount(){
		return rebateAmount;
	}

	public void setRebateLimit(Integer rebateLimit){
		this.rebateLimit = rebateLimit;
	}

	public Integer getRebateLimit(){
		return rebateLimit;
	}

	public void setValidDays(Integer validDays){
		this.validDays = validDays;
	}

	public Integer getValidDays(){
		return validDays;
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

	public void setValidType(Integer validType){
		this.validType = validType;
	}

	public Integer getValidType(){
		return validType;
	}

	public void setBatchStatue(Integer batchStatue){
		this.batchStatue = batchStatue;
	}

	public Integer getBatchStatue(){
		return batchStatue;
	}

	public void setBatchCount(Integer batchCount){
		this.batchCount = batchCount;
	}

	public Integer getBatchCount(){
		return batchCount;
	}

	public void setChannel(String channel){
		this.channel = channel;
	}

	public String getChannel(){
		return channel;
	}

	public void setBudgetAmount(BigDecimal budgetAmount){
		this.budgetAmount = budgetAmount;
	}

	public BigDecimal getBudgetAmount(){
		return budgetAmount;
	}

	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getRemarks(){
		return remarks;
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
