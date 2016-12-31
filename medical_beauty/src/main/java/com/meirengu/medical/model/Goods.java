package com.meirengu.medical.model;

import java.math.BigDecimal;

public class Goods {
    private Integer goodsId;

    private String goodsName;

    private Integer gcId;

    private String gcName;

    private Integer brandId;

    private Integer typeId;

    private Integer storeId;

    private Boolean specOpen;

    private Integer specId;

    private String specName;

    private String goodsImage;

    private BigDecimal goodsStorePrice;

    private String goodsStorePriceInterval;

    private String goodsSerial;

    private Boolean goodsShow;

    private Integer goodsClick;

    private Boolean goodsState;

    private Boolean goodsCommend;

    private Integer goodsAddTime;

    private String goodsKeywords;

    private String goodsDescription;

    private Integer goodsStarttime;

    private Integer goodsEndtime;

    private Boolean goodsForm;

    private Integer transportId;

    private BigDecimal pyPrice;

    private BigDecimal kdPrice;

    private BigDecimal esPrice;

    private Integer cityId;

    private Integer provinceId;

    private String goodsCloseReason;

    private Boolean goodsStoreState;

    private Integer commentnum;

    private Integer salenum;

    private Integer goodsCollect;

    private Integer goodsGoldnum;

    private Boolean goodsIsztc;

    private Boolean goodsZtcstate;

    private Integer goodsZtcstartdate;

    private Integer goodsZtclastdate;

    private Boolean groupFlag;

    private BigDecimal groupPrice;

    private Boolean xianshiFlag;

    private BigDecimal xianshiDiscount;

    private Boolean goodsTransfeeCharge;

    private String goodsImageMore;

    private String goodsBody;

    private String goodsAttr;

    private String goodsSpec;

    private String goodsColImg;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGcId() {
        return gcId;
    }

