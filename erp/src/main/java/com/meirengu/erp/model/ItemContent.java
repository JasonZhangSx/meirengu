package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* ItemContent 实体类
* Thu Mar 16 18:14:42 CST 2017 建新
*/
public class ItemContent  extends BaseObject {
   /** 众筹项目内容索引id */
   private Integer contentId;
   /** 内容对应众筹项目id */
   private Integer itemId;
   /** 1、项目内容   2、 融资方案 */
   private Integer contentType;
   /** 主标题 */
   private String contentTitle;
   /** 副标题 */
   private String contentSubtitle;
   /** 内容详情 */
   private String contentInfo;
   /** 排序，数字越小权重越大 */
   private Integer contentSort;
   /** 创建时间 */
   private Date createTime;
   /** 修改时间 */
   private Date updateTime;
   /** 操作人账号 */
   private String operateAccount;

   public void setContentId(Integer contentId){
       this.contentId = contentId;
   }

   public Integer getContentId(){
       return contentId;
   }

   public void setItemId(Integer itemId){
       this.itemId = itemId;
   }

   public Integer getItemId(){
       return itemId;
   }

   public void setContentType(Integer contentType){
       this.contentType = contentType;
   }

   public Integer getContentType(){
       return contentType;
   }

   public void setContentTitle(String contentTitle){
       this.contentTitle = contentTitle;
   }

   public String getContentTitle(){
       return contentTitle;
   }

   public void setContentSubtitle(String contentSubtitle){
       this.contentSubtitle = contentSubtitle;
   }

   public String getContentSubtitle(){
       return contentSubtitle;
   }

   public void setContentInfo(String contentInfo){
       this.contentInfo = contentInfo;
   }

   public String getContentInfo(){
       return contentInfo;
   }

   public void setContentSort(Integer contentSort){
       this.contentSort = contentSort;
   }

   public Integer getContentSort(){
       return contentSort;
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
