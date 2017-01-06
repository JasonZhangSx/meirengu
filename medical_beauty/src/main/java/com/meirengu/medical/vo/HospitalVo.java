package com.meirengu.medical.vo;

import com.meirengu.medical.model.Hospital;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 18:15.
 * 医院扩展类
 */
public class HospitalVo extends Hospital {
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
        return "HospitalVo{" +
                "pageStart=" + pageStart +
                ", pageEnd=" + pageEnd +
                "} " + super.toString();
    }
}
