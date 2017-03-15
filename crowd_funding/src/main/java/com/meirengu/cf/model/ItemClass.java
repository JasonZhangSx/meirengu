package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
 /*
  * ItemClass 实体类
  * Tue Mar 14 16:55:44 CST 2017 建新
  */
public class ItemClass  extends BaseObject {
	/** 众筹项目分类索引ID */
	private int classId;
	/** 众筹分类名称 */
	private String className;
	/** 父ID */
	private int classParentId;
	/** 排序，数字越小权重越大 */
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

	public void setClassParentId(int classParentId){
		this.classParentId = classParentId;
	}

	public int getClassParentId(){
		return classParentId;
	}

	public void setClassSort(byte classSort){
		this.classSort = classSort;
	}

	public byte getClassSort(){
		return classSort;
	}

}
