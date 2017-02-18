package com.meirengu.medical.controller;

import com.meirengu.medical.service.IBrandService;
import com.meirengu.medical.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/10 18:12.
 * 品牌Controller
 */
@CrossOrigin
@Controller
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private IBrandService iBrandService;

    /**
     * 根据对应条件查询品牌数据
     * @param brandVo
     * @return 将查询结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getBrandData(BrandVo brandVo) {
        return iBrandService.getBrandData(brandVo);
    }

    /**
     * 添加品牌数据
     * @param brandVo 品牌对应Model
     * @return 将结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(BrandVo brandVo,HttpServletRequest request){
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
        return iBrandService.addBrandData(brandVo,iter,multiRequest);
    }
    /**
     * 修改品牌数据
     * @param brandVo
     * @return 将结果以json格式返回
     * TODO 医院图片未定
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(BrandVo brandVo) {
        return iBrandService.updateBrandData(brandVo);
    }

    /**
     * 删除品牌数据
     * @param brandId 品牌ID
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{brandId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int brandId) {
        return iBrandService.deleteBrandData(brandId);
    }
}
