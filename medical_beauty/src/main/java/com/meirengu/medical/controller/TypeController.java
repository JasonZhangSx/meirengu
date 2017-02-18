package com.meirengu.medical.controller;

import com.meirengu.medical.service.ITypeService;
import com.meirengu.medical.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 20:34.
 * 商品类型Controller
 */
@CrossOrigin
@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private ITypeService iTypeService;
    /**
     * 条件查询项目分类
     * @param typeVo 商品类型Model
     * @return 将添加结果返回
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getBrandData(TypeVo typeVo) {
        return iTypeService.getTypeData(typeVo);
    }

    /**
     * 添加商品类型数据
     * @param typeVo 商品类型Model
     * @return 将结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(TypeVo typeVo){
        return iTypeService.addTypeData(typeVo);
    }
    /**
     * 修改商品类型数据
     * @param typeVo 商品类型Model
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(TypeVo typeVo){
        return iTypeService.updateTypeData(typeVo);
    }

    /**
     * 删除商品类型数据
     * @param typeId 商品类型Id
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{icId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int typeId) {
        return iTypeService.deleteTypeData(typeId);
    }
}
