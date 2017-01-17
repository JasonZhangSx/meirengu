package com.meirengu.medical.controller;

import com.meirengu.medical.service.IItemClassService;
import com.meirengu.medical.vo.ItemClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 11:38.
 * 项目分类Controller
 */
@CrossOrigin
@Controller
@RequestMapping("/itemClass")
public class ItemClassController {
    @Autowired
    private IItemClassService iItemClassService;
    /**
     * 条件查询项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String getBrandData(ItemClassVo itemClassVo) {
        return iItemClassService.getItemClassData(itemClassVo);
    }

    /**
     * 添加品牌数据
     * @param itemClassVo 品牌对应Model
     * @return 将结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(ItemClassVo itemClassVo){
        return iItemClassService.addItemClassData(itemClassVo);
    }
    /**
     * 修改品牌数据
     * @param itemClassVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(ItemClassVo itemClassVo){
        return iItemClassService.updateItemClassData(itemClassVo);
    }

    /**
     * 删除品牌数据
     * @param icId 品牌ID
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{icId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int icId) {
        return iItemClassService.deleteItemClassData(icId);
    }
}
