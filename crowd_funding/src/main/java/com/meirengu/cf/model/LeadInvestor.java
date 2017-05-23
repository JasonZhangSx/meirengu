package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * LeadInvestor 实体类
  * Mon May 08 11:33:33 CST 2017 建新
  */
public class LeadInvestor  extends BaseObject {
	/** 领投人id */
	private Integer id;
	/** 领投人名称 */
	private String investorName;
	/** 领投人类型    1公司，2个人 */
	private Integer investorType;
	/** 公司法人  */
	private String principalName;
	/** 营业执照号 */
	private String investorBusinessLicence;
	/** 领投人身份证号 */
	private String investorIdcard;
	/** 领投人地址 */
	private String investorAddress;
	/** 领投人联系电话 */
	private String investorTelphone;
	/** 领投人头像 */
	private String investorImage;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 操作账号 */
	private String operateAccount;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setInvestorName(String investorName){
		this.investorName = investorName;
	}

	public String getInvestorName(){
		return investorName;
	}

	public void setInvestorType(Integer investorType){
		this.investorType = investorType;
	}

	public Integer getInvestorType(){
		return investorType;
	}

	public void setInvestorBusinessLicence(String investorBusinessLicence){
		this.investorBusinessLicence = investorBusinessLicence;
	}

	public String getInvestorBusinessLicence(){
		return investorBusinessLicence;
	}

	public void setInvestorIdcard(String investorIdcard){
		this.investorIdcard = investorIdcard;
	}

	public String getInvestorIdcard(){
		return investorIdcard;
	}

	public void setInvestorAddress(String investorAddress){
		this.investorAddress = investorAddress;
	}

	public String getInvestorAddress(){
		return investorAddress;
	}

	public void setInvestorTelphone(String investorTelphone){
		this.investorTelphone = investorTelphone;
	}

	public String getInvestorTelphone(){
		return investorTelphone;
	}

	public void setInvestorImage(String investorImage){
		this.investorImage = investorImage;
	}

	public String getInvestorImage(){
		return investorImage;
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

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
 }
