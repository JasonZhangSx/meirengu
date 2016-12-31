package com.meirengu.medical.vo;

import com.meirengu.medical.model.Doctor;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:11.
 * Dector扩展类
 */
public class DoctorVo extends Doctor {
    //是否分页
    private String isPaging;
    //起始位置
    private String pageStart;
    //结束位置
    private String pageEnd;

    public String getPageStart() {
        return pageStart;
    }

    public void setPageStart(String pageStart) {
        this.pageStart = pageStart;
    }

    public String getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(String pageEnd) {
        this.pageEnd = pageEnd;
    }

    public String getIsPaging() {
        return isPaging;
    }

    public void setIsPaging(String isPaging) {
        this.isPaging = isPaging;
    }

    @Override
    public String toString() {
        return "DoctorVo{" +
                "isPaging='" + isPaging + '\'' +
                ", pageStart='" + pageStart + '\'' +
                ", pageEnd='" + pageEnd + '\'' +
                "} " + super.toString();
    }
}
