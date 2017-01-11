package com.meirengu.mall.model;

/**
 * 推荐位实体类
 * @author 建新
 * @create 2017-01-10 17:15
 */
public class Recommend extends BaseEntity{

    private int id;
    /** 名称 **/
    private String name;
    /** 推荐标识码 **/
    private String code;
    /** 推荐描述 **/
    private String desc;
    /** 配置信息 **/
    private String config;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
