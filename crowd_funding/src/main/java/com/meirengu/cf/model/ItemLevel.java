package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.math.BigDecimal;
import java.util.Date;
 /*
  * ItemLevel 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class ItemLevel  extends BaseObject {
	/** 众筹档位索引id */
	private int levelId;
	/** 档位对应众筹项目id */
	private int itemId;
	/** 档位名称 */
	private String levelName;
	/** 档位支持金额 */
	private BigDecimal levelAmount;
	/** 档位回报描述 */
	private String levelDesc;
	/** 总份数，0即为无限制 */
	private int totalNumber;
	/** 单人限额 */
	private int singleLimitNumber;
	/** 预约份数 */
	private int bookNumber;
	/** 完成份数 */
	private int completedNumber;
	/** 回报天数 */
	private int paybackDays;
	/** 回报时间 */
	private Date paybackTime;
	/** 是否分红 */
	private int isShareBonus;
	/** 年化利率 */
	private int yearRate;
	/** 投资期限，以月为单位 */
	private int investmentPeriod;
	/** 收益方式：1、一次性还款（到期本息到账）；2、按月还息到期还本（每月计息，一月30天，一年360天计算） */
	private int revenueModel;
	/** 分红周期（多少月回款一次）1、1月；3、3月；6、6月；12、12月 */
	private int shareBonusPeriod;
	/** 是否需要地址 */
	private int isNeedAddress;
	/** 是否需要协议 */
	private int isNeedAgreement;
	/** 档位状态：1：预约中；2已预约满；3候补中；4认购中；5已完成 */
	private int levelStatus;
	/** 排序：数字越小权重越大 */
	private int levelSort;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setLevelId(int levelId){
		this.levelId = levelId;
	}

	public int getLevelId(){
		return levelId;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setLevelName(String levelName){
		this.levelName = levelName;
	}

	public String getLevelName(){
		return levelName;
	}

	public void setLevelAmount(BigDecimal levelAmount){
		this.levelAmount = levelAmount;
	}

	public BigDecimal getLevelAmount(){
		return levelAmount;
	}

	public void setLevelDesc(String levelDesc){
		this.levelDesc = levelDesc;
	}

	public String getLevelDesc(){
		return levelDesc;
	}

	public void setTotalNumber(int totalNumber){
		this.totalNumber = totalNumber;
	}

	public int getTotalNumber(){
		return totalNumber;
	}

	public void setSingleLimitNumber(int singleLimitNumber){
		this.singleLimitNumber = singleLimitNumber;
	}

	public int getSingleLimitNumber(){
		return singleLimitNumber;
	}

	public void setBookNumber(int bookNumber){
		this.bookNumber = bookNumber;
	}

	public int getBookNumber(){
		return bookNumber;
	}

	public void setCompletedNumber(int completedNumber){
		this.completedNumber = completedNumber;
	}

	public int getCompletedNumber(){
		return completedNumber;
	}

	public void setPaybackDays(int paybackDays){
		this.paybackDays = paybackDays;
	}

	public int getPaybackDays(){
		return paybackDays;
	}

	public void setPaybackTime(Date paybackTime){
		this.paybackTime = paybackTime;
	}

	public Date getPaybackTime(){
		return paybackTime;
	}

	public void setIsShareBonus(int isShareBonus){
		this.isShareBonus = isShareBonus;
	}

	public int getIsShareBonus(){
		return isShareBonus;
	}

	public void setYearRate(int yearRate){
		this.yearRate = yearRate;
	}

	public int getYearRate(){
		return yearRate;
	}

	public void setInvestmentPeriod(int investmentPeriod){
		this.investmentPeriod = investmentPeriod;
	}

	public int getInvestmentPeriod(){
		return investmentPeriod;
	}

	public void setRevenueModel(int revenueModel){
		this.revenueModel = revenueModel;
	}

	public int getRevenueModel(){
		return revenueModel;
	}

	public void setShareBonusPeriod(int shareBonusPeriod){
		this.shareBonusPeriod = shareBonusPeriod;
	}

	public int getShareBonusPeriod(){
		return shareBonusPeriod;
	}

	public void setIsNeedAddress(int isNeedAddress){
		this.isNeedAddress = isNeedAddress;
	}

	public int getIsNeedAddress(){
		return isNeedAddress;
	}

	public void setIsNeedAgreement(int isNeedAgreement){
		this.isNeedAgreement = isNeedAgreement;
	}

	public int getIsNeedAgreement(){
		return isNeedAgreement;
	}

	public void setLevelStatus(int levelStatus){
		this.levelStatus = levelStatus;
	}

	public int getLevelStatus(){
		return levelStatus;
	}

	public void setLevelSort(int levelSort){
		this.levelSort = levelSort;
	}

	public int getLevelSort(){
		return levelSort;
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
