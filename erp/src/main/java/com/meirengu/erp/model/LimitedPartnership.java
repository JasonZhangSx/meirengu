package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* LimitedPartnership 实体类
* Mon May 08 11:33:33 CST 2017 建新
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
   /** 负责人身份证图片 */
   private String imagePrincipal;
   /** 营业执照照片 */
   private String imageBusinessLicence;
   /** 开户行 */
   private String imageBank;
   /** 医疗机构执业许可证照片 */
   private String imageProfessionalLicense;
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
