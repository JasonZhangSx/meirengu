package com.meirengu.mall.model;

import java.util.Date;

/**
 * 推荐位实体类
 * @author 建新
 * @create 2017-01-10 17:15
 */
public class Recommend extends BaseEntity{

    private int id;
    /** 推荐位id **/
    private int rpId;
    /** 推荐内容描述 **/
    private String title;
    /** 推荐内容id **/
    private int contentId;
    /** 推荐类型，0商品1专场2媒体3品牌 **/
    private int type;
    /** 创建时间 **/
    private Date createTime;
    /** 幻灯片排序 **/
    private int slideSort;
    /** 推荐点击率 **/
    private int clickNum;
    /** 推荐内容是否启用：0不启用，1启用 **/
    private int isUse;

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRpId() {
        return rpId;
    }

    public void setRpId(int rpId) {
        this.rpId = rpId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSlideSort() {
        return slideSort;
    }

    public void setSlideSort(int slideSort) {
        this.slideSort = slideSort;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }
}
