package com.meirengu.medical.model;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsCustom {
    private Integer customId;

    private Integer goodsId;

    private Integer doctorId;

    private Integer hospitalId;

    private String customName;

    private Integer customDesc;

    private BigDecimal customPrice;

    private BigDecimal goodsPrice;

    private Integer saleNum;

    private Byte goodsShow;

    private String goodsCloseReason;

    private Boolean goodsSet;

    private Date customTime;

    public Integer getCustomId() {
        return customId;
    }

    public void setCustomId(Integer customId) {
        this.customId = customId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    public Integer getCustomDesc() {
        return customDesc;
    }

    public void setCustomDesc(Integer customDesc) {
        this.customDesc = customDesc;
    }

    public BigDecimal getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(BigDecimal customPrice) {
        this.customPrice = customPrice;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Byte getGoodsShow() {
        return goodsShow;
    }

    public void setGoodsShow(Byte goodsShow) {
        this.goodsShow = goodsShow;
    }

    public String getGoodsCloseReason() {
        return goodsCloseReason;
    }

    public void setGoodsCloseReason(String goodsCloseReason) {
        this.goodsCloseReason = goodsCloseReason == null ? null : goodsCloseReason.trim();
    }

    public Boolean getGoodsSet() {
        return goodsSet;
    }

    public void setGoodsSet(Boolean goodsSet) {
        this.goodsSet = goodsSet;
    }

    public Date getCustomTime() {
        return customTime;
    }

    public void setCustomTime(Date customTime) {
        this.customTime = customTime;
    }
}