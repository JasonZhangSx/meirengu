package com.meirengu.medical.vo;

import com.meirengu.medical.model.Hospital;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 18:15.
 * 医院扩展类
 */
public class HospitalVo extends Hospital {
    //查询记录数
    private int totalRecords;
    //每页多少条数据
    private int pageSize;
    //第几页
    private int pageNo;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        if (pageNo<=1){
            return 0;
        }else {
            return pageNo*pageSize-pageSize;
        }
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String toString() {
        return "HospitalVo{" +
                "totalRecords=" + totalRecords +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                "} " + super.toString();
    }
}
