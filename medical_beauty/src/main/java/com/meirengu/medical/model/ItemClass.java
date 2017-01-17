package com.meirengu.medical.model;

public class ItemClass {
    private Integer icId;

    private String icName;

    private Integer typeId;

    private String typeName;

    private Integer icParentId;

    private Integer icSort;

    private Integer icShow;

    public Integer getIcId() {
        return icId;
    }

    public void setIcId(Integer icId) {
        this.icId = icId;
    }

    public String getIcName() {
        return icName;
    }

    public void setIcName(String icName) {
        this.icName = icName == null ? null : icName.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getIcParentId() {
        return icParentId;
    }

    public void setIcParentId(Integer icParentId) {
        this.icParentId = icParentId;
    }

    public Integer getIcSort() {
        return icSort;
    }

    public void setIcSort(Integer icSort) {
        this.icSort = icSort;
    }

    public Integer getIcShow() {
        return icShow;
    }

    public void setIcShow(Integer icShow) {
        this.icShow = icShow;
    }

    @Override
    public String toString() {
        return "ItemClass{" +
                "icId=" + icId +
                ", icName='" + icName + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", icParentId=" + icParentId +
                ", icSort=" + icSort +
                ", icShow=" + icShow +
                '}';
    }
}