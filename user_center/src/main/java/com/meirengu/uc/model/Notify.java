package com.meirengu.uc.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * Notify 实体类
  * Wed Mar 22 21:53:51 CST 2017 建新
  */
public class Notify  extends BaseObject {
	/** 消息索引ID */
	private Integer id;
	/** 消息标题 **/
	private String title;
	/** 消息内容 */
	private String content;
	/** 消息类型：1公告Announce；2提醒Remind；3信息、私信Message */
	private Integer type;
	/** 目的的ID */
	private Integer target;
	/** 目的的类型 */
	private String targetType;
	/** 提醒信息的动作类型 */
	private String action;
	/** 发送者的ID */
	private Integer sender;
	/** 创建时间 */
	private Date createTime;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return type;
	}

	public void setTarget(Integer target){
		this.target = target;
	}

	public Integer getTarget(){
		return target;
	}

	public void setTargetType(String targetType){
		this.targetType = targetType;
	}

	public String getTargetType(){
		return targetType;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return action;
	}

	public void setSender(Integer sender){
		this.sender = sender;
	}

	public Integer getSender(){
		return sender;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
 }
