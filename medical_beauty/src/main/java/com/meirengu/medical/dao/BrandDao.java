package com.meirengu.medical.dao;


import com.meirengu.medical.vo.BrandVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandDao {

    /**
     * 条件查询品牌列表
     * @param brandVo 对应Model
     * @return 查询结果
     */
    List<BrandVo> selectByPrimaryKey(BrandVo brandVo);

    /**
     * 条件添加品牌数据
     * @param brandVo 对应Model
     * @return 添加结果
     */
    int insertSelective(BrandVo brandVo);

    /**
     * 条件更新品牌数据
     * @param brandVo 对应Model
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(BrandVo brandVo);
}