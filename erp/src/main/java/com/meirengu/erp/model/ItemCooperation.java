package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

/*
* ItemCooperation 实体类
* Mon Apr 10 16:18:41 CST 2017 建新
*/
public class ItemCooperation  extends BaseObject {
   /** 众筹项目索引id */
   private Integer itemId;
   /** 合作佣金比例 */
   private BigDecimal commissionRate;
   /** 保证金比例 */
   private BigDecimal guaranteeRate;
   /** 预付分红金 */
   private Integer prepaidBonus;
   /** 放款方式：1、一次性；2、两次放款 */
   private Integer loanMode;
   /** 收款比例，默认30% */
   private Integer firstRatio;
   /** 发起人身份证正反面 */
   private String sponsorIdcard;
   /** 发起人征信报告 */
   private String sponsorCredit;
   /** 企业法人身份证正反面 */
   private String principalIdcard;
   /** 企业法人征信报告 */
   private String principalCredit;
   /** 营业执照 */
   private String businessLicence;
   /** 场地/土地租赁协议 */
   private String venueRentalAgreement;
   /** 店装修效果图 */
   private String storeRenderings;
   /** 公司章程 */
   private String corporateArticles;
   /** 近一年流水、资产负债表 */
   private String assetWaterLiabilities;
   /** 股东姓名 */
   private String shareholderName;
   /** 股东身份证号 */
   private String shareholderIdcard;
   /** 股东地址 */
   private String shareholderAddress;
   /** 担保人姓名 */
   private String guaranteeName;
   /** 担保人身份证号 */
   private String guaranteeIdcard;
   /** 担保人地址 */
   private String guaranteeAddress;
   /** 抵押股份 */
   private BigDecimal pledgedShares;
   /** 项目方印章 */
   private String partnerSeal;
   /** 托管协议书 */
   private String escrowAgreement;
   /** 融资服务协议 */
   private String financeService;
   /** 融资管理协议 */
   private String financeManage;
   /** 股权质押合同 */
   private String sharePledgeAgreement;
   /** 保证合同 */
   private String guarantyAgreement;
   /** 创建时间 */
   private Date createTime;
   /** 修改时间 */
   private Date updateTime;
   /** 操作人账号 */
   private String operateAccount;

   public void setItemId(Integer itemId){
       this.itemId = itemId;
   }

   public Integer getItemId(){
       return itemId;
   }

   public void setCommissionRate(BigDecimal commissionRate){
       this.commissionRate = commissionRate;
   }

   public BigDecimal getCommissionRate(){
       return commissionRate;
   }

   public void setGuaranteeRate(BigDecimal guaranteeRate){
       this.guaranteeRate = guaranteeRate;
   }

   public BigDecimal getGuaranteeRate(){
       return guaranteeRate;
   }

   public void setPrepaidBonus(Integer prepaidBonus){
       this.prepaidBonus = prepaidBonus;
   }

   public Integer getPrepaidBonus(){
       return prepaidBonus;
   }

   public void setLoanMode(Integer loanMode){
       this.loanMode = loanMode;
   }

   public Integer getLoanMode(){
       return loanMode;
   }

   public void setFirstRatio(Integer firstRatio){
       this.firstRatio = firstRatio;
   }

   public Integer getFirstRatio(){
       return firstRatio;
   }

   public void setSponsorIdcard(String sponsorIdcard){
       this.sponsorIdcard = sponsorIdcard;
   }

   public String getSponsorIdcard(){
       return sponsorIdcard;
   }

   public void setSponsorCredit(String sponsorCredit){
       this.sponsorCredit = sponsorCredit;
   }

   public String getSponsorCredit(){
       return sponsorCredit;
   }

   public void setPrincipalIdcard(String principalIdcard){
       this.principalIdcard = principalIdcard;
   }

   public String getPrincipalIdcard(){
       return principalIdcard;
   }

   public void setPrincipalCredit(String principalCredit){
       this.principalCredit = principalCredit;
   }

   public String getPrincipalCredit(){
       return principalCredit;
   }

   public void setBusinessLicence(String businessLicence){
       this.businessLicence = businessLicence;
   }

   public String getBusinessLicence(){
       return businessLicence;
   }

   public void setVenueRentalAgreement(String venueRentalAgreement){
       this.venueRentalAgreement = venueRentalAgreement;
   }

   public String getVenueRentalAgreement(){
       return venueRentalAgreement;
   }

   public void setStoreRenderings(String storeRenderings){
       this.storeRenderings = storeRenderings;
   }

   public String getStoreRenderings(){
       return storeRenderings;
   }

   public void setCorporateArticles(String corporateArticles){
       this.corporateArticles = corporateArticles;
   }

   public String getCorporateArticles(){
       return corporateArticles;
   }

   public void setAssetWaterLiabilities(String assetWaterLiabilities){
       this.assetWaterLiabilities = assetWaterLiabilities;
   }

   public String getAssetWaterLiabilities(){
       return assetWaterLiabilities;
   }

   public void setShareholderName(String shareholderName){
       this.shareholderName = shareholderName;
   }

   public String getShareholderName(){
       return shareholderName;
   }

   public void setShareholderIdcard(String shareholderIdcard){
       this.shareholderIdcard = shareholderIdcard;
   }

   public String getShareholderIdcard(){
       return shareholderIdcard;
   }

   public void setShareholderAddress(String shareholderAddress){
       this.shareholderAddress = shareholderAddress;
   }

   public String getShareholderAddress(){
       return shareholderAddress;
   }

   public void setGuaranteeName(String guaranteeName){
       this.guaranteeName = guaranteeName;
   }

   public String getGuaranteeName(){
       return guaranteeName;
   }

   public void setGuaranteeIdcard(String guaranteeIdcard){
       this.guaranteeIdcard = guaranteeIdcard;
   }

   public String getGuaranteeIdcard(){
       return guaranteeIdcard;
   }

   public void setGuaranteeAddress(String guaranteeAddress){
       this.guaranteeAddress = guaranteeAddress;
   }

   public String getGuaranteeAddress(){
       return guaranteeAddress;
   }

   public void setPledgedShares(BigDecimal pledgedShares){
       this.pledgedShares = pledgedShares;
   }

   public BigDecimal getPledgedShares(){
       return pledgedShares;
   }

   public void setPartnerSeal(String partnerSeal){
       this.partnerSeal = partnerSeal;
   }

   public String getPartnerSeal(){
       return partnerSeal;
   }

   public void setEscrowAgreement(String escrowAgreement){
       this.escrowAgreement = escrowAgreement;
   }

   public String getEscrowAgreement(){
       return escrowAgreement;
   }

   public void setFinanceService(String financeService){
       this.financeService = financeService;
   }

   public String getFinanceService(){
       return financeService;
   }

   public void setFinanceManage(String financeManage){
       this.financeManage = financeManage;
   }

   public String getFinanceManage(){
       return financeManage;
   }

   public void setSharePledgeAgreement(String sharePledgeAgreement){
       this.sharePledgeAgreement = sharePledgeAgreement;
   }

   public String getSharePledgeAgreement(){
       return sharePledgeAgreement;
   }

   public void setGuarantyAgreement(String guarantyAgreement){
       this.guarantyAgreement = guarantyAgreement;
   }

   public String getGuarantyAgreement(){
       return guarantyAgreement;
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
