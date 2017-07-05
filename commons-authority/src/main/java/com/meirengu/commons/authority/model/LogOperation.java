package com.meirengu.commons.authority.model;
public class LogOperation {

	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 操作日志ID       db_column: log_id 
     */	
	private java.lang.Integer logId;
    /**
     * 业务模块名称       db_column: business_name 
     */	
	private java.lang.String businessName;
    /**
     * 操作类型       db_column: operation_type 
     */	
	private java.lang.Integer operationType;
    /**
     * 业务主键       db_column: primary_key 
     */	
	private java.lang.String primaryKey;
    /**
     * 操作员ID       db_column: user_id 
     */	
	private java.lang.Integer userId;
    /**
     * 操作员账号       db_column: user_name 
     */	
	private java.lang.String userName;
    /**
     * 创建时间       db_column: create_time 
     */	
	private java.util.Date createTime;
	//columns END

	public void setLogId(java.lang.Integer value) {
		this.logId = value;
	}
	
	public java.lang.Integer getLogId() {
		return this.logId;
	}
	public void setBusinessName(java.lang.String value) {
		this.businessName = value;
	}
	
	public java.lang.String getBusinessName() {
		return this.businessName;
	}
	public void setOperationType(java.lang.Integer value) {
		this.operationType = value;
	}
	
	public java.lang.Integer getOperationType() {
		return this.operationType;
	}
	public void setPrimaryKey(java.lang.String value) {
		this.primaryKey = value;
	}
	
	public java.lang.String getPrimaryKey() {
		return this.primaryKey;
	}
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}


}

