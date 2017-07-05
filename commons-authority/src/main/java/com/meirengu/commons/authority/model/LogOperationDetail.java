/*
 * Powered By [cifex-framework]
 * Web Site: http://www.cifex.com
 * Since 2015 - 2017
 */


package com.meirengu.commons.authority.model;

public class LogOperationDetail {

	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 详细日志ID       db_column: detail_id 
     */	
	private java.lang.Integer detailId;
    /**
     * 操作日志ID       db_column: log_operation_id 
     */	
	private java.lang.Integer logOperationId;
    /**
     * 字段名       db_column: column_name 
     */	
	private java.lang.String columnName;
    /**
     * 字段旧值       db_column: column_old_value 
     */	
	private java.lang.String columnOldValue;
    /**
     * 字段新值       db_column: column_new_value 
     */	
	private java.lang.String columnNewValue;
    /**
     * 创建时间       db_column: create_date 
     */	
	private java.util.Date createTime;
	//columns END

	public void setDetailId(java.lang.Integer value) {
		this.detailId = value;
	}
	
	public java.lang.Integer getDetailId() {
		return this.detailId;
	}
	public void setLogOperationId(java.lang.Integer value) {
		this.logOperationId = value;
	}
	
	public java.lang.Integer getLogOperationId() {
		return this.logOperationId;
	}
	public void setColumnName(java.lang.String value) {
		this.columnName = value;
	}
	
	public java.lang.String getColumnName() {
		return this.columnName;
	}
	public void setColumnOldValue(java.lang.String value) {
		this.columnOldValue = value;
	}
	
	public java.lang.String getColumnOldValue() {
		return this.columnOldValue;
	}
	public void setColumnNewValue(java.lang.String value) {
		this.columnNewValue = value;
	}
	
	public java.lang.String getColumnNewValue() {
		return this.columnNewValue;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}


}

