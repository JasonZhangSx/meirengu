package com.meirengu.erp.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 订单管理模块列表查询字段VO
 */
public class QueryVo implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(QueryVo.class);
    
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
	/** 候补预约处理状态 */
	private Integer status;
	/** 订单状态 */
	private Integer orderState;
	/** 订单状态集合 */
	private List<Integer> orderStateList;
	/** 退款订单状态 */
	private Integer refundState;
	/** 退款订单编号 */
	private String refundSn;

	public QueryVo(int pageNum, int pageSize, String sortColumn, String order){
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sortColumn = sortColumn;
		this.order = order;
	}

	public QueryVo(DataTablesInput input){
		// 分页字段
		int start = input.getStart();
		int length = input.getLength();
		int pageNum = start / length + 1;

		this.pageNum = pageNum;
		this.pageSize = length;

		// 查询条件
		List<Column> columnList = input.getColumns();
		for (Column parameter : columnList) {
			String parameterName = parameter.getName();
			String parameterSearchValue = parameter.getSearch().getValue();
			if (StringUtils.isEmpty(parameterName) || StringUtils.isEmpty(parameterSearchValue)) {
				continue;
			}
			switch (parameterName) {
				case "userPhone":
					this.userPhone = parameterSearchValue;
					break;
				case "itemName":
					this.itemName = parameterSearchValue;
					break;
				case "orderSn":
					this.orderSn = parameterSearchValue;
					break;
				case "status":
					this.status = Integer.parseInt(parameterSearchValue);
					break;
				case "orderState":
					this.orderState = Integer.parseInt(parameterSearchValue);
					break;
				case "refundState":
					this.refundState = Integer.parseInt(parameterSearchValue);
					break;
				case "refundSn":
					this.refundSn = parameterSearchValue;
					break;
				default:
					logger.info("tradeQueryVo中没有: {} 字段", parameterName);
					break;
			}
		}
	}

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
		if(StringUtils.isEmpty(sortColumn)){
			sortColumn="create_time";
		}
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}



	public String getOrder() {
		if(StringUtils.isEmpty(order)){
			order="desc";
		}
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

	public List<Integer> getOrderStateList() {
		return orderStateList;
	}

	public void setOrderStateList(List<Integer> orderStateList) {
		this.orderStateList = orderStateList;
	}

	public Integer getRefundState() {
		return refundState;
	}

	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}

	public String getRefundSn() {
		return refundSn;
	}

	public void setRefundSn(String refundSn) {
		this.refundSn = refundSn;
	}

	public String getParamsStr() throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		String paramsStr = sb.toString();
		if (this.getPageNum() != null) {
			sb.append("page_num=" + this.getPageNum());
			sb.append("&");
		}
		if (this.getPageSize() != null) {
			sb.append("page_size=" + this.getPageSize());
			sb.append("&");
		}
		if (StringUtils.isNotBlank(this.getSortColumn())) {
			sb.append("sort_by=" + this.getSortColumn());
			sb.append("&");
		}
		if (StringUtils.isNotBlank(this.getOrder())) {
			sb.append("order=" + this.getOrder());
			sb.append("&");
		}
		if (StringUtils.isNotBlank(this.getOrderSn())) {
			sb.append("order_sn=" + this.getOrderSn());
			sb.append("&");
		}
		if (StringUtils.isNotBlank(this.getUserPhone())) {
			sb.append("user_phone=" + this.getUserPhone());
			sb.append("&");
		}
		if (StringUtils.isNotBlank(this.getItemName())) {
			sb.append("item_name=" + URLEncoder.encode(this.getItemName(), "UTF-8"));
			sb.append("&");
		}
		if (this.getStatus() != null) {
			sb.append("status=" + this.getStatus());
			sb.append("&");
		}
		if (this.getOrderState() != null) {
			sb.append("order_state=" + this.getOrderState());
			sb.append("&");
		}
		if (this.getOrderStateList() != null) {
			for (Integer orderStatus : this.getOrderStateList()) {
				sb.append("order_state_list=" + orderStatus);
				sb.append("&");
			}
		}
		if (this.getRefundState() != null) {
			sb.append("refund_state=" + this.getRefundState());
			sb.append("&");
		}
		if (StringUtils.isNotBlank(this.getRefundSn())) {
			sb.append("refund_sn=" + this.getRefundSn());
			sb.append("&");
		}
		//去掉最后一个&
		if (sb.length() != 0) {
			paramsStr = sb.substring(0, sb.length()-1);
		}
		return paramsStr;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

