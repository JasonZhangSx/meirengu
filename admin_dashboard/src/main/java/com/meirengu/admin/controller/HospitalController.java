package com.meirengu.admin.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.admin.service.IHospitalService;
import com.meirengu.admin.vo.HospitalVo;
import com.meirengu.commons.authority.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/30 11:25.
 * 医院Controller
 */
@Controller
@RequestMapping("/hospital")
public class HospitalController{
    @Autowired
    private IHospitalService iHospitalService;

    /**
     * 根据对应条件查询医院数据
     * @param hospitalVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getHospitalData(HospitalVo hospitalVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iHospitalService.getHospitalData(hospitalVo);
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/hospital/hospital_list");
        return modelAndView;
    }
    /**
     * 医院详情页面
     * @param hospitalVo
     * @return 将结果以json格式返回
     * selectionHospital
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getHospitalDetail(HospitalVo hospitalVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iHospitalService.getHospitalData(hospitalVo);
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/hospital/hospital_detail");
        return modelAndView;
    }
    /**
     * 添加医院
     * @param hospitalVo
     * @return 将结果以json格式返回
     * TODO 只上传医院资质
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object addHospital(HospitalVo hospitalVo, HttpServletRequest request) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
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
        ModelAndView modelAndView = new ModelAndView();
        try {
            hospitalVo.setSetupTime(format.parse(request.getParameter("hiredate").toString()));
        } catch (ParseException e) {
            hospitalVo.setSetupTime(new Date());
        }
        Map<String,Object> map = iHospitalService.insertSelective(hospitalVo,iter,multiRequest);
//        modelAndView.addObject("map",map);
//        modelAndView.setViewName("");
//        return selectionHospital(null);
        return JSON.toJSON(map);
    }
    /**
     * 查询医院详情并返回修改页
     * @param hospitalVo
     * @return 将结果以json格式返回
     * selectionHospital
     */
    @RequestMapping(value = "/detailUpdate",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getHospitalDetailUpdate(HospitalVo hospitalVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iHospitalService.getHospitalData(hospitalVo);
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/hospital/hospital_update");
        return modelAndView;
    }

    /**
     * 修改医院
     * @param hospitalVo
     * @return 将结果以json格式返回
     * TODO 医院图片未定
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateHospital(HospitalVo hospitalVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iHospitalService.conditionUpdate(hospitalVo);
        return JSON.toJSON(map);
    }


    /**
     * 逻辑删除删除医院
     * @param hospitalId
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{hospitalId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ModelAndView deleteHospital(@PathVariable int hospitalId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iHospitalService.conditionDelete(hospitalId);
        modelAndView.addObject("map",map);
        modelAndView.setViewName("");
        return modelAndView;
    }
}
