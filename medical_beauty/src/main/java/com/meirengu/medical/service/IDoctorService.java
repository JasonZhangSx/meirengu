package com.meirengu.medical.service;

import com.meirengu.medical.vo.DoctorVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

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
    String getDoctorData(DoctorVo doctorVo);
    /**
     * 甄选医生页面
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    String selectionDoctor(DoctorVo doctorVo);
    /**
     * 添加医生数据
     * @param doctorVo 医生Model
     * @return 修改结果返回
     */
    String addDoctorData(DoctorVo doctorVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest);
    /**
     * 逻辑修改医生数据
     * @param doctorVo
     * @return
     */
    String updateDoctor(DoctorVo doctorVo);

    /**
     * 逻辑删除医生数据
     * @param doctorId
     * @return
     */
    String deleteDoctor(int doctorId);
}
