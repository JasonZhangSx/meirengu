package com.meirengu.medical.model;

import java.util.Date;

public class Hospital {
    private Integer hospitalId;

    private String hospitalName;

    private Byte hospitalType;

    private Short hospitalClass;

    private String hospitalSmallLogo;

    private String hospitalLogo;

    private String hospitalPic;

    private String hospitalWebsite;

    private String hospitalAddress;

    private Integer hospitalTel;

    private String certificatePic;

    private String contacts;

    private String contactsTel;

    private String email;

    private Integer county;

    private Integer province;

    private Integer city;

    private Date setupTime;

    private Integer createby;

    private Date createtime;

    private Integer lasteditby;

    private Date lastedittime;

    private Integer doctorSum;

    private Integer caseSum;

    private Integer hospitalStatus;

    private Integer hospitalSort;

    private Boolean isAudit;

    private String auditAccount;

    private Date auditTime;

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

    public Byte getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(Byte hospitalType) {
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

    public String getHospitalRemark() {
        return hospitalRemark;
    }

    public void setHospitalRemark(String hospitalRemark) {
        this.hospitalRemark = hospitalRemark == null ? null : hospitalRemark.trim();
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

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalId=" + hospitalId +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalType=" + hospitalType +
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