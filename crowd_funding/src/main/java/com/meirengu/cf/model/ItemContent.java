package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * ItemContent 实体类
  * Wed Mar 15 10:41:25 CST 2017 建新
  */
public class ItemContent  extends BaseObject {
	/** 众筹项目内容索引id */
	private int contentId;
	/** 内容对应众筹项目id */
	private int itemId;
	/** 1、项目内容   2、 融资方案 */
	private int contentType;
	/** 主标题 */
	private String contentTitle;
	/** 副标题 */
	private String contentSubtitle;
	/** 内容详情 */
	private int contentInfo;
	/** 排序，数字越小权重越大 */
	private int contentSort;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 操作人账号 */
	private String operateAccount;

	public void setContentId(int contentId){
		this.contentId = contentId;
	}

	public int getContentId(){
		return contentId;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setContentType(int contentType){
		this.contentType = contentType;
	}

	public int getContentType(){
		return contentType;
	}

	public void setContentTitle(String contentTitle){
		this.contentTitle = contentTitle;
	}

	public String getContentTitle(){
		return contentTitle;
	}

	public void setContentSubtitle(String contentSubtitle){
		this.contentSubtitle = contentSubtitle;
	}

	public String getContentSubtitle(){
		return contentSubtitle;
	}

	public void setContentInfo(int contentInfo){
		this.contentInfo = contentInfo;
	}

	public int getContentInfo(){
		return contentInfo;
	}

	public void setContentSort(int contentSort){
		this.contentSort = contentSort;
	}

	public int getContentSort(){
		return contentSort;
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

	public void setOperateAccount(String operateAccount){
		this.operateAccount = operateAccount;
	}

	public String getOperateAccount(){
		return operateAccount;
	}

}
