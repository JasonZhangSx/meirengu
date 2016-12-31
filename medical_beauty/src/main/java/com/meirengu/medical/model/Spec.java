package com.meirengu.medical.model;

public class Spec {
    private Integer spId;

    private String spName;

    private String spFormat;

    private Boolean spSort;

    private String spValue;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public String getSpFormat() {
        return spFormat;
    }

    public void setSpFormat(String spFormat) {
        this.spFormat = spFormat == null ? null : spFormat.trim();
    }

    public Boolean getSpSort() {
        return spSort;
    }

    public void setSpSort(Boolean spSort) {
        this.spSort = spSort;
    }

    public String getSpValue() {
        return spValue;
    }

    public void setSpValue(String spValue) {
        this.spValue = spValue == null ? null : spValue.trim();
    }
}