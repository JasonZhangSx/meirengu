package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* Partner 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class Partner extends BaseObject {
   /** 合作方索引id */
   private Integer partnerId;
   /** 行业类型id */
   private Integer typeId;
   /** 合作方名称 */
   private String partnerName;
   /** 合作方标签 */
   private String partnerLabel;
   /** 合作方联系方式 */
   private String partnerTelphone;
   /** 合作方图标 */
   private String partnerImg;
   /** 公司成立日 **/
   private Date partnerCreateDay;
   /** 公司注册资金 **/
   private Integer partnerRegistCapital;
   /** 公司估值 **/
   private Integer partnerValuation;
   /** 服务专员id */
   private Integer accountId;
   /** 企业名词 */
   private String enterpriseName;
   /** 证件号 */
   private String idNumber;
   /** 企业地址 */
   private String enterpriseAddress;
   /** 负责人姓名 */
   private String principalName;
   /** 负责人身份证号 */
   private String principalIdcard;
   /** 负责人联系电话 */
   private String principalTelephone;
   /** 负责人传真 */
   private String principalFax;
   /** 负责人联系地址 */
   private String principalAddress;
   /** 联系人姓名 */
   private String contactsName;
   /** 联系人身份证号 */
   private String contactsIdcard;
   /** 联系人电话 */
   private String contactsTelephone;
   /** 联系人 传真 */
   private String contactsFax;
   /** 联系人地址 */
   private String contactsAddress;
   /** 开户行名称 */
   private String bankName;
   /** 账号名 */
   private String bankAccount;
   /** 银行账号 */
   private String bankCard;
   /** 负责人身份证图片 */
   private String imagePrincipal;
   /** 营业执照照片 */
   private String imageBusinessLicence;
   /** 开户行照片 */
   private String imageBank;
   /** 医疗机构执业许可证 */
   private String imageProfessionalLicense;
   /** 删除标识： 1 未删除  0 删除 */
   private Integer flag;
   /** 创建时间 */
   private Date createTime;
   /** 修改时间 */
   private Date updateTime;
   /** 操作人账号 */
   private String operateAccount;

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerLabel() {
        return partnerLabel;
    }

    public void setPartnerLabel(String partnerLabel) {
        this.partnerLabel = partnerLabel;
    }

    public String getPartnerTelphone() {
        return partnerTelphone;
    }

    public void setPartnerTelphone(String partnerTelphone) {
        this.partnerTelphone = partnerTelphone;
    }

    public String getPartnerImg() {
        return partnerImg;
    }

    public void setPartnerImg(String partnerImg) {
        this.partnerImg = partnerImg;
    }

    public Date getPartnerCreateDay() {
        return partnerCreateDay;
    }

    public void setPartnerCreateDay(Date partnerCreateDay) {
        this.partnerCreateDay = partnerCreateDay;
    }

    public Integer getPartnerRegistCapital() {
        return partnerRegistCapital;
    }

    public void setPartnerRegistCapital(Integer partnerRegistCapital) {
        this.partnerRegistCapital = partnerRegistCapital;
    }

    public Integer getPartnerValuation() {
        return partnerValuation;
    }

    public void setPartnerValuation(Integer partnerValuation) {
        this.partnerValuation = partnerValuation;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalIdcard() {
        return principalIdcard;
    }

    public void setPrincipalIdcard(String principalIdcard) {
        this.principalIdcard = principalIdcard;
    }

    public String getPrincipalTelephone() {
        return principalTelephone;
    }

    public void setPrincipalTelephone(String principalTelephone) {
        this.principalTelephone = principalTelephone;
    }

    public String getPrincipalFax() {
        return principalFax;
    }

    public void setPrincipalFax(String principalFax) {
        this.principalFax = principalFax;
    }

    public String getPrincipalAddress() {
        return principalAddress;
    }

    public void setPrincipalAddress(String principalAddress) {
        this.principalAddress = principalAddress;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsIdcard() {
        return contactsIdcard;
    }

    public void setContactsIdcard(String contactsIdcard) {
        this.contactsIdcard = contactsIdcard;
    }

    public String getContactsTelephone() {
        return contactsTelephone;
    }

    public void setContactsTelephone(String contactsTelephone) {
        this.contactsTelephone = contactsTelephone;
    }

    public String getContactsFax() {
        return contactsFax;
    }

    public void setContactsFax(String contactsFax) {
        this.contactsFax = contactsFax;
    }

    public String getContactsAddress() {
        return contactsAddress;
    }

    public void setContactsAddress(String contactsAddress) {
        this.contactsAddress = contactsAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getImagePrincipal() {
        return imagePrincipal;
    }

    public void setImagePrincipal(String imagePrincipal) {
        this.imagePrincipal = imagePrincipal;
    }

    public String getImageBusinessLicence() {
        return imageBusinessLicence;
    }

    public void setImageBusinessLicence(String imageBusinessLicence) {
        this.imageBusinessLicence = imageBusinessLicence;
    }

    public String getImageBank() {
        return imageBank;
    }

    public void setImageBank(String imageBank) {
        this.imageBank = imageBank;
    }

    public String getImageProfessionalLicense() {
        return imageProfessionalLicense;
    }

    public void setImageProfessionalLicense(String imageProfessionalLicense) {
        this.imageProfessionalLicense = imageProfessionalLicense;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount;
    }
}
