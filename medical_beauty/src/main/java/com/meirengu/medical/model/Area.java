package com.meirengu.medical.model;

public class Area {
    private Integer areaId;

    private String areaName;

    private Integer areaParentId;

    private Boolean areaSort;

    private Boolean areaDeep;

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
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public Integer getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(Integer areaParentId) {
        this.areaParentId = areaParentId;
    }

    public Boolean getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(Boolean areaSort) {
        this.areaSort = areaSort;
    }

    public Boolean getAreaDeep() {
        return areaDeep;
    }

    public void setAreaDeep(Boolean areaDeep) {
        this.areaDeep = areaDeep;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", areaParentId=" + areaParentId +
                ", areaSort=" + areaSort +
                ", areaDeep=" + areaDeep +
                '}';
    }
}