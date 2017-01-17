package com.meirengu.medical.dao;


import com.meirengu.medical.vo.TypeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDao {
    /**
     * 添加商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回添加商品类型数据结果
     */
    int addTypeData(TypeVo typeVo);
    /**
     * 查询商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回查询商品类型数据结果
     */
    List<TypeVo> getTypeData(TypeVo typeVo);
    /**
     * 更新商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回更新商品类型数据结果
     */
    int updateTypeData(TypeVo typeVo);
    /**
     * 删除商品类型数据
     * @param typeVo 商品类型Model
     * @return 返回删除商品类型数据结果
     */
    int deleteTypeData(TypeVo typeVo);
}