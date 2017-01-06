package com.meirengu.medical.vo;

import com.meirengu.medical.model.Doctor;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:11.
 * Dector扩展类
 */
public class DoctorVo extends Doctor {
    //起始位置
    private int pageStart;
    //结束位置
    private int pageEnd;

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    @Override
    public String toString() {
        return "DoctorVo{" +
                "pageStart='" + pageStart + '\'' +
                ", pageEnd='" + pageEnd + '\'' +
                "} " + super.toString();
    }
}
