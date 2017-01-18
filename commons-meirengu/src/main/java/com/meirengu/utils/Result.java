package com.meirengu.utils;

import java.util.List;

/**
 *封装特定的json显示结果 
 * */
@SuppressWarnings("rawtypes")
public class Result {
	private int totalCount;
	private List list;
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
