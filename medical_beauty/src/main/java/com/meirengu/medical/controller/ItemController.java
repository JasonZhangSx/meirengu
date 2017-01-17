package com.meirengu.medical.controller;

import com.meirengu.medical.service.IItemService;
import com.meirengu.medical.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/12 14:25.
 * 项目Controller
 */
@CrossOrigin
@Controller
@RequestMapping("/itemPro")
public class ItemController extends WebMvcConfigurerAdapter {
    @Autowired
    private IItemService iItemService;
    /**
     * 条件查询项目
     * @param itemVo 项目Model
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String getBrandData(ItemVo itemVo) {
        return iItemService.getItemData(itemVo);
    }

    /**
     * 添加項目数据
     * @param itemVo 項目对应Model
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(ItemVo itemVo,HttpServletRequest request){
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
        return iItemService.addItemData(itemVo,iter,multiRequest);
    }
    /**
     * 修改項目数据
     * @param itemVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(ItemVo itemVo){
        return iItemService.updateItemData(itemVo);
    }

    /**
     * 删除項目数据
     * @param icId 項目ID
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{icId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int icId) {
        return iItemService.deleteItemData(icId);
    }
}
