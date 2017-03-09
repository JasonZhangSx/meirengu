package com.meirengu.admin.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.admin.model.Hospital;
import com.meirengu.admin.service.IDoctorService;
import com.meirengu.admin.service.IHospitalService;
import com.meirengu.admin.vo.DoctorVo;
import com.meirengu.admin.vo.HospitalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/27 19:07.
 * 医生Controller
 */
@CrossOrigin
@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final static Logger logger = LoggerFactory.getLogger(DoctorController.class);
    @Autowired
    private IDoctorService iDoctorService;
    @Autowired
    private IHospitalService iHospitalService;
    /**
     * 根据对应条件查询医生数据
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDoctorData(DoctorVo doctorVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iDoctorService.getDoctorData(doctorVo);
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/doctor/doctor_list");
        return modelAndView;
    }
//
//    /**
//     * 甄选医生页面
//     * @param doctorVo
//     * @return 将查询结果已json格式返回
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public String selectionDoctor(DoctorVo doctorVo) {
//        return iDoctorService.selectionDoctor(doctorVo);
//    }
    /**
     * 医生详情页面
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDoctorDetail(DoctorVo doctorVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iDoctorService.getDoctorData(doctorVo);
        try {
            Map m = (Map) map.get("data");
            List list = (List) m.get("list");
            doctorVo = (DoctorVo) list.get(0);
            HospitalVo hospitalVo = new HospitalVo();
            hospitalVo.setHospitalId(doctorVo.getHospitalId());
            Map hm = (Map) iHospitalService.getHospitalData(hospitalVo).get("data");
            List hl = (List) hm.get("list");
            hospitalVo= (HospitalVo) hl.get(0);
            map.put("hospital",hospitalVo.getHospitalName());
        }catch (Exception e){
            logger.error("医生详情查询所属医院失败");
        }
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/doctor/doctor_detail");
        return modelAndView;
    }
    /**
     * 医生添加页面
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDoctorDetailSave(DoctorVo doctorVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iHospitalService.getHospitalData(new HospitalVo());
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/doctor/doctor_add");
        return modelAndView;
    }
    /**
     * 添加医生数据
     * @param doctorVo 医生对应Model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object addDoctorData(DoctorVo doctorVo,HttpServletRequest request){
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
        Map<String,Object> map = iDoctorService.addDoctorData(doctorVo,iter,multiRequest);
        return JSON.toJSON(map);
    }
    /**
     * 医生详情页面
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/detailUpdate",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDoctorDetailUpdate(DoctorVo doctorVo) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = iDoctorService.getDoctorData(doctorVo);
        modelAndView.addObject("map",map);
        modelAndView.setViewName("/view/doctor/doctor_update");
        return modelAndView;
    }
    /**
     * 修改医生数据
     * @param doctorVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateDoctor(DoctorVo doctorVo) {
        Map<String,Object> map = iDoctorService.updateDoctor(doctorVo);
        return JSON.toJSON(map);
    }
    /**
     * 逻辑删除医生
     * @param doctorId
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(value = "/{doctorId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDoctor(@PathVariable int doctorId) {
        Map<String,Object> map = iDoctorService.deleteDoctor(doctorId);
        return map.toString();
    }
}
