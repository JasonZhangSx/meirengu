package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.math.BigDecimal;
import java.util.Date;
 /*
  * Item 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class Item  extends BaseObject {
	/** 众筹项目索引id */
	private int itemId;
	/** 项目名称(不超过30字) */
	private String itemName;
	/** 项目简介 */
	private String itemProfile;
	/** 众筹类型id */
	private int typeId;
	/** 项目分类id */
	private int classId;
	/** 目标金额 */
	private BigDecimal targetAmount;
	 /** 预约金额 */
	 private BigDecimal appointAmount;
	/** 已筹金额 */
	private BigDecimal completedAmount;
	/** 预热天数 */
	private int preheatingDays;
	/** 预热开始时间 */
	private Date preheatingStartTime;
	/** 预热结束时间 */
	private Date preheatingEndTime;
	/** 众筹天数 */
	private int crowdDays;
	/** 众筹开始时间 */
	private Date crowdStartTime;
	/** 众筹结束时间 */
	private Date crowdEndTime;
	/** 合作方id */
	private int partnerId;
	/** 项目区域id */
	private int areaId;
	/** 项目头图 */
	private String headerImage;
	/** 状态：1:新建中；2:初审中；3初审通过；4初审未通过；5:设置合作中；6:复审中；7复审通过；8复审未通过；9待发布；10:预热中；11认筹中；12 已完成；13已下架 */
	private int itemStatus;
	/** 删除标识： 1 未删除  0 删除 */
	private int flag;
	/** 排序，数字越小权重越大 */
	private int itemSort;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;
	/** 项目发起人名称 */
	private String sponsorName;

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

	public void setItemProfile(String itemProfile){
		this.itemProfile = itemProfile;
	}

	public String getItemProfile(){
		return itemProfile;
	}

	public void setTypeId(int typeId){
		this.typeId = typeId;
	}

	public int getTypeId(){
		return typeId;
	}

	public void setClassId(int classId){
		this.classId = classId;
	}

	public int getClassId(){
		return classId;
	}

	public void setTargetAmount(BigDecimal targetAmount){
		this.targetAmount = targetAmount;
	}

	public BigDecimal getTargetAmount(){
		return targetAmount;
	}

	public void setCompletedAmount(BigDecimal completedAmount){
		this.completedAmount = completedAmount;
	}

	public BigDecimal getCompletedAmount(){
		return completedAmount;
	}

	public void setPreheatingDays(int preheatingDays){
		this.preheatingDays = preheatingDays;
	}

	public int getPreheatingDays(){
		return preheatingDays;
	}

	public void setPreheatingStartTime(Date preheatingStartTime){
		this.preheatingStartTime = preheatingStartTime;
	}

	public Date getPreheatingStartTime(){
		return preheatingStartTime;
	}

	public void setPreheatingEndTime(Date preheatingEndTime){
		this.preheatingEndTime = preheatingEndTime;
	}

	public Date getPreheatingEndTime(){
		return preheatingEndTime;
	}

	public void setCrowdDays(int crowdDays){
		this.crowdDays = crowdDays;
	}

	public int getCrowdDays(){
		return crowdDays;
	}

	public void setCrowdStartTime(Date crowdStartTime){
		this.crowdStartTime = crowdStartTime;
	}

	public Date getCrowdStartTime(){
		return crowdStartTime;
	}

	public void setCrowdEndTime(Date crowdEndTime){
		this.crowdEndTime = crowdEndTime;
	}

	public Date getCrowdEndTime(){
		return crowdEndTime;
	}

	public void setPartnerId(int partnerId){
		this.partnerId = partnerId;
	}

	public int getPartnerId(){
		return partnerId;
	}

	public void setAreaId(int areaId){
		this.areaId = areaId;
	}

	public int getAreaId(){
		return areaId;
	}

	public void setHeaderImage(String headerImage){
		this.headerImage = headerImage;
	}

	public String getHeaderImage(){
		return headerImage;
	}

	public void setItemStatus(int itemStatus){
		this.itemStatus = itemStatus;
	}

	public int getItemStatus(){
		return itemStatus;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}

	public void setItemSort(int itemSort){
		this.itemSort = itemSort;
	}

	public int getItemSort(){
		return itemSort;
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

	public void setSponsorName(String sponsorName){
		this.sponsorName = sponsorName;
	}

	public String getSponsorName(){
		return sponsorName;
	}

	 public BigDecimal getAppointAmount() {
		 return appointAmount;
	 }

	 public void setAppointAmount(BigDecimal appointAmount) {
		 this.appointAmount = appointAmount;
	 }
 }
