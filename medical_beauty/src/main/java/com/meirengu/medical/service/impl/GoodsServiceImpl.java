package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.GoodsMapper;
import com.meirengu.medical.service.IGoodsService;
import com.meirengu.medical.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 19:51.
 * 商品接口实现
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    private final static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public String getDoctorData(GoodsVo goodsVo) {

        return null;
    }
}
