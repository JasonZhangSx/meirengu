package com.meirengu.news.model;

/**
 * 文章分类实体
 * Created by 建新 on 2016/12/27.
 */

public class ArticleClass  extends BaseEntity {

    private int id;
    /** 分类标识码 **/
    private String code;
    /** 分类名称 **/
    private String name;
    /** 分类父ID **/
    private int parentId;
    /** 分类排序 **/
    private int sort;

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

}
