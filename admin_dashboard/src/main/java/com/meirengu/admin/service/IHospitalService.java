package com.meirengu.admin.service;

import com.meirengu.admin.vo.HospitalVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.Map;

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
    Map<String,Object> getHospitalData(HospitalVo hospitalVo);

    /**
     * 添加医院数据
     * @param hospitalVo
     * @return
     */
    Map<String,Object> insertSelective(HospitalVo hospitalVo, Iterator<String> iter, MultipartHttpServletRequest
            multipartHttpServletRequest);

    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 对应数据
     */
    Map<String,Object> selectionHospital(HospitalVo hospitalVo);

    /**
     * 修改医院数据
     * @param hospitalVo
     * @return
     */
    Map<String,Object> conditionUpdate(HospitalVo hospitalVo);

    /**
     * 逻辑删除医院数据
     * @param hospitalId
     * @return
     */
    Map<String,Object> conditionDelete(int hospitalId);
}
