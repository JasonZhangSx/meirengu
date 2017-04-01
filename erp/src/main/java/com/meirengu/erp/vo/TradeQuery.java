package com.meirengu.erp.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单管理模块列表查询字段VO
 */
public class TradeQuery implements Serializable {
    

	private static final long serialVersionUID = -8836010001713826972L;

	/** 页号码,页码从1开始 */
	private Integer pageNum;
	/** 分页大小 */
	private Integer pageSize;
	/** 排序的列 */
	private String sortColumn;
	/** 升序、降序 */
	private String order;


	/** 用户电话号码 */
	private String userPhone;
	/** 项目名称 */
	private String itemName;
	/** 订单编号 */
	private String orderSn;
	/** 状态 */
	private Integer status;
	/** 状态 */
	private Integer orderState;

	public Integer getPageNum() {
		if(pageNum==null||pageNum<=0){
			pageNum=1;
		}
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		if(pageSize==null||pageSize<=0){
			pageSize=10;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}



	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getParamsStr(){
		StringBuffer sb = new StringBuffer();
		if (this.pageNum != null) {
			sb.append("page_num=" + this.pageNum);
			sb.append("&");
		}
		if (this.pageSize != null) {
			sb.append("page_size=" + this.pageSize);
			sb.append("&");
		}
		if (this.sortColumn != null) {
			sb.append("sort_by=" + this.sortColumn);
			sb.append("&");
		}
		if (this.order != null) {
			sb.append("order=" + this.order);
			sb.append("&");
		}
		if (this.userPhone != null) {
			sb.append("user_phone=" + this.userPhone);
			sb.append("&");
		}
		if (this.itemName != null) {
			sb.append("item_name=" + this.itemName);
			sb.append("&");
		}
		if (this.status != null) {
			sb.append("status=" + this.status);
			sb.append("&");
		}
		if (this.orderState != null) {
			sb.append("order_state=" + this.orderState);
			sb.append("&");
		}
		//去掉最后一个&
		if (sb.length() != 0) {
			sb.substring(0, sb.length()-2);
		}
		return sb.toString();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

