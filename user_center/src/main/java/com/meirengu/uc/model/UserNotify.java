package com.meirengu.uc.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * UserNotify 实体类
  * Wed Mar 22 21:53:51 CST 2017 建新
  */
public class UserNotify  extends BaseObject {
	/** 用户消息索引ID */
	private Integer id;
	/** 关联消息ID */
	private Integer notifyId;
	/** 消息用户ID */
	private Integer userId;
	/** 消息是否已读 */
	private Integer isRead;
	/** 创建时间 */
	private Date createTime;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setNotifyId(Integer notifyId){
		this.notifyId = notifyId;
	}

	public Integer getNotifyId(){
		return notifyId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setIsRead(Integer isRead){
		this.isRead = isRead;
	}

	public Integer getIsRead(){
		return isRead;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

}
