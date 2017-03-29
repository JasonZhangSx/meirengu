package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* Partner 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class Partner  extends BaseObject {
   /** 合作方索引id */
   private int partnerId;
   /** 行业类型id */
   private int typeId;
   /** 合作方名称 */
   private String partnerName;
   /** 服务专员id */
   private int accountId;
   /** 企业名词 */
   private String enterpriseName;
   /** 证件号 */
   private int idNumber;
   /** 企业地址 */
   private String enterpriseAddress;
   /** 负责人姓名 */
   private String principalName;
   /** 负责人身份证号 */
   private String principalIdcard;
   /** 负责人联系电话 */
   private String principalTelephone;
   /** 负责人传真 */
   private int principalFax;
   /** 负责人联系地址 */
   private String principalAddress;
   /** 联系人姓名 */
   private String contactsName;
   /** 联系人身份证号 */
   private String contactsIdcard;
   /** 联系人电话 */
   private String contactsTelephone;
   /** 联系人 传真 */
   private int contactsFax;
   /** 联系人地址 */
   private String contactsAddress;
   /** 开户行名称 */
   private String bankName;
   /** 账号名 */
   private String bankAccount;
   /** 银行账号 */
   private int bankCard;
   /** 负责人身份证图片 */
   private String imagePrincipal;
   /** 营业执照照片 */
   private String imageBusinessLicence;
   /** 开户行照片 */
   private String imageBank;
   /** 医疗机构执业许可证 */
   private String imageProfessionalLicense;
   /** 删除标识： 1 未删除  0 删除 */
   private int flag;
   /** 创建时间 */
   private Date createTime;
   /** 修改时间 */
   private Date updateTime;
   /** 操作人账号 */
   private String operateAccount;

   public void setPartnerId(int partnerId){
       this.partnerId = partnerId;
   }

   public int getPartnerId(){
       return partnerId;
   }

   public void setTypeId(int typeId){
       this.typeId = typeId;
   }

   public int getTypeId(){
       return typeId;
   }

   public void setPartnerName(String partnerName){
       this.partnerName = partnerName;
   }

   public String getPartnerName(){
       return partnerName;
   }

   public void setAccountId(int accountId){
       this.accountId = accountId;
   }

   public int getAccountId(){
       return accountId;
   }

   public void setEnterpriseName(String enterpriseName){
       this.enterpriseName = enterpriseName;
   }

   public String getEnterpriseName(){
       return enterpriseName;
   }

   public void setIdNumber(int idNumber){
       this.idNumber = idNumber;
   }

   public int getIdNumber(){
       return idNumber;
   }

   public void setEnterpriseAddress(String enterpriseAddress){
       this.enterpriseAddress = enterpriseAddress;
   }

   public String getEnterpriseAddress(){
       return enterpriseAddress;
   }

   public void setPrincipalName(String principalName){
       this.principalName = principalName;
   }

   public String getPrincipalName(){
       return principalName;
   }

   public void setPrincipalIdcard(String principalIdcard){
       this.principalIdcard = principalIdcard;
   }

   public String getPrincipalIdcard(){
       return principalIdcard;
   }

   public void setPrincipalTelephone(String principalTelephone){
       this.principalTelephone = principalTelephone;
   }

   public String getPrincipalTelephone(){
       return principalTelephone;
   }

   public void setPrincipalFax(int principalFax){
       this.principalFax = principalFax;
   }

   public int getPrincipalFax(){
       return principalFax;
   }

   public void setPrincipalAddress(String principalAddress){
       this.principalAddress = principalAddress;
   }

   public String getPrincipalAddress(){
       return principalAddress;
   }

   public void setContactsName(String contactsName){
       this.contactsName = contactsName;
   }

   public String getContactsName(){
       return contactsName;
   }

   public void setContactsIdcard(String contactsIdcard){
       this.contactsIdcard = contactsIdcard;
   }

   public String getContactsIdcard(){
       return contactsIdcard;
   }

   public void setContactsTelephone(String contactsTelephone){
       this.contactsTelephone = contactsTelephone;
   }

   public String getContactsTelephone(){
       return contactsTelephone;
   }

   public void setContactsFax(int contactsFax){
       this.contactsFax = contactsFax;
   }

   public int getContactsFax(){
       return contactsFax;
   }

   public void setContactsAddress(String contactsAddress){
       this.contactsAddress = contactsAddress;
   }

   public String getContactsAddress(){
       return contactsAddress;
   }

   public void setBankName(String bankName){
       this.bankName = bankName;
   }

   public String getBankName(){
       return bankName;
   }

   public void setBankAccount(String bankAccount){
       this.bankAccount = bankAccount;
   }

   public String getBankAccount(){
       return bankAccount;
   }

   public void setBankCard(int bankCard){
       this.bankCard = bankCard;
   }

   public int getBankCard(){
       return bankCard;
   }

   public void setImagePrincipal(String imagePrincipal){
       this.imagePrincipal = imagePrincipal;
   }

   public String getImagePrincipal(){
       return imagePrincipal;
   }

   public void setImageBusinessLicence(String imageBusinessLicence){
       this.imageBusinessLicence = imageBusinessLicence;
   }

   public String getImageBusinessLicence(){
       return imageBusinessLicence;
   }

   public void setImageBank(String imageBank){
       this.imageBank = imageBank;
   }

   public String getImageBank(){
       return imageBank;
   }

   public void setImageProfessionalLicense(String imageProfessionalLicense){
       this.imageProfessionalLicense = imageProfessionalLicense;
   }

   public String getImageProfessionalLicense(){
       return imageProfessionalLicense;
   }

   public void setFlag(int flag){
       this.flag = flag;
   }

   public int getFlag(){
       return flag;
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
