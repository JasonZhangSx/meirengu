package com.meirengu.admin.service;


import com.meirengu.admin.vo.TypeVo;

import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 20:26.
 * 商品类型业务逻辑接口
 */
public interface ITypeService {
    /**
     * 添加商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回添加商品类型数据结果
     */
    Map addTypeData(TypeVo typeVo);
    /**
     * 查询商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回查询商品类型数据结果
     */
    Map getTypeData(TypeVo typeVo);
    /**
     * 更新商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回更新商品类型数据结果
     */
    Map updateTypeData(TypeVo typeVo);
    /**
     * 删除商品类型数据
     * @param typeId 商品类型Id
     * @return 返回删除商品类型数据结果
     */
    Map deleteTypeData(Integer typeId);
}
