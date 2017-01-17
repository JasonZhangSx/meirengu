package com.meirengu.medical.service;

import com.meirengu.medical.vo.HospitalVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 17:40.
 * 医院业务逻辑接口
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
    String insertSelective(HospitalVo hospitalVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest);

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
     * @param hospitalId
     * @return
     */
    String conditionDelete(int hospitalId);
}
