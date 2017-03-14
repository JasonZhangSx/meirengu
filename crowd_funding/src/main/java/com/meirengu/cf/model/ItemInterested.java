package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * ItemInterested 实体类
  * Tue Mar 14 16:55:44 CST 2017 建新
  */
public class ItemInterested  extends BaseObject {
	/** 感兴趣项目索引id */
	private int id;
	/** 众筹项目id */
	private int itemId;
	/** 用户id */
	private int userId;
	/** 用户姓名 */
	private String userName;
	/** 用户手机号 */
	private String userPhone;
	/** 状态：1、感兴趣；0、取消感兴趣 */
	private byte status;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;

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

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}

	public String getUserPhone(){
		return userPhone;
	}

	public void setStatus(byte status){
		this.status = status;
	}

	public byte getStatus(){
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

}
