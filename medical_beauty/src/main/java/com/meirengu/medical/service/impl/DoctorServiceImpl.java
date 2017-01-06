package com.meirengu.medical.service.impl;

import com.meirengu.medical.dao.DoctorMapper;
import com.meirengu.medical.service.IDoctorService;
import com.meirengu.medical.util.CodeUtil;
import com.meirengu.medical.util.ResultUtil;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.vo.DoctorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

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
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<DoctorVo> list = new ArrayList<>();
            list = doctorMapper.conditionQuery(doctorVo);
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
            list = doctorMapper.selectionDoctor(doctorVo);
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
            while(iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multipartHttpServletRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (!"".equals(myFileName.trim())) {
                        //重新生成文件名，生成规则：当前时间戳+随机数
                        String fileName = String.valueOf(System.currentTimeMillis() / 1000).concat(String.valueOf(new Random().nextInt()));
                        File localFile = new File(UtilData.filePath + fileName);
                        try {
                            file.transferTo(localFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            /**
             doctorVo.setDoctorPic(doctorPicImg.getOriginalFilename());
             doctorVo.setCertificatePic(certificatePicOne.getOriginalFilename()+","+certificatePicTwo.getOriginalFilename());
             */
            doctorVo.setCreatedate(new Date());
            doctorMapper.insertSelective(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("addDoctorData ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 修改医生数据
     * @param doctorId 医生id
     * @return
     */
    @Override
    public String updateDoctor(int doctorId) {
        logger.info("Request updateDoctor parameter:{}", doctorId);
        //获取文件需要上传到的路径
        try {
            DoctorVo doctorVo = new DoctorVo();
            doctorVo.setDoctorId(doctorId);
            doctorMapper.updateDoctor(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateDoctor ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
    /**
     * 删除医生数据
     * @param doctorVo 医生Model
     * @return
     */
    @Override
    public String deleteDoctor(DoctorVo doctorVo) {
        logger.info("Request deleteDoctor parameter:{}", doctorVo.toString());
        //获取文件需要上传到的路径
        try {
            doctorVo.setDoctorStatus((byte) 0);
            doctorMapper.updateDoctor(doctorVo);
            return ResultUtil.getResult(CodeUtil.CORRECT.getCode(),null);
        } catch (Exception e) {
            logger.error("updateDoctor ErrorMsg{}",e.getMessage());
            return ResultUtil.getResult(CodeUtil.ERROR_INSERT.getCode(),null);
        }
    }
}
