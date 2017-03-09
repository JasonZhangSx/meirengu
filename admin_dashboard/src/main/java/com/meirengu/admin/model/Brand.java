package com.meirengu.admin.model;


public class Brand {
    private Integer brandId;

//    @ValidateSize(attributeValue = "品牌名称",maxSize = "100")
    private String brandName;

//    @ValidateSize(attributeValue = "类别名称",maxSize = "50")
    private String brandClass;

//    @ValidateSize(attributeValue = "品牌图片",maxSize = "100")
    private String brandPic;

//    @ValidateInt(attributeValue = "排序",max = 999)
    private Integer brandSort;

//    @ValidateInt(attributeValue = "推荐",max = 1)
    private Integer brandRecommend;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandClass() {
        return brandClass;
    }

    public void setBrandClass(String brandClass) {
        this.brandClass = brandClass == null ? null : brandClass.trim();
    }

    public String getBrandPic() {
        return brandPic;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic == null ? null : brandPic.trim();
    }

    public Integer getBrandSort() {
        return brandSort;
    }

    public void setBrandSort(Integer brandSort) {
        this.brandSort = brandSort;
    }

    public Integer getBrandRecommend() {
        return brandRecommend;
    }

    public void setBrandRecommend(Integer brandRecommend) {
        this.brandRecommend = brandRecommend;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", brandClass='" + brandClass + '\'' +
                ", brandPic='" + brandPic + '\'' +
                ", brandSort=" + brandSort +
                ", brandRecommend=" + brandRecommend +
                '}';
    }
}