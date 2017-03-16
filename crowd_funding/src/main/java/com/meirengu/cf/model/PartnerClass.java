package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * PartnerClass 实体类
  * Wed Mar 15 15:13:35 CST 2017 建新
  */
public class PartnerClass  extends BaseObject {
	/** 行业分类索引id */
	private int classId;
	/** 行业类型名称 */
	private String className;
	/** 分类描述 */
	private String classDescription;
	/** 排序：数字越小权重越大 */
	private int classSort;
	/** 关联合作方数 */
	private int partnerNum;
	/** 删除标识： 1 未删除  0 删除 */
	private int flag;
	/** 创建时间 */
	private Date createTime;

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

	public void setClassDescription(String classDescription){
		this.classDescription = classDescription;
	}

	public String getClassDescription(){
		return classDescription;
	}

	public void setClassSort(int classSort){
		this.classSort = classSort;
	}

	public int getClassSort(){
		return classSort;
	}

	public void setPartnerNum(int partnerNum){
		this.partnerNum = partnerNum;
	}

	public int getPartnerNum(){
		return partnerNum;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

}
