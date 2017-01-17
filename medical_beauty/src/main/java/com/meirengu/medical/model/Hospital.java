package com.meirengu.medical.model;

import com.meirengu.medical.util.check.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Hospital implements AnnotationValidable {
    private Integer hospitalId;
    @ValidateSize(attributeValue = "医院名称",maxSize = "100")
    private String hospitalName;
    @ValidateDigit(attributeValue = "类型")
    @ValidateSize(attributeValue = "类型",maxSize = "2")
    private String hospitalType;
    @ValidateDigit(attributeValue = "医院类型")
    private Short hospitalClass;
    //小logo
    private String hospitalSmallLogo;
    //logo
    private String hospitalLogo;
    //医院图片
    private String hospitalPic;
    //医院网站
    private String hospitalWebsite;
    //医院地址
    private String hospitalAddress;
    @ValidateDigit(attributeValue = "医院电话")
    @ValidateSize(attributeValue = "医院电话",maxSize = "11")
    private Integer hospitalTel;
    //资格证书图片
    private String certificatePic;

    @ValidateSize(attributeValue = "联系人",maxSize = "50")
    private String contacts;
    //联系电话
    private String contactsTel;

    @ValidatePattern(attributeValue = "邮箱",pattern="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    private String email;
    @ValidateDigit(attributeValue = "国家")
    private Integer county;
    @ValidateDigit(attributeValue = "省")
    private Integer province;
    @ValidateDigit(attributeValue = "市")
    private Integer city;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date setupTime;
    //新增人
    private Integer createby;
    //新增时间
    private Date createtime;
    //最后修改人
    private Integer lasteditby;
    //最后修改时间
    private Date lastedittime;
    @ValidateDigit(attributeValue = "医院人数")
    @ValidateSize(attributeValue = "医院人数",maxSize = "4")
    private Integer doctorSum;
    @ValidateDigit(attributeValue = "案例数")
    private Integer caseSum;
    @ValidateDigit(attributeValue = "医院状态")
    @ValidateSize(attributeValue = "医院状态",maxSize = "1")
    private Integer hospitalStatus;
    //排序
    private Integer hospitalSort;
    //是否审核状态
    private Boolean isAudit;
    //审核账号
    private String auditAccount;
    //审核时间
    private Date auditTime;
    //医院简介
    private String hospitalRemark;

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public Short getHospitalClass() {
        return hospitalClass;
    }

    public void setHospitalClass(Short hospitalClass) {
        this.hospitalClass = hospitalClass;
    }

    public String getHospitalSmallLogo() {
        return hospitalSmallLogo;
    }

    public void setHospitalSmallLogo(String hospitalSmallLogo) {
        this.hospitalSmallLogo = hospitalSmallLogo == null ? null : hospitalSmallLogo.trim();
    }

    public String getHospitalLogo() {
        return hospitalLogo;
    }

    public void setHospitalLogo(String hospitalLogo) {
        this.hospitalLogo = hospitalLogo == null ? null : hospitalLogo.trim();
    }

    public String getHospitalPic() {
        return hospitalPic;
    }

    public void setHospitalPic(String hospitalPic) {
        this.hospitalPic = hospitalPic == null ? null : hospitalPic.trim();
    }

    public String getHospitalWebsite() {
        return hospitalWebsite;
    }

    public void setHospitalWebsite(String hospitalWebsite) {
        this.hospitalWebsite = hospitalWebsite == null ? null : hospitalWebsite.trim();
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress == null ? null : hospitalAddress.trim();
    }

    public Integer getHospitalTel() {
        return hospitalTel;
    }

    public void setHospitalTel(Integer hospitalTel) {
        this.hospitalTel = hospitalTel;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel == null ? null : contactsTel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Date getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Date setupTime) {
        this.setupTime = setupTime;
    }

    public Integer getCreateby() {
        return createby;
    }

    public void setCreateby(Integer createby) {
        this.createby = createby;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getLasteditby() {
        return lasteditby;
    }

    public void setLasteditby(Integer lasteditby) {
        this.lasteditby = lasteditby;
    }

    public Date getLastedittime() {
        return lastedittime;
    }

    public void setLastedittime(Date lastedittime) {
        this.lastedittime = lastedittime;
    }

    public Integer getDoctorSum() {
        return doctorSum;
    }

    public void setDoctorSum(Integer doctorSum) {
        this.doctorSum = doctorSum;
    }

    public Integer getCaseSum() {
        return caseSum;
    }

    public void setCaseSum(Integer caseSum) {
        this.caseSum = caseSum;
    }

    public Integer getHospitalStatus() {
        return hospitalStatus;
    }

    public void setHospitalStatus(Integer hospitalStatus) {
        this.hospitalStatus = hospitalStatus;
    }

    public Integer getHospitalSort() {
        return hospitalSort;
    }

    public void setHospitalSort(Integer hospitalSort) {
        this.hospitalSort = hospitalSort;
    }

    public Boolean getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Boolean isAudit) {
        this.isAudit = isAudit;
    }

    public String getAuditAccount() {
        return auditAccount;
    }

    public void setAuditAccount(String auditAccount) {
        this.auditAccount = auditAccount == null ? null : auditAccount.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getCertificatePic() {
        return certificatePic;
    }

    public void setCertificatePic(String certificatePic) {
        this.certificatePic = certificatePic;
    }

    public Boolean getAudit() {
        return isAudit;
    }

    public void setAudit(Boolean audit) {
        isAudit = audit;
    }

    public String getHospitalRemark() {
        return hospitalRemark;
    }

    public void setHospitalRemark(String hospitalRemark) {
        this.hospitalRemark = hospitalRemark;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalId=" + hospitalId +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalType='" + hospitalType + '\'' +
                ", hospitalClass=" + hospitalClass +
                ", hospitalSmallLogo='" + hospitalSmallLogo + '\'' +
                ", hospitalLogo='" + hospitalLogo + '\'' +
                ", hospitalPic='" + hospitalPic + '\'' +
                ", hospitalWebsite='" + hospitalWebsite + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                ", hospitalTel=" + hospitalTel +
                ", certificatePic='" + certificatePic + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsTel='" + contactsTel + '\'' +
                ", email='" + email + '\'' +
                ", county=" + county +
                ", province=" + province +
                ", city=" + city +
                ", setupTime=" + setupTime +
                ", createby=" + createby +
                ", createtime=" + createtime +
                ", lasteditby=" + lasteditby +
                ", lastedittime=" + lastedittime +
                ", doctorSum=" + doctorSum +
                ", caseSum=" + caseSum +
                ", hospitalStatus=" + hospitalStatus +
                ", hospitalSort=" + hospitalSort +
                ", isAudit=" + isAudit +
                ", auditAccount='" + auditAccount + '\'' +
                ", auditTime=" + auditTime +
                ", hospitalRemark='" + hospitalRemark + '\'' +
                '}';
    }
}