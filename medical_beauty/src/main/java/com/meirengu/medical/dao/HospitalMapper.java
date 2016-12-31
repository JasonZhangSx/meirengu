package com.meirengu.medical.dao;

import com.meirengu.medical.model.Hospital;
import com.meirengu.medical.vo.HospitalVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalMapper {
    /**
     * 条件查询医院列表
     * @param hospitalVo
     * @return 对应数据
     */
    List<HospitalVo> conditionQuery(HospitalVo hospitalVo);
    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 对应数据
     */
    List selectionHospital(HospitalVo hospitalVo);
    /**
     * 添加医院数据
     * @param hospitalVo
     * @return
     */
    int insertSelective(HospitalVo hospitalVo);

    /**
     * 修改医院数据
     * @param hospitalVo
     * @return
     */
    int conditionUpdate(HospitalVo hospitalVo);
}