package com.meirengu.medical.model;

import java.util.Date;

public class Doctor {
    private Integer doctorId;

    private Integer hospitalId;

    private String doctorName;

    private String doctorProfile;

    private String doctorPic;

    private String doctorTitle;

    private String goodat;

    private String culturalTop;

    private String culturalExperience;

    private String certificateNo;

    private String certificatePic;

    private String professionExperience;

    private String academicAchievement;

    private Integer jobDuration;

    private Integer country;

    private Integer province;

    private Integer city;

    private Integer nativecity;

    private Boolean sex;

    private String idcard;

    private String mobile;

    private String email;

    private String qq;

    private String weixin;

    private String createby;

    private Date createdate;

    private String lasteditby;

    private Date lasteditdate;

    private Byte doctorStatus;

    private Byte doctorSort;

    private Boolean isAudit;

    private String auditAccount;

    private Date auditTime;

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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }

    public String getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(String doctorProfile) {
        this.doctorProfile = doctorProfile == null ? null : doctorProfile.trim();
    }

    public String getDoctorPic() {
        return doctorPic;
    }

    public void setDoctorPic(String doctorPic) {
        this.doctorPic = doctorPic == null ? null : doctorPic.trim();
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle == null ? null : doctorTitle.trim();
    }

    public String getGoodat() {
        return goodat;
    }

    public void setGoodat(String goodat) {
        this.goodat = goodat == null ? null : goodat.trim();
    }

    public String getCulturalTop() {
        return culturalTop;
    }

    public void setCulturalTop(String culturalTop) {
        this.culturalTop = culturalTop == null ? null : culturalTop.trim();
    }

    public String getCulturalExperience() {
        return culturalExperience;
    }

    public void setCulturalExperience(String culturalExperience) {
        this.culturalExperience = culturalExperience == null ? null : culturalExperience.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getCertificatePic() {
        return certificatePic;
    }

    public void setCertificatePic(String certificatePic) {
        this.certificatePic = certificatePic == null ? null : certificatePic.trim();
    }

    public String getProfessionExperience() {
        return professionExperience;
    }

    public void setProfessionExperience(String professionExperience) {
        this.professionExperience = professionExperience == null ? null : professionExperience.trim();
    }

    public String getAcademicAchievement() {
        return academicAchievement;
    }

    public void setAcademicAchievement(String academicAchievement) {
        this.academicAchievement = academicAchievement == null ? null : academicAchievement.trim();
    }

    public Integer getJobDuration() {
        return jobDuration;
    }

    public void setJobDuration(Integer jobDuration) {
        this.jobDuration = jobDuration;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
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

    public Integer getNativecity() {
        return nativecity;
    }

    public void setNativecity(Integer nativecity) {
        this.nativecity = nativecity;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLasteditby() {
        return lasteditby;
    }

    public void setLasteditby(String lasteditby) {
        this.lasteditby = lasteditby == null ? null : lasteditby.trim();
    }

    public Date getLasteditdate() {
        return lasteditdate;
    }

    public void setLasteditdate(Date lasteditdate) {
        this.lasteditdate = lasteditdate;
    }

    public Byte getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(Byte doctorStatus) {
        this.doctorStatus = doctorStatus;
    }

    public Byte getDoctorSort() {
        return doctorSort;
    }

    public void setDoctorSort(Byte doctorSort) {
        this.doctorSort = doctorSort;
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
}