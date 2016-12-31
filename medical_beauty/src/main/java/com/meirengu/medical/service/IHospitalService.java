package com.meirengu.medical.service;

import com.meirengu.medical.vo.HospitalVo;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 17:40.
 * 医院接口
 */
public interface IHospitalService {
    /**
     * 根据对应条件查询医院数据
     * @param hospitalVo
     * @return 将查询结果已json格式返回
     */
    String getHospitalData(HospitalVo hospitalVo);

    /**
     * 添加医院数据
     * @param hospitalVo
     * @return
     */
    String insertSelective(HospitalVo hospitalVo);

    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 对应数据
     */
    String selectionHospital(HospitalVo hospitalVo);

    /**
     * 修改医院数据
     * @param hospitalVo
     * @return
     */
    String conditionUpdate(HospitalVo hospitalVo);

    /**
     * 逻辑删除医院数据
     * @param hospitalVo
     * @return
     */
    String conditionDelete(HospitalVo hospitalVo);
}
