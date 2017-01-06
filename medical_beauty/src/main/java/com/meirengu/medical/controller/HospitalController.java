package com.meirengu.medical.controller;

import com.meirengu.medical.service.IHospitalService;
import com.meirengu.medical.util.UtilData;
import com.meirengu.medical.vo.HospitalVo;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/30 11:25.
 * 医院Controller
 */
@Controller
@RequestMapping("/hospital")
public class HospitalController {
    @Autowired
    private IHospitalService iHospitalService;

    /**
     * 根据对应条件查询医院数据
     * @param hospitalVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String getHospitalData(HospitalVo hospitalVo) {
        return iHospitalService.getHospitalData(hospitalVo);
    }
    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 将结果以json格式返回
     * selectionHospital
     */
    @RequestMapping(method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String selectionHospital(HospitalVo hospitalVo) {
        return iHospitalService.selectionHospital(hospitalVo);
    }
    /**
     * 添加医院
     * @param hospitalVo
     * @return 将结果以json格式返回
     * TODO 只上传医院资质
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addHospital(HospitalVo hospitalVo, HttpServletRequest request) {
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
        return iHospitalService.insertSelective(hospitalVo,iter,multiRequest);
    }

    /**
     * 修改医院
     * @param hospitalVo
     * @return 将结果以json格式返回
     * TODO 医院图片未定
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(HospitalVo hospitalVo) {
        return iHospitalService.conditionUpdate(hospitalVo);
    }


    /**
     * 逻辑删除删除医院
     * @param hospitalId
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{hospitalId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int hospitalId) {
        return iHospitalService.conditionDelete(hospitalId);
    }
}
