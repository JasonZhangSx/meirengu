package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

/*
* Item 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class Item  extends BaseObject {
   /** 众筹项目索引id */
   Integer itemId;
   /** 项目名称(不超过30字) */
   String itemName;
   /** 项目简介 */
   String itemProfile;
   /** 众筹类型id */
   Integer typeId;
   /** 项目分类id */
   Integer classId;
   /** 目标金额 */
   BigDecimal targetAmount;
    /** 预约金额 */
   BigDecimal appointAmount;
   /** 已筹金额 */
   BigDecimal completedAmount;
   /** 预热天数 */
   Integer preheatingDays;
   /** 预热开始时间 */
   Date preheatingStartTime;
   /** 预热结束时间 */
   Date preheatingEndTime;
   /** 众筹天数 */
   Integer crowdDays;
   /** 众筹开始时间 */
   Date crowdStartTime;
   /** 众筹结束时间 */
   Date crowdEndTime;
   /** 合作方id */
   Integer partnerId;
   /** 项目区域id */
   Integer areaId;
   /** 项目头图 */
   String headerImage;
   /** 状态：1:新建中；2:初审中；3初审通过；4初审未通过；5:设置合作中；6:复审中；7复审通过；8复审未通过；9待发布；10:预热中；11认筹中；12 已完成；13已下架 */
   Integer itemStatus;
   /** 领投人名称 **/
   String leadInvestorName;
   /** 领投金额 单位：万 **/
   BigDecimal leadInvestorAmount;
   /** 领投人头像 **/
   String leadInvestorHeader;
   /** 删除标识： 1 未删除  0 删除 */
   Integer flag;
   /** 排序，数字越小权重越大 */
   Integer itemSort;
   /** 创建时间 */
   Date createTime;
   /** 修改时间 */
   Date updateTime;
   /** 操作人账号 */
   String operateAccount;
   /** 项目发起人名称 */
   String sponsorName;

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

   public void setItemProfile(String itemProfile){
       this.itemProfile = itemProfile;
   }

   public String getItemProfile(){
       return itemProfile;
   }

   public void setTypeId(Integer typeId){
       this.typeId = typeId;
   }

   public Integer getTypeId(){
       return typeId;
   }

   public void setClassId(Integer classId){
       this.classId = classId;
   }

   public Integer getClassId(){
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

   public void setPreheatingDays(Integer preheatingDays){
       this.preheatingDays = preheatingDays;
   }

   public Integer getPreheatingDays(){
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

   public void setCrowdDays(Integer crowdDays){
       this.crowdDays = crowdDays;
   }

   public Integer getCrowdDays(){
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

   public void setPartnerId(Integer partnerId){
       this.partnerId = partnerId;
   }

   public Integer getPartnerId(){
       return partnerId;
   }

   public void setAreaId(Integer areaId){
       this.areaId = areaId;
   }

   public Integer getAreaId(){
       return areaId;
   }

   public void setHeaderImage(String headerImage){
       this.headerImage = headerImage;
   }

   public String getHeaderImage(){
       return headerImage;
   }

   public void setItemStatus(Integer itemStatus){
       this.itemStatus = itemStatus;
   }

   public Integer getItemStatus(){
       return itemStatus;
   }

    public String getLeadInvestorName() {
        return leadInvestorName;
    }

    public void setLeadInvestorName(String leadInvestorName) {
        this.leadInvestorName = leadInvestorName;
    }

    public BigDecimal getLeadInvestorAmount() {
        return leadInvestorAmount;
    }

    public void setLeadInvestorAmount(BigDecimal leadInvestorAmount) {
        this.leadInvestorAmount = leadInvestorAmount;
    }

    public String getLeadInvestorHeader() {
        return leadInvestorHeader;
    }

    public void setLeadInvestorHeader(String leadInvestorHeader) {
        this.leadInvestorHeader = leadInvestorHeader;
    }

    public void setFlag(Integer flag){
       this.flag = flag;
   }

   public Integer getFlag(){
       return flag;
   }

   public void setItemSort(Integer itemSort){
       this.itemSort = itemSort;
   }

   public Integer getItemSort(){
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
