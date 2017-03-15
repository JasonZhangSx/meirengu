package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.math.BigDecimal;
import java.util.Date;
 /*
  * ItemCooperation 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class ItemCooperation  extends BaseObject {
	/** 众筹项目索引id */
	private int itemId;
	/** 合作佣金比例 */
	private BigDecimal commissionRate;
	/** 保证金比例 */
	private BigDecimal guaranteeRate;
	/** 预付分红金 */
	private int prepaidBonus;
	/** 放款方式：1、一次性；2、两次放款 */
	private int loanMode;
	/** 收款比例，默认30% */
	private int firstRatio;
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
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
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

	public void setPrepaidBonus(int prepaidBonus){
		this.prepaidBonus = prepaidBonus;
	}

	public int getPrepaidBonus(){
		return prepaidBonus;
	}

	public void setLoanMode(int loanMode){
		this.loanMode = loanMode;
	}

	public int getLoanMode(){
		return loanMode;
	}

	public void setFirstRatio(int firstRatio){
		this.firstRatio = firstRatio;
	}

	public int getFirstRatio(){
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
