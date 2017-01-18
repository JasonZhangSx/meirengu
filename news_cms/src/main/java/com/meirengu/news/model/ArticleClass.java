package com.meirengu.news.model;

import java.util.Date;

/**
 * 文章分类实体
 * Created by 建新 on 2016/12/27.
 */

public class ArticleClass extends BaseEntity {

    private int id;
    /** 分类标识码 **/
    private String code;
    /** 分类名称 **/
    private String name;
    /** 分类父ID **/
    private int parentId;
    /** 分类排序 **/
    private int sort;
    /** 删除标识，0为删除，1为未删除 **/
    private int flag;
    /** 创建时间 **/
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
