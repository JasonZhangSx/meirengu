package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * ItemOperateRecord 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class ItemOperateRecord  extends BaseObject {
	/** 项目操作记录索引id */
	private int id;
	/** 项目id */
	private int itemId;
	/** 操作类型：1初审；2设置合作；3复审；4发布；5下架 */
	private int operateType;
	/** 操作状态 */
	private int operateStatus;
	/** 操作时间 */
	private Date operateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setOperateType(int operateType){
		this.operateType = operateType;
	}

	public int getOperateType(){
		return operateType;
	}

	public void setOperateStatus(int operateStatus){
		this.operateStatus = operateStatus;
	}

	public int getOperateStatus(){
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

}
