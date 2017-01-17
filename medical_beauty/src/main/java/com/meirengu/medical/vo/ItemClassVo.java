package com.meirengu.medical.vo;

import com.meirengu.medical.model.ItemClass;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 11:22.
 * 项目分类扩展类
 */
public class ItemClassVo extends ItemClass {
    private List<ItemClassVo> list;

    public List<ItemClassVo> getList() {
        return list;
    }

    public void setList(List<ItemClassVo> list) {
        this.list = list;
    }
}
