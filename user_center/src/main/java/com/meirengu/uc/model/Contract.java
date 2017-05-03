package com.meirengu.uc.model;

import java.util.Date;

/**
 * Created by huoyan403 on 4/13/2017.
 */
public class Contract {


    private Integer contractId;
    private Integer userId;
    private Integer itemId;
    private Integer levelId;
    private Integer preservationId;
    private Date preservationTime;
    private String contractFilepath;
    private String contractNo;
    private Integer orderId;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getPreservationId() {
        return preservationId;
    }

    public void setPreservationId(Integer preservationId) {
        this.preservationId = preservationId;
    }

    public Date getPreservationTime() {
        return preservationTime;
    }

    public void setPreservationTime(Date preservationTime) {
        this.preservationTime = preservationTime;
    }

    public String getContractFilepath() {
        return contractFilepath;
    }

    public void setContractFilepath(String contractFilepath) {
        this.contractFilepath = contractFilepath;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}
