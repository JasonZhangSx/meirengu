package com.meirengu.medical.vo;

import com.meirengu.medical.model.Brand;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/10 14:28.
 * 品牌表扩展类
 */
public class BrandVo extends Brand {
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
        return "BrandVo{" +
                "pageStart=" + pageStart +
                ", pageEnd=" + pageEnd +
                "} " + super.toString();
    }
}
