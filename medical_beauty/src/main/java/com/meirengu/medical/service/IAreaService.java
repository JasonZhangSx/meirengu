package com.meirengu.medical.service;

import com.meirengu.medical.vo.AreaVo;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 18:06.
 * 地区业务逻辑接口
 */
public interface IAreaService {
    /**
     * 添加地区数据
     * @param areaVo 地区Model
     * @return 添加结果
     */
    String addAreaData(AreaVo areaVo);
    /**
     * 查询地区数据
     * @param areaVo 地区Model
     * @return 查询结果
     */
    String getAreaData(AreaVo areaVo);
    /**
     * 更新地区数据
     * @param areaVo 地区Model
     * @return 更新结果
     */
    String updateAreaData(AreaVo areaVo);
    /**
     * 删除地区数据
     * @param areaId 地区ID
     * @return 删除结果
     */
    String deleteAreaData(Integer areaId);
}
