package com.meirengu.uc.po;

/**
 * Created by huoyan403 on 3/21/2017.
 */
public class AddressNamePO {
    private Integer addressId;
    private String userAddress;
    private AddressPO addressPO;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public AddressPO getAddressPO() {
        return addressPO;
    }

    public void setAddressPO(AddressPO addressPO) {
        this.addressPO = addressPO;
    }
}
