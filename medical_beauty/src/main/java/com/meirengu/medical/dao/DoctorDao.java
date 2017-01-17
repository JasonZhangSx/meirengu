package com.meirengu.medical.dao;

import com.meirengu.medical.model.Doctor;
import com.meirengu.medical.vo.DoctorVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDao {

    /**
     * 甄选医生页面
     * @param doctorVo
     * @return
     */
    List selectionDoctor(DoctorVo doctorVo);


    /**
     * 条件查询医生列表
     * @param doctorVo
     * @return 对应数据
     */
    List<DoctorVo> conditionQuery(DoctorVo doctorVo);

    /**
     * 逻辑插入医生数据
     * @param doctorVo
     * @return
     */
    int insertSelective(DoctorVo doctorVo);

    /**
     * 逻辑修改医生数据
     * @param doctorVo
     * @return
     */
    int updateDoctor(DoctorVo doctorVo);
}