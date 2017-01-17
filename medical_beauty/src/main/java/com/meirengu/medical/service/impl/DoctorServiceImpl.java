package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.DoctorDao;
import com.meirengu.medical.service.IDoctorService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.FileUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.vo.DoctorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:35.
 * 医生业务逻辑实现
 */
@Service
public class DoctorServiceImpl implements IDoctorService {
    private final static Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    @Autowired
    private DoctorDao doctorDao;

    /**
     * 条件查询医生列表
     * @param doctorVo
     * @return 对应数据
     */
    @Override
    public String getDoctorData(DoctorVo doctorVo) {
        logger.info("Request getDoctorData parameter:{}", doctorVo.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<DoctorVo> list = new ArrayList<>();
            list = doctorDao.conditionQuery(doctorVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        } catch (Exception e) {
            logger.error("getDoctorData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 甄选医生列表数据
     * @param doctorVo
     * @return 对应数据
     */
    @Override
    public String selectionDoctor(DoctorVo doctorVo) {
        logger.info("Request selectionDoctor parameter:{}", doctorVo.toString());
        List<DoctorVo> list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            list = doctorDao.selectionDoctor(doctorVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        } catch (Exception e) {
            logger.error("selectionDoctor ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }
    /**
     * 插入医生数据
     * @param doctorVo 医生Model
     * @return
     * TODO　图片未定
     */
    @Override
    public String addDoctorData(DoctorVo doctorVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest) {
        logger.info("Request addDoctorData parameter:{}", doctorVo.toString());
        //获取文件需要上传到的路径
        try {
            Map<String,String> map = new HashMap<>();
            doctorVo.setCreatedate(new Date());
            doctorDao.insertSelective(doctorVo);
            while(iter.hasNext()) {
                map= FileUtil.createFile(map,iter,multipartHttpServletRequest, UtilData.doctorImagePath+doctorVo.getDoctorId()+"/");
                for (String s : map.keySet()){
                    setImgValue(doctorVo,s,map.get(s));
                }
                map.clear();
            }
            doctorDao.updateDoctor(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("addDoctorData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 修改医生数据
     * @param doctorVo 医生Model
     * @return
     */
    @Override
    public String updateDoctor(DoctorVo doctorVo) {
        logger.info("Request updateDoctor parameter:{}", doctorVo);
        //获取文件需要上传到的路径
        try {
            doctorDao.updateDoctor(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateDoctor ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 删除医生数据
     * @param doctorId 医生id
     * @return
     */
    @Override
    public String deleteDoctor(int doctorId) {
        logger.info("Request deleteDoctor parameter:{}", doctorId);
        //获取文件需要上传到的路径
        try {
            DoctorVo doctorVo = new DoctorVo();
            doctorVo.setDoctorId(doctorId);
            doctorVo.setDoctorStatus((byte) 0);
            doctorDao.updateDoctor(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateDoctor ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 给数据库存储图片字段赋值
     * @param doctorVo 医生实体
     * @param imgName 图片名称
     * @param imageValue 图片值
     */
    private void setImgValue(DoctorVo doctorVo,String imgName,String imageValue){
        switch (imgName){
            case "doctorPicImg" :
                doctorVo.setDoctorPic(imageValue);
                break;
            case "certificatePicOne" :
                doctorVo.setDoctorProfile(imageValue);
                break;
            case "certificatePicTwo" :
                doctorVo.setDoctorProfile(doctorVo.getDoctorProfile()+","+imageValue);
                break;
        }
    }
}
