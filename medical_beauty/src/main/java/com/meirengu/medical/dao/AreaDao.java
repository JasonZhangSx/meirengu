package com.meirengu.medical.dao;


import com.meirengu.medical.vo.AreaVo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AreaDao {

    /**
     * 添加地区数据
     * @param areaVo 地区Model
     * @return 添加结果
     */
    int addAreaData(AreaVo areaVo);
    /**
     * 查询地区数据
     * @param areaVo 地区Model
     * @return 查询结果
     */
    List<AreaVo> getAreaData(AreaVo areaVo);
    /**
     * 更新地区数据
     * @param areaVo 地区Model
     * @return 更新结果
     */
    int updateAreaData(AreaVo areaVo);
}