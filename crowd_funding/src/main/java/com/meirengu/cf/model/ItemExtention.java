package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.math.BigDecimal;
 /*
  * ItemExtention 实体类
  * Tue May 23 14:41:52 CST 2017 建新
  */
public class ItemExtention  extends BaseObject {
	/**  */
	private Integer itemId;
	/** 出让股份 */
	private BigDecimal sellShare;
	/** 融资金额(单位万) */
	private Integer financeAmount;
	/** 进入注册资本 */
	private Integer registerCapital;
	/** 进入资本公积金 */
	private Integer captitalReserve;
	/** 融资后注册资本 */
	private Integer afterRegisterCapital;
	/** 领投人id */
	private Integer leadInvestorId;
	/** 领头金额 */
	private Integer leadInvestorAmount;
	/** 执行事务合伙人 */
	private String executivePartner;
	/** 领投原因 */
	private String leadInvestorReason;
	/** 有限合伙公司 */
	private Integer limitedPartnershipId1;
	/**  */
	private Integer limitedPartnershipId2;
	/**  */
	private Integer limitedPartnershipId3;
	/**  */
	private Integer limitedPartnershipId4;
	/** 股权收益权投资协议模板 */
	private String equityTemplate;
	/** 合伙协议模板 */
	private String partnershipTemplate;

	public void setItemId(Integer itemId){
		this.itemId = itemId;
	}

	public Integer getItemId(){
		return itemId;
	}

	public void setSellShare(BigDecimal sellShare){
		this.sellShare = sellShare;
	}

	public BigDecimal getSellShare(){
		return sellShare;
	}

	public void setFinanceAmount(Integer financeAmount){
		this.financeAmount = financeAmount;
	}

	public Integer getFinanceAmount(){
		return financeAmount;
	}

	public void setRegisterCapital(Integer registerCapital){
		this.registerCapital = registerCapital;
	}

	public Integer getRegisterCapital(){
		return registerCapital;
	}

	public void setCaptitalReserve(Integer captitalReserve){
		this.captitalReserve = captitalReserve;
	}

	public Integer getCaptitalReserve(){
		return captitalReserve;
	}

	public void setAfterRegisterCapital(Integer afterRegisterCapital){
		this.afterRegisterCapital = afterRegisterCapital;
	}

	public Integer getAfterRegisterCapital(){
		return afterRegisterCapital;
	}

	public void setLeadInvestorId(Integer leadInvestorId){
		this.leadInvestorId = leadInvestorId;
	}

	public Integer getLeadInvestorId(){
		return leadInvestorId;
	}

	public void setLeadInvestorAmount(Integer leadInvestorAmount){
		this.leadInvestorAmount = leadInvestorAmount;
	}

	public Integer getLeadInvestorAmount(){
		return leadInvestorAmount;
	}

	public void setExecutivePartner(String executivePartner){
		this.executivePartner = executivePartner;
	}

	public String getExecutivePartner(){
		return executivePartner;
	}

	public void setLeadInvestorReason(String leadInvestorReason){
		this.leadInvestorReason = leadInvestorReason;
	}

	public String getLeadInvestorReason(){
		return leadInvestorReason;
	}

	public void setLimitedPartnershipId1(Integer limitedPartnershipId1){
		this.limitedPartnershipId1 = limitedPartnershipId1;
	}

	public Integer getLimitedPartnershipId1(){
		return limitedPartnershipId1;
	}

	public void setLimitedPartnershipId2(Integer limitedPartnershipId2){
		this.limitedPartnershipId2 = limitedPartnershipId2;
	}

	public Integer getLimitedPartnershipId2(){
		return limitedPartnershipId2;
	}

	public void setLimitedPartnershipId3(Integer limitedPartnershipId3){
		this.limitedPartnershipId3 = limitedPartnershipId3;
	}

	public Integer getLimitedPartnershipId3(){
		return limitedPartnershipId3;
	}

	public void setLimitedPartnershipId4(Integer limitedPartnershipId4){
		this.limitedPartnershipId4 = limitedPartnershipId4;
	}

	public Integer getLimitedPartnershipId4(){
		return limitedPartnershipId4;
	}

	public void setEquityTemplate(String equityTemplate){
		this.equityTemplate = equityTemplate;
	}

	public String getEquityTemplate(){
		return equityTemplate;
	}

	public void setPartnershipTemplate(String partnershipTemplate){
		this.partnershipTemplate = partnershipTemplate;
	}

	public String getPartnershipTemplate(){
		return partnershipTemplate;
	}

}
