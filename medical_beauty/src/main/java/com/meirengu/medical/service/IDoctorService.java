package com.meirengu.medical.service;

import com.meirengu.medical.vo.DoctorVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:33.
 * 医生接口
 */
public interface IDoctorService {
    /**
     * 根据对应条件查询医生数据
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    String getDoctorData(DoctorVo doctorVo);

    /**
     * 添加医生数据
     * @param doctorVo 医生Model
     * @return 修改结果返回
     */
    String addDoctorData(DoctorVo doctorVo);
}
