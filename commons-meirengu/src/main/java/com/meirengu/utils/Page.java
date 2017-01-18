package com.meirengu.utils;

import java.util.List;

//用于分页
public class Page {

    private Integer count = 0;
    private List result;
    private Integer start = 0;
    private Integer limit = 10;
    private Integer total = 0;
    private Integer mark=0;//1时为点击新闻分类加载新闻
    
    
    
    
    public Integer getMark() {
        return mark;
    }
    public void setMark(Integer mark) {
        this.mark = mark;
    }
    public Integer getTotal() {
        return (count/limit) + (count%limit==0?0:1);
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    public Integer getStart() {
        return start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public List getResult() {
        return result;
    }
    public void setResult(List result) {
        this.result = result;
    }
    
}
