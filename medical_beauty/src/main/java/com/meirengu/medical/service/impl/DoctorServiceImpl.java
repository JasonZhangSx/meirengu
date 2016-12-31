package com.meirengu.medical.service.impl;

import com.google.gson.Gson;
import com.meirengu.medical.dao.DoctorMapper;
import com.meirengu.medical.service.IDoctorService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.vo.DoctorVo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:35.
 * 医生接口实现
 */
@Service
public class DoctorServiceImpl implements IDoctorService {
    private final static Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    @Autowired
    private DoctorMapper doctorMapper;

    /**
     * 条件查询医生列表
     * @param doctorVo
     * @return 对应数据
     */
    @Override
    public String getDoctorData(DoctorVo doctorVo) {
        logger.info("Request getDoctorData parameter:{}", doctorVo.toString());
        List<DoctorVo> list = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        try {
            list = doctorMapper.conditionQuery(doctorVo);
            resultMap.put("list",list);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),resultMap);
        } catch (Exception e) {
            logger.error("getDoctorData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_SELECT.getCode(),null);
        }
    }

    /**
     * 插入医生数据
     * @param doctorVo 医生Model
     * @return
     */
    @Override
    public String addDoctorData(DoctorVo doctorVo) {
        logger.info("Request addDoctorData parameter:{}", doctorVo.toString());
        //获取文件需要上传到的路径
        try {
            OutputStream doctorPic = new FileOutputStream(new File(UtilData.filePath,doctorVo.getDoctorPic()));
            String[] fileNum=doctorVo.getCertificatePic().split(",");
            for (String fileName : fileNum){
                OutputStream certificatePic = new FileOutputStream(new File(UtilData.filePath,fileName));
            }
            doctorMapper.insertSelective(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (FileNotFoundException e) {
            logger.error("addDoctorData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }

}
