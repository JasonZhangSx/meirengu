package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * LimitedPartnership 实体类
  * Tue May 23 14:41:52 CST 2017 建新
  */
public class LimitedPartnership  extends BaseObject {
	/** 有限合伙id */
	private Integer id;
	/** 企业名称 */
	private String companyName;
	/** 成立时间 */
	private Date establishmentTime;
	/** 限制人数（默认50，不超过50） */
	private Integer limitNum;
	/** 企业证件号 */
	private String idcard;
	/** 企业地址 */
	private String companyAddress;
	/** 执行事务合伙人 */
	private String executivePartner;
	/** 关联项目名称 */
	private String itemName;
	/** 是否使用 0未使用 1使用 */
	private Integer status;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setEstablishmentTime(Date establishmentTime){
		this.establishmentTime = establishmentTime;
	}

	public Date getEstablishmentTime(){
		return establishmentTime;
	}

	public void setLimitNum(Integer limitNum){
		this.limitNum = limitNum;
	}

	public Integer getLimitNum(){
		return limitNum;
	}

	public void setIdcard(String idcard){
		this.idcard = idcard;
	}

	public String getIdcard(){
		return idcard;
	}

	public void setCompanyAddress(String companyAddress){
		this.companyAddress = companyAddress;
	}

	public String getCompanyAddress(){
		return companyAddress;
	}

	public void setExecutivePartner(String executivePartner){
		this.executivePartner = executivePartner;
	}

	public String getExecutivePartner(){
		return executivePartner;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return status;
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
