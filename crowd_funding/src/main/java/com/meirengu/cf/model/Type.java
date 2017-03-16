package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
 /*
  * Type 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class Type  extends BaseObject {
	/** 众筹类型id */
	private int typeId;
	/** 众筹类型名称 */
	private String typeName;
	/** 排序：数字越小权重越大 */
	private int typeSort;
	/** 删除标识： 1 未删除  0 删除 */
	private int flag;

	public void setTypeId(int typeId){
		this.typeId = typeId;
	}

	public int getTypeId(){
		return typeId;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName(){
		return typeName;
	}

	public void setTypeSort(int typeSort){
		this.typeSort = typeSort;
	}

	public int getTypeSort(){
		return typeSort;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}

}
