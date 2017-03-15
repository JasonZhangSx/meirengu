package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
 /*
  * Type 实体类
  * Tue Mar 14 16:55:44 CST 2017 建新
  */
public class Type  extends BaseObject {
	/** 众筹类型id */
	private int typeId;
	/** 众筹类型名称 */
	private String typeName;
	/** 排序：数字越小权重越大 */
	private byte typeSort;

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

	public void setTypeSort(byte typeSort){
		this.typeSort = typeSort;
	}

	public byte getTypeSort(){
		return typeSort;
	}

}
