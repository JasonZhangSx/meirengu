package com.meirengu.news.model;

import java.util.List;
import java.util.Map;

/**
 * 分页实体
 * @param <T>
 */
public class Page<T> {

	private static final long serialVersionUID = 1L;
	private int pageNow = 1; //当前页数
	private int pageSize = 10; //每页显示记录的条数
	private int totalCount; //总的记录条数
	private int totalPageCount; //总的页数
	private int startPos = 0; //开始位置，从0开始
	private List<Map<String, Object>> list;
	
	public void init(){
		getPageNow();
		getPageSize();
		getTotalCount();
		getTotalPageCount();
		getStartPos();
		getList();
	}
	
	public int getPageNow() {
		if(totalCount == 0){
			pageNow=0;
		}
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPageCount() {
		totalPageCount =(totalCount % pageSize == 0) ? (getTotalCount() / getPageSize()) : (getTotalCount() / getPageSize()) + 1;
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getStartPos() {
		return (pageNow - 1) * pageSize;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
