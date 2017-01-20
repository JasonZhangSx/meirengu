package com.meirengu.mall.model;

import com.meirengu.mall.model.BaseEntity;

/**
 * 推荐位实体
 * @author 建新
 * @create 2017-01-19 16:45
 */
public class RecommendPosition extends BaseEntity{

    private int id;
    /** 推荐位名称 **/
    private String name;
    /** 推荐位简介 **/
    private String intro;
    /** 推荐类别： 0图片1文字2幻灯3Flash **/
    private int rpClass;
    /** 推荐展示方式：0幻灯片，1多条推荐展示，2单条推荐展示 **/
    private int display;
    /** 推荐位是否启用： 0不启用，1启用**/
    private int isUse;
    /** 推荐位宽度 **/
    private int width;
    /** 推荐位高度 **/
    private int height;
    /** 拥有的推荐条数 **/
    private int recommentNum;
    /** 推荐位点击率 **/
    private int clickNum;
    /** 推荐位默认内容 **/
    private String defaultContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getRpClass() {
        return rpClass;
    }

    public void setRpClass(int rpClass) {
        this.rpClass = rpClass;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRecommentNum() {
        return recommentNum;
    }

    public void setRecommentNum(int recommentNum) {
        this.recommentNum = recommentNum;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }
}
