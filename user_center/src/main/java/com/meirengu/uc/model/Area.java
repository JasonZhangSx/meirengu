package com.meirengu.uc.model;

/**
 * Created by huoyan403 on 3/21/2017.
 */
public class Area {

    private Integer areaId;
    private String areaName;
    private Integer areaParentId;
    private Integer areaSort;
    private Integer areaDeep;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(Integer areaParentId) {
        this.areaParentId = areaParentId;
    }

    public Integer getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(Integer areaSort) {
        this.areaSort = areaSort;
    }

    public Integer getAreaDeep() {
        return areaDeep;
    }

    public void setAreaDeep(Integer areaDeep) {
        this.areaDeep = areaDeep;
    }
}