    public void setGcId(Integer gcId) {
        this.gcId = gcId;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName == null ? null : gcName.trim();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Boolean getSpecOpen() {
        return specOpen;
    }

    public void setSpecOpen(Boolean specOpen) {
        this.specOpen = specOpen;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage == null ? null : goodsImage.trim();
    }

    public BigDecimal getGoodsStorePrice() {
        return goodsStorePrice;
    }

    public void setGoodsStorePrice(BigDecimal goodsStorePrice) {
        this.goodsStorePrice = goodsStorePrice;
    }

    public String getGoodsStorePriceInterval() {
        return goodsStorePriceInterval;
    }

    public void setGoodsStorePriceInterval(String goodsStorePriceInterval) {
        this.goodsStorePriceInterval = goodsStorePriceInterval == null ? null : goodsStorePriceInterval.trim();
    }

    public String getGoodsSerial() {
        return goodsSerial;
    }

    public void setGoodsSerial(String goodsSerial) {
        this.goodsSerial = goodsSerial == null ? null : goodsSerial.trim();
    }

    public Boolean getGoodsShow() {
        return goodsShow;
    }

    public void setGoodsShow(Boolean goodsShow) {
        this.goodsShow = goodsShow;
    }

    public Integer getGoodsClick() {
        return goodsClick;
    }

    public void setGoodsClick(Integer goodsClick) {
        this.goodsClick = goodsClick;
    }

    public Boolean getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(Boolean goodsState) {
        this.goodsState = goodsState;
    }

    public Boolean getGoodsCommend() {
        return goodsCommend;
    }

    public void setGoodsCommend(Boolean goodsCommend) {
        this.goodsCommend = goodsCommend;
    }

    public Integer getGoodsAddTime() {
        return goodsAddTime;
    }

    public void setGoodsAddTime(Integer goodsAddTime) {
        this.goodsAddTime = goodsAddTime;
    }

    public String getGoodsKeywords() {
        return goodsKeywords;
    }

    public void setGoodsKeywords(String goodsKeywords) {
        this.goodsKeywords = goodsKeywords == null ? null : goodsKeywords.trim();
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription == null ? null : goodsDescription.trim();
    }

    public Integer getGoodsStarttime() {
        return goodsStarttime;
    }

    public void setGoodsStarttime(Integer goodsStarttime) {
        this.goodsStarttime = goodsStarttime;
    }

    public Integer getGoodsEndtime() {
        return goodsEndtime;
    }

    public void setGoodsEndtime(Integer goodsEndtime) {
        this.goodsEndtime = goodsEndtime;
    }

    public Boolean getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(Boolean goodsForm) {
        this.goodsForm = goodsForm;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public BigDecimal getPyPrice() {
        return pyPrice;
    }

    public void setPyPrice(BigDecimal pyPrice) {
        this.pyPrice = pyPrice;
    }

    public BigDecimal getKdPrice() {
        return kdPrice;
    }

    public void setKdPrice(BigDecimal kdPrice) {
        this.kdPrice = kdPrice;
    }

    public BigDecimal getEsPrice() {
        return esPrice;
    }

    public void setEsPrice(BigDecimal esPrice) {
        this.esPrice = esPrice;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getGoodsCloseReason() {
        return goodsCloseReason;
    }

    public void setGoodsCloseReason(String goodsCloseReason) {
        this.goodsCloseReason = goodsCloseReason == null ? null : goodsCloseReason.trim();
    }

    public Boolean getGoodsStoreState() {
        return goodsStoreState;
    }

    public void setGoodsStoreState(Boolean goodsStoreState) {
        this.goodsStoreState = goodsStoreState;
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public Integer getSalenum() {
        return salenum;
    }

    public void setSalenum(Integer salenum) {
        this.salenum = salenum;
    }

    public Integer getGoodsCollect() {
        return goodsCollect;
    }

    public void setGoodsCollect(Integer goodsCollect) {
        this.goodsCollect = goodsCollect;
    }

    public Integer getGoodsGoldnum() {
        return goodsGoldnum;
    }

    public void setGoodsGoldnum(Integer goodsGoldnum) {
        this.goodsGoldnum = goodsGoldnum;
    }

    public Boolean getGoodsIsztc() {
        return goodsIsztc;
    }

    public void setGoodsIsztc(Boolean goodsIsztc) {
        this.goodsIsztc = goodsIsztc;
    }

    public Boolean getGoodsZtcstate() {
        return goodsZtcstate;
    }

    public void setGoodsZtcstate(Boolean goodsZtcstate) {
        this.goodsZtcstate = goodsZtcstate;
    }

    public Integer getGoodsZtcstartdate() {
        return goodsZtcstartdate;
    }

    public void setGoodsZtcstartdate(Integer goodsZtcstartdate) {
        this.goodsZtcstartdate = goodsZtcstartdate;
    }

    public Integer getGoodsZtclastdate() {
        return goodsZtclastdate;
    }

    public void setGoodsZtclastdate(Integer goodsZtclastdate) {
        this.goodsZtclastdate = goodsZtclastdate;
    }

    public Boolean getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Boolean groupFlag) {
        this.groupFlag = groupFlag;
    }

    public BigDecimal getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Boolean getXianshiFlag() {
        return xianshiFlag;
    }

    public void setXianshiFlag(Boolean xianshiFlag) {
        this.xianshiFlag = xianshiFlag;
    }

    public BigDecimal getXianshiDiscount() {
        return xianshiDiscount;
    }

    public void setXianshiDiscount(BigDecimal xianshiDiscount) {
        this.xianshiDiscount = xianshiDiscount;
    }

    public Boolean getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Boolean goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
    }

    public String getGoodsImageMore() {
        return goodsImageMore;
    }

    public void setGoodsImageMore(String goodsImageMore) {
        this.goodsImageMore = goodsImageMore == null ? null : goodsImageMore.trim();
    }

    public String getGoodsBody() {
        return goodsBody;
    }

    public void setGoodsBody(String goodsBody) {
        this.goodsBody = goodsBody == null ? null : goodsBody.trim();
    }

    public String getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr == null ? null : goodsAttr.trim();
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec == null ? null : goodsSpec.trim();
    }

    public String getGoodsColImg() {
        return goodsColImg;
    }

    public void setGoodsColImg(String goodsColImg) {
        this.goodsColImg = goodsColImg == null ? null : goodsColImg.trim();
    }
}