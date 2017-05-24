package com.meirengu.news.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * SignUpActivity 实体类
  * Wed May 24 14:48:02 CST 2017 建新
  */
public class SignUpActivity  extends BaseObject {
	/** 姓名 */
	private String name;
	/** 电话 */
	private String telphone;
	/** 城市 */
	private String city;
	/** 1.海伦合伙人报名邀约 */
	private Integer type;
	/** 报名时间 */
	private Date createTime;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTelphone(String telphone){
		this.telphone = telphone;
	}

	public String getTelphone(){
		return telphone;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return type;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

}
