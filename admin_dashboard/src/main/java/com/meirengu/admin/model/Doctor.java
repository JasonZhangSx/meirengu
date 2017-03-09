package com.meirengu.admin.model;


import java.util.Date;

public class Doctor{
    private Integer doctorId;
//    @ValidateDigit(attributeValue = "所属医院")
//    @ValidateSize(attributeValue = "所属医院",maxSize = "10")
    private Integer hospitalId;
//    @ValidateSize(attributeValue = "医生名称",minSize = "2",maxSize = "100")
    private String doctorName;
    //医生简介
    private String doctorProfile;
    //医生详情
    private String doctorDetail;
    //医生照片
    private String doctorPic;
    //医生职称
    private String doctorTitle;
//    @ValidateSize(attributeValue = "副标题",maxSize = "20")
    private String doctorViceTitle;
    //医生标签
    private String doctorLabel;
    //擅长医术
    private String goodat;
    //最高学历
    private String culturalTop;
    //学习经历
    private String culturalExperience;
//    @ValidateSize(attributeValue = "医师资格证书号",maxSize = "20")
    private String certificateNo;
    //医生执业证书快照
    private String certificatePic;
    //职业经历
    private String professionExperience;
    //学术成就
    private String academicAchievement;
//    @ValidateDigit(attributeValue = "从医年限")
//    @ValidateSize(attributeValue = "从医年限",maxSize = "11")
    private Integer jobDuration;
//    @ValidateDigit(attributeValue = "国家")
//    @ValidateSize(attributeValue = "国家",maxSize = "11")
    private Integer country;
//    @ValidateDigit(attributeValue = "省份")
//    @ValidateSize(attributeValue = "省份",maxSize = "11")
    private Integer province;
//    @ValidateDigit(attributeValue = "城市")
//    @ValidateSize(attributeValue = "城市",maxSize = "11")
    private Integer city;
//    @ValidateDigit(attributeValue = "籍贯")
//    @ValidateSize(attributeValue = "籍贯",maxSize = "11")
    private Integer nativecity;
//    @ValidateDigit(attributeValue = "性别")
//    @ValidateSize(attributeValue = "性别",maxSize = "1")
    private Boolean sex;
//    @ValidatePattern(attributeValue = "身份证号",pattern="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$")
    private String idcard;
//    @ValidateDigit(attributeValue = "手机号")
//    @ValidateSize(attributeValue = "手机号",maxSize = "11")
    private String mobile;
//    @ValidatePattern(attributeValue = "邮箱",pattern="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    private String email;
//    @ValidateDigit(attributeValue = "qq")
//    @ValidateSize(attributeValue = "qq",minSize = "5",maxSize = "10")
    private String qq;
//    @ValidateSize(attributeValue = "微信",minSize = "2",maxSize = "16")
    private String weixin;
    //新增人
    private String createby;
    //新增时间
    private Date createdate;
    //最后修改人
    private String lasteditby;
    //最后修改时间
    private Date lasteditdate;
    //状态
    private Byte doctorStatus;
    //排序
    private Byte doctorSort;
    //审核状态
    private Boolean isAudit;
    //审核 账号
    private String auditAccount;
    //审核时间
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

    public String getDoctorViceTitle() {
        return doctorViceTitle;
    }

    public void setDoctorViceTitle(String doctorViceTitle) {
        this.doctorViceTitle = doctorViceTitle;
    }

    public String getDoctorLabel() {
        return doctorLabel;
    }

    public void setDoctorLabel(String doctorLabel) {
        this.doctorLabel = doctorLabel;
    }

    public Boolean getAudit() {
        return isAudit;
    }

    public void setAudit(Boolean audit) {
        isAudit = audit;
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

    public String getDoctorDetail() {
        return doctorDetail;
    }

    public void setDoctorDetail(String doctorDetail) {
        this.doctorDetail = doctorDetail;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", hospitalId=" + hospitalId +
                ", doctorName='" + doctorName + '\'' +
                ", doctorProfile='" + doctorProfile + '\'' +
                ", doctorDetail='" + doctorDetail + '\'' +
                ", doctorPic='" + doctorPic + '\'' +
                ", doctorTitle='" + doctorTitle + '\'' +
                ", doctorViceTitle='" + doctorViceTitle + '\'' +
                ", doctorLabel='" + doctorLabel + '\'' +
                ", goodat='" + goodat + '\'' +
                ", culturalTop='" + culturalTop + '\'' +
                ", culturalExperience='" + culturalExperience + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", certificatePic='" + certificatePic + '\'' +
                ", professionExperience='" + professionExperience + '\'' +
                ", academicAchievement='" + academicAchievement + '\'' +
                ", jobDuration=" + jobDuration +
                ", country=" + country +
                ", province=" + province +
                ", city=" + city +
                ", nativecity=" + nativecity +
                ", sex=" + sex +
                ", idcard='" + idcard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", createby='" + createby + '\'' +
                ", createdate=" + createdate +
                ", lasteditby='" + lasteditby + '\'' +
                ", lasteditdate=" + lasteditdate +
                ", doctorStatus=" + doctorStatus +
                ", doctorSort=" + doctorSort +
                ", isAudit=" + isAudit +
                ", auditAccount='" + auditAccount + '\'' +
                ", auditTime=" + auditTime +
                '}';
    }
}