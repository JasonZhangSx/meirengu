package com.meirengu.pay.vo;

import com.meirengu.pay.model.PaymentRecord;
import com.meirengu.pay.utils.check.ValidateDigit;
import com.meirengu.pay.utils.check.ValidateNotNull;
import com.meirengu.pay.utils.check.ValidatePattern;
import com.meirengu.pay.utils.check.ValidateSize;

/**
 * 流水扩展
 * Author: haoyang.Yu
 * Create Date: 2017/3/25 15:38.
 */
public class PaymentRecordVo extends PaymentRecord {
    @ValidateNotNull(attributeValue = "姓名")
    private String realName;
//    @ValidateNotNull(attributeValue = "身份证号码")
    @ValidatePattern(attributeValue = "身份证号码",pattern="(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])")
    private String identityNumber;
//    @ValidateNotNull(attributeValue = "银行卡号")
//    @ValidatePattern(attributeValue = "银行卡号",pattern="^\\d{19}$")

    @ValidateDigit()
    @ValidateSize(attributeValue = "银行卡号",minSize = "15",maxSize = "19")
    private String bankNo;
    @ValidateNotNull(attributeValue = "手机号")
    @ValidatePattern(attributeValue = "手机号",pattern="^[1][3,4,5,7,8][0-9]{9}$")
    private String mobile;

    private String startDate;

    private String endDate;
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "PaymentRecordVo{" +
                "realName='" + realName + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", mobile='" + mobile + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                "} " + super.toString();
    }
}
