package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
 /*
  * ItemClass 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class ItemClass  extends BaseObject {
	/** 众筹项目分类索引ID */
	private int classId;
	/** 众筹分类名称 */
	private String className;
	/** 父ID */
	private int classParentId;
	/** 排序，数字越小权重越大 */
	private int classSort;
	/** 删除标识： 1 未删除  0 删除 */
	private int flag;

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

	public void setClassSort(int classSort){
		this.classSort = classSort;
	}

	public int getClassSort(){
		return classSort;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}

}
