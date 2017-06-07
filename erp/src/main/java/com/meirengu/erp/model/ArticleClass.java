package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* ArticleClass 实体类
* Tue Jun 06 10:57:35 CST 2017 建新
*/
public class ArticleClass  extends BaseObject {
   /** 索引ID */
   private Integer acId;
   /** 分类标识码 */
   private String acCode;
   /** 分类名称 */
   private String acName;
   /** 父ID */
   private Integer acParentId;
   /** 排序 */
   private Integer acSort;
   /** 删除标识，0为删除，1为未删除 */
   private Integer flag;
   /** 创建时间 */
   private Date createTime;

   public void setAcId(Integer acId){
       this.acId = acId;
   }

   public Integer getAcId(){
       return acId;
   }

   public void setAcCode(String acCode){
       this.acCode = acCode;
   }

   public String getAcCode(){
       return acCode;
   }

   public void setAcName(String acName){
       this.acName = acName;
   }

   public String getAcName(){
       return acName;
   }

   public void setAcParentId(Integer acParentId){
       this.acParentId = acParentId;
   }

   public Integer getAcParentId(){
       return acParentId;
   }

   public void setAcSort(Integer acSort){
       this.acSort = acSort;
   }

   public Integer getAcSort(){
       return acSort;
   }

   public void setFlag(Integer flag){
       this.flag = flag;
   }

   public Integer getFlag(){
       return flag;
   }

   public void setCreateTime(Date createTime){
       this.createTime = createTime;
   }

   public Date getCreateTime(){
       return createTime;
   }

}
