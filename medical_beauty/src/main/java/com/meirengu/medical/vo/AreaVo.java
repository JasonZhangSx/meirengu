package com.meirengu.medical.vo;

import com.meirengu.medical.model.Area;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 17:35.
 * 地区表扩展类
 */
public class AreaVo extends Area {
    private List<AreaVo> list;

    public List<AreaVo> getList() {
        return list;
    }

    public void setList(List<AreaVo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AreaVo{" +
                "list=" + list +
                "} " + super.toString();
    }
}
