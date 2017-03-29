package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* ItemInterested 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class ItemInterested  extends BaseObject {
   /** 感兴趣项目索引id */
   private Integer id;
   /** 众筹项目id */
   private Integer itemId;
   /** 用户id */
   private Integer userId;
   /** 用户姓名 */
   private String userName;
   /** 用户手机号 */
   private String userPhone;
   /** 状态：1、感兴趣；0、取消感兴趣 */
   private Integer status;
   /** 创建时间 */
   private Date createTime;
   /** 修改时间 */
   private Date updateTime;

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

   public void setUserId(Integer userId){
       this.userId = userId;
   }

   public Integer getUserId(){
       return userId;
   }

   public void setUserName(String userName){
       this.userName = userName;
   }

   public String getUserName(){
       return userName;
   }

   public void setUserPhone(String userPhone){
       this.userPhone = userPhone;
   }

   public String getUserPhone(){
       return userPhone;
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

}
