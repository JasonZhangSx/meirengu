package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.HospitalMapper;
import com.meirengu.medical.service.IHospitalService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.vo.HospitalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/29 18:16.
 * 医生接口实现
 */
@Service
public class HospitalServiceImpl implements IHospitalService {
    private final static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
    @Autowired
    private HospitalMapper hospitalMapper;

    /**
     * 根据对应条件查询医院数据
     * @param hospitalVo
     * @return 将查询结果已json格式返回
     */
    @Override
    public String getHospitalData(HospitalVo hospitalVo) {
        logger.info("Request getHospitalData parameter:{}", hospitalVo.toString());
        String json = null;
        List<HospitalVo> list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            list=hospitalMapper.conditionQuery(hospitalVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        }catch (Exception e){
            logger.error("getDoctorData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }

    /**
     * 插入医院数据
     * @param hospitalVo
     * @return
     */
    @Override
    public String insertSelective(HospitalVo hospitalVo) {
        logger.info("Request insertSelective parameter:{}", hospitalVo.toString());
        try {
            String[] fileNum=hospitalVo.getCertificatePic().split(",");
            for (String fileName : fileNum){
                OutputStream doctorPic = new FileOutputStream(new File(UtilData.filePath,fileName));
            }
            hospitalMapper.insertSelective(hospitalVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        }catch (Exception e){
            logger.error("insertSelective ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 对应数据
     *  TODO 新增人如何获取未定
     */
    @Override
    public String selectionHospital(HospitalVo hospitalVo) {
        logger.info("Request selectionHospital parameter:{}", hospitalVo.toString());
        List list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            list=hospitalMapper.selectionHospital(hospitalVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        }catch (Exception e){
            logger.error("selectionHospital ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 修改医院数据
     * @param hospitalVo
     * @return
     * TODO 修改人如何获取未定
     */
    @Override
    public String conditionUpdate(HospitalVo hospitalVo) {
        logger.info("Request conditionUpdate parameter:{}", hospitalVo.toString());
        try {
            hospitalMapper.conditionUpdate(hospitalVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        }catch (Exception e){
            logger.error("conditionUpdate ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_UPDATE.getCode(),null);
        }
    }

    /**
     * 逻辑删除医院数据
     * @param hospitalVo
     * @return
     *  TODO 修改人如何获取未定
     */
    @Override
    public String conditionDelete(HospitalVo hospitalVo) {
        logger.info("Request conditionDelete parameter:{}", hospitalVo.toString());
        try {
            hospitalVo.setHospitalStatus(UtilData.delete_yes);
            hospitalMapper.conditionUpdate(hospitalVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        }catch (Exception e){
            logger.error("conditionDelete ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_DELETE.getCode(),null);
        }
    }
}
