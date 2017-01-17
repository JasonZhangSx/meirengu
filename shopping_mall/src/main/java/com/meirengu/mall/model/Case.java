package com.meirengu.mall.model;

import java.util.Date;

/**
 * 案例实体类
 * @author 建新
 * @create 2017-01-10 17:15
 */
public class Case extends BaseEntity{

    private int id;
    /** 项目id **/
    private int itemId;
    /** 医生id **/
    private int doctorId;
    /** 医院id **/
    private int hospitalId;
    /** 项目分类id **/
    private int icId;
    /** 案例名称 **/
    private String name;
    /** 案例描述 **/
    private String desc;
    /** 对比前图片 **/
    private String beforePic;
    /** 对比后图片 **/
    private String afterPic;
    /** 案例时间 **/
    private Date time;
    /** 状态 默认1显示 0不显示 **/
    private int state;
    /** 排序 **/
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getIcId() {
        return icId;
    }

    public void setIcId(int icId) {
        this.icId = icId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBeforePic() {
        return beforePic;
    }

    public void setBeforePic(String beforePic) {
        this.beforePic = beforePic;
    }

    public String getAfterPic() {
        return afterPic;
    }

    public void setAfterPic(String afterPic) {
        this.afterPic = afterPic;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
