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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:07.
 * 医生Controller
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
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String getDoctorData(DoctorVo doctorVo) {
        return iDoctorService.getDoctorData(doctorVo);
    }

    /**
     * 甄选医生页面
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String selectionDoctor(DoctorVo doctorVo) {
        return iDoctorService.selectionDoctor(doctorVo);
    }

    /**
     * 添加医生数据
     * @param doctorVo 医生对应Model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(DoctorVo doctorVo,HttpServletRequest request){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        Iterator<String> iter = new ArrayList<String>().iterator();
        MultipartHttpServletRequest multiRequest = null;
        //判断 request 是否有文件上传,即多部分请求
        if(resolver.isMultipart(request)){
            //转换成多部分request
            multiRequest=(MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            iter=multiRequest.getFileNames();
        }
        return iDoctorService.addDoctorData(doctorVo,iter,multiRequest);
    }

    /**
     * 修改医生数据
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateDoctor(DoctorVo doctorVo) {
        return iDoctorService.updateDoctor(doctorVo);
    }
    /**
     * 逻辑删除医生
     * @param doctorId
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/{doctorId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDoctor(@PathVariable int doctorId) {
        return iDoctorService.deleteDoctor(doctorId);
    }


}
