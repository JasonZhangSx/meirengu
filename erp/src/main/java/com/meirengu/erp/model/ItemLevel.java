package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

/*
* ItemLevel 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class ItemLevel  extends BaseObject {
   /** 众筹档位索引id */
   private Integer levelId;
   /** 档位对应众筹项目id */
   private Integer itemId;
   /** 档位名称 */
   private String levelName;
   /** 档位支持金额 */
   private BigDecimal levelAmount;
   /** 档位回报描述 */
   private String levelDesc;
   /** 总份数，0即为无限制 */
   private Integer totalNumber;
   /** 单人限额 */
   private Integer singleLimitNumber;
   /** 预约份数 */
   private Integer bookNumber;
   /** 完成份数 */
   private Integer completedNumber;
   /** 回报天数 */
   private Integer paybackDays;
   /** 回报时间 */
   private Date paybackTime;
   /** 是否分红 */
   private Integer isShareBonus;
   /** 年化利率 */
   private double yearRate;
   /** 投资期限，以月为单位 */
   private Integer investmentPeriod;
   /** 收益方式：1、一次性还款（到期本息到账）；2、按月还息到期还本（每月计息，一月30天，一年360天计算） */
   private Integer revenueModel;
   /** 分红周期（多少月回款一次）1、1月；3、3月；6、6月；12、12月 */
   private Integer shareBonusPeriod;
   /** 是否需要地址 */
   private Integer isNeedAddress;
   /** 是否需要协议 */
   private Integer isNeedAgreement;
   /** 档位状态：1：预约中；2已预约满；3候补中；4认购中；5已完成 */
   private Integer levelStatus;
   /** 排序：数字越小权重越大 */
   private Integer levelSort;
   /** 创建时间 */
   private Date createTime;
   /** 修改时间 */
   private Date updateTime;
   /** 操作人账号 */
   private String operateAccount;

   public void setLevelId(Integer levelId){
       this.levelId = levelId;
   }

   public Integer getLevelId(){
       return levelId;
   }

   public void setItemId(Integer itemId){
       this.itemId = itemId;
   }

   public Integer getItemId(){
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

   public void setTotalNumber(Integer totalNumber){
       this.totalNumber = totalNumber;
   }

   public Integer getTotalNumber(){
       return totalNumber;
   }

   public void setSingleLimitNumber(Integer singleLimitNumber){
       this.singleLimitNumber = singleLimitNumber;
   }

   public Integer getSingleLimitNumber(){
       return singleLimitNumber;
   }

   public void setBookNumber(Integer bookNumber){
       this.bookNumber = bookNumber;
   }

   public Integer getBookNumber(){
       return bookNumber;
   }

   public void setCompletedNumber(Integer completedNumber){
       this.completedNumber = completedNumber;
   }

   public Integer getCompletedNumber(){
       return completedNumber;
   }

   public void setPaybackDays(Integer paybackDays){
       this.paybackDays = paybackDays;
   }

   public Integer getPaybackDays(){
       return paybackDays;
   }

   public void setPaybackTime(Date paybackTime){
       this.paybackTime = paybackTime;
   }

   public Date getPaybackTime(){
       return paybackTime;
   }

   public void setIsShareBonus(Integer isShareBonus){
       this.isShareBonus = isShareBonus;
   }

   public Integer getIsShareBonus(){
       return isShareBonus;
   }

   public void setYearRate(double yearRate){
       this.yearRate = yearRate;
   }

   public double getYearRate(){
       return yearRate;
   }

   public void setInvestmentPeriod(Integer investmentPeriod){
       this.investmentPeriod = investmentPeriod;
   }

   public Integer getInvestmentPeriod(){
       return investmentPeriod;
   }

   public void setRevenueModel(Integer revenueModel){
       this.revenueModel = revenueModel;
   }

   public Integer getRevenueModel(){
       return revenueModel;
   }

   public void setShareBonusPeriod(Integer shareBonusPeriod){
       this.shareBonusPeriod = shareBonusPeriod;
   }

   public Integer getShareBonusPeriod(){
       return shareBonusPeriod;
   }

   public void setIsNeedAddress(Integer isNeedAddress){
       this.isNeedAddress = isNeedAddress;
   }

   public Integer getIsNeedAddress(){
       return isNeedAddress;
   }

   public void setIsNeedAgreement(Integer isNeedAgreement){
       this.isNeedAgreement = isNeedAgreement;
   }

   public Integer getIsNeedAgreement(){
       return isNeedAgreement;
   }

   public void setLevelStatus(Integer levelStatus){
       this.levelStatus = levelStatus;
   }

   public Integer getLevelStatus(){
       return levelStatus;
   }

   public void setLevelSort(Integer levelSort){
       this.levelSort = levelSort;
   }

   public Integer getLevelSort(){
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
