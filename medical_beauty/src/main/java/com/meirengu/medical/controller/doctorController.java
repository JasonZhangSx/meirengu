package com.meirengu.medical.controller;

import com.meirengu.medical.service.IDoctorService;
import com.meirengu.medical.vo.DoctorVo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:07.
 * 医生Controller--》医生所有操作
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController{
    private final static Logger logger = LoggerFactory.getLogger(DoctorController.class);
    @Autowired
    private IDoctorService iDoctorService;
    /**
     * 根据对应条件查询医生数据
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/getDoctorData",method = RequestMethod.POST)
    @ResponseBody
    public String getDoctorData(DoctorVo doctorVo) {
        return iDoctorService.getDoctorData(doctorVo);
    }

    /**
     * 添加医生数据
     * @param doctorVo 医生对应Model
     * @param doctorPicImg 医生头像图片
     * @param certificatePicOne 医生执业证书快照正面
     * @param certificatePicTwo 医生执业证书快照反面
     * @return
     */
    @RequestMapping(value = "/addDoctorData",method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(DoctorVo doctorVo,
                                @RequestParam("doctorPicImg") MultipartFile doctorPicImg,
                                @RequestParam("certificatePicOne") MultipartFile certificatePicOne,
                                @RequestParam("certificatePicTwo") MultipartFile certificatePicTwo){
        doctorVo.setDoctorPic(doctorPicImg.getOriginalFilename());
        doctorVo.setCertificatePic(certificatePicOne.getOriginalFilename()+","+certificatePicTwo.getOriginalFilename());
        return iDoctorService.addDoctorData(doctorVo);
    }


}
