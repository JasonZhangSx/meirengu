package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* ItemOperateRecord 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class ItemOperateRecord  extends BaseObject {
   /** 项目操作记录索引id */
   private Integer id;
   /** 项目id */
   private Integer itemId;
   /** 操作类型：1初审；2设置合作；3复审；4发布；5下架 */
   private Integer operateType;
   /** 操作状态 */
   private Integer operateStatus;
   /** 备注信息 */
   private String operateRemark;
   /** 操作时间 */
   private Date operateTime;
   /** 操作人账号 */
   private String operateAccount;

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

   public void setOperateType(Integer operateType){
       this.operateType = operateType;
   }

   public Integer getOperateType(){
       return operateType;
   }

   public void setOperateStatus(Integer operateStatus){
       this.operateStatus = operateStatus;
   }

   public Integer getOperateStatus(){
       return operateStatus;
   }

   public void setOperateTime(Date operateTime){
       this.operateTime = operateTime;
   }

   public Date getOperateTime(){
       return operateTime;
   }

   public void setOperateAccount(String operateAccount){
       this.operateAccount = operateAccount;
   }

   public String getOperateAccount(){
       return operateAccount;
   }

   public String getOperateRemark() {
       return operateRemark;
   }

   public void setOperateRemark(String operateRemark) {
       this.operateRemark = operateRemark;
   }
}
