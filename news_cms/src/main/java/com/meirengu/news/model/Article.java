package com.meirengu.news.model;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Date;

/**
 * 文章实体
 * Created by 建新 on 2016/12/27.
 */
public class Article  extends BaseEntity {

    private int id;
    /** 文章分类ID **/
    private int acId;
    /** 文章URL **/
    private String url;
    /** 文章是否展示 0为否，1为是 **/
    private int show;
    /** 文章排序 **/
    private int sort;
    /** 文章头图 **/
    private String img;
    /** 文章标题 **/
    private String title;
    /** 文章内容 **/
    private String content;
    /** 是否为轮播图文章，0为非轮播图，1为轮播图 **/
    private int isBanner;
    /** 是否推荐 0为不推荐，1为推荐 **/
    private int isCommend;
    /** 文章发布时间 **/
    private Date time;
    /** 是否发布， 1为发布，0为未发布**/
    private int isPublish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcId() {
        return acId;
    }

    public void setAcId(int acId) {
        this.acId = acId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIsBanner() {
        return isBanner;
    }

    public void setIsBanner(int isBanner) {
        this.isBanner = isBanner;
    }

    public int getIsCommend() {
        return isCommend;
    }

    public void setIsCommend(int isCommend) {
        this.isCommend = isCommend;
    }

    public int getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
    }
}
