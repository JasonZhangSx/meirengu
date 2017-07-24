package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/*
* IpWhiteList 实体类
* Tue Jul 18 15:08:25 CST 2017 建新
*/
public class IpWhiteList extends BaseObject {
   /** ip白名单id */
   private Integer id;
   /** ip */
   private String ip;
   /** 描述 */
   private String description;
   /** 1 内部  2  合作方 */
   private Integer type;
   /** 每个ip对应的开放url， 全部为*，多个以,分割 */
   private String url;
   /** 状态  1 开启  2 关闭 */
   private Integer status;
   /**  */
   private Date createTime;
   /**  */
   private Date updateTime;

   public void setId(Integer id){
       this.id = id;
   }

   public Integer getId(){
       return id;
   }

   public void setIp(String ip){
       this.ip = ip;
   }

   public String getIp(){
       return ip;
   }

   public void setType(Integer type){
       this.type = type;
   }

   public Integer getType(){
       return type;
   }

   public void setUrl(String url){
       this.url = url;
   }

   public String getUrl(){
       return url;
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

   public String getDescription() {
       return description;
   }

   public void setDescription(String description) {
       this.description = description;
   }
}
