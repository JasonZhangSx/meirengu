package com.meirengu.erp.model;

import com.meirengu.model.BaseObject;

/*
* Type 实体类
* Wed Mar 15 10:41:25 CST 2017 建新
*/
public class Type  extends BaseObject {
   /** 众筹类型id */
   private Integer typeId;
   /** 众筹类型名称 */
   private String typeName;
   /** 排序：数字越小权重越大 */
   private Integer typeSort;
   /** 删除标识： 1 未删除  0 删除 */
   private Integer flag;

   public void setTypeId(Integer typeId){
       this.typeId = typeId;
   }

   public Integer getTypeId(){
       return typeId;
   }

   public void setTypeName(String typeName){
       this.typeName = typeName;
   }

   public String getTypeName(){
       return typeName;
   }

   public void setTypeSort(Integer typeSort){
       this.typeSort = typeSort;
   }

   public Integer getTypeSort(){
       return typeSort;
   }

   public void setFlag(Integer flag){
       this.flag = flag;
   }

   public Integer getFlag(){
       return flag;
   }

}
