package com.meirengu.admin.service;

import com.meirengu.admin.vo.DoctorVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:33.
 * 医生业务逻辑接口
 */
public interface IDoctorService {
    /**
     * 根据对应条件查询医生数据
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    Map getDoctorData(DoctorVo doctorVo);
    /**
     * 甄选医生页面
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    Map selectionDoctor(DoctorVo doctorVo);

    /**
     * 医生详情页面
     * @param doctorVo
     * @return
     */
    Map getDoctorDetails(DoctorVo doctorVo);
    /**
     * 添加医生数据
     * @param doctorVo 医生Model
     * @return 修改结果返回
     */
    Map addDoctorData(DoctorVo doctorVo, Iterator<String> iter, MultipartHttpServletRequest
            multipartHttpServletRequest);
    /**
     * 逻辑修改医生数据
     * @param doctorVo
     * @return
     */
    Map updateDoctor(DoctorVo doctorVo);

    /**
     * 逻辑删除医生数据
     * @param doctorId
     * @return
     */
    Map deleteDoctor(int doctorId);
}
