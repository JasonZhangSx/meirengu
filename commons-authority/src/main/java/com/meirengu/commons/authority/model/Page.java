package com.meirengu.commons.authority.model;

import java.util.List;

/**
 * 分页实体(后期优化用抽取通用)
 *
 * @author zhangjianxin
 */
public class Page<T> extends BaseObject {

    private static final long serialVersionUID = 1L;
    private int pageNow = 1; //当前页数
    private int pageSize = 10; //每页显示记录的条数
    private int totalCount; //总的记录条数
    private int totalPageCount; //总的页数
    private int startPos = 0; //开始位置，从0开始
    private List<T> list;

    public void init() {
        getPageNow();
        getPageSize();
        getTotalCount();
        getTotalPageCount();
        getStartPos();
        getList();
    }

    public int getPageNow() {
        if (totalCount == 0) {
            pageNow = 0;
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
        totalPageCount = (totalCount % pageSize == 0) ? (getTotalCount() / getPageSize()) : (getTotalCount() /
                getPageSize()) + 1;
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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
