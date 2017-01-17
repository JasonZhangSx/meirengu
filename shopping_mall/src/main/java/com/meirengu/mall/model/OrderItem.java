package com.meirengu.mall.model;

/**
 * 订单项目关联表
 *
 * @author 建新
 * @create 2017-01-14 11:20
 */
public class OrderItem extends BaseEntity{

    private int id;
    /** 订单id **/
    private int orderId;
    /** 项目id **/
    private int itemId;
    /** 项目名称 **/
    private String itemName;
    /** 项目价格 **/
    private double itemPrice;
    /** 项目数量 **/
    private int itemNum;
    /** 项目图片 **/
    private String itemImage;
    /** 医院id **/
    private int hospitalId;
    /** 医生id **/
    private int doctorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
