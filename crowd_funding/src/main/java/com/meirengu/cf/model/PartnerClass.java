package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
 /*
  * PartnerClass 实体类
  * Tue Mar 14 16:55:44 CST 2017 建新
  */
public class PartnerClass  extends BaseObject {
	/** 行业分类索引id */
	private int classId;
	/** 行业类型名称 */
	private String className;
	/** 排序：数字越小权重越大 */
	private byte classSort;

	public void setClassId(int classId){
		this.classId = classId;
	}

	public int getClassId(){
		return classId;
	}

	public void setClassName(String className){
		this.className = className;
	}

	public String getClassName(){
		return className;
	}

	public void setClassSort(byte classSort){
		this.classSort = classSort;
	}

	public byte getClassSort(){
		return classSort;
	}

}
