package com.meirengu.medical.service;

import com.meirengu.medical.vo.GoodsVo;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 19:49.
 * 商品接口
 */
public interface IGoodsService {
    /**
     * 根据对应条件查询商品数据
     * @param goodsVo
     * @return
     */
    String getDoctorData(GoodsVo goodsVo);
}
