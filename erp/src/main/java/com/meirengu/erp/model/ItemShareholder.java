package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.math.BigDecimal;

/*
* ItemShareholder 实体类
* Tue May 23 14:41:52 CST 2017 建新
*/
public class ItemShareholder  extends BaseObject {
   /**  */
   private Integer id;
   /** 项目id */
   private Integer itemId;
   /** 股东名称 */
   private String shareholderName;
   /** 股东身份证号 */
   private String shareholderIdcard;
   /** 住址 */
   private String shareholderAddress;
   /** 联系电话 */
   private String shareholderTelphone;
   /** 出资额 */
   private Integer shareholderAmount;
   /** 投前占比 */
   private BigDecimal beforeInvestRate;
   /** 投后占比 */
   private BigDecimal afterInvestRate;

   public void setId(Integer id){
       this.id = id;
   }

   public Integer getId(){
       return id;
   }

   public void setItemId(Integer itemId){
       this.itemId = itemId;
   }

   public Integer getItemId(){
       return itemId;
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

   public void setShareholderTelphone(String shareholderTelphone){
       this.shareholderTelphone = shareholderTelphone;
   }

   public String getShareholderTelphone(){
       return shareholderTelphone;
   }

   public void setShareholderAmount(Integer shareholderAmount){
       this.shareholderAmount = shareholderAmount;
   }

   public Integer getShareholderAmount(){
       return shareholderAmount;
   }

   public void setBeforeInvestRate(BigDecimal beforeInvestRate){
       this.beforeInvestRate = beforeInvestRate;
   }

   public BigDecimal getBeforeInvestRate(){
       return beforeInvestRate;
   }

   public void setAfterInvestRate(BigDecimal afterInvestRate){
       this.afterInvestRate = afterInvestRate;
   }

   public BigDecimal getAfterInvestRate(){
       return afterInvestRate;
   }

}
