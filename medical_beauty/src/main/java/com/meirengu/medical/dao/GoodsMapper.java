package com.meirengu.medical.dao;

import com.meirengu.medical.model.Goods;
import com.meirengu.medical.vo.GoodsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    /**
     * 根据条件查询商品数据
     * @param goodsVo
     * @return
     */
    List<GoodsVo> getDoctorData(GoodsVo goodsVo);
}
