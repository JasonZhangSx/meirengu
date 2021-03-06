package com.meirengu.medical.service;

import com.meirengu.medical.vo.BrandVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/10 14:43.
 * 品牌业务逻辑接口
 */
public interface IBrandService {
    /**
     * 条件查询品牌列表
     * @param brandVo 对应Model
     * @return 查询结果
     */
    String getBrandData(BrandVo brandVo);

    /**
     * 条件添加品牌数据
     * @param brandVo 对应Model
     * @return 添加结果
     */
    String addBrandData(BrandVo brandVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest);

    /**
     * 条件更新品牌数据
     * @param brandVo 对应Model
     * @return 更新结果
     */
    String updateBrandData(BrandVo brandVo);

    /**
     * 条件删除品牌数据
     * @param brandId 品牌数据
     * @return 删除结果
     */
    String deleteBrandData(Integer brandId);
}
