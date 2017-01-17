package com.meirengu.medical.controller;

import com.meirengu.medical.service.IAreaService;
import com.meirengu.medical.vo.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 19:14.
 * 地区Controller
 */
@CrossOrigin
@Controller
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private IAreaService iAreaService;
    /**
     * 条件查询地区数据
     * @param areaVo 地区Model
     * @return 将添加结果返回
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String getBrandData(AreaVo areaVo) {
        return iAreaService.getAreaData(areaVo);
    }

    /**
     * 添加地区数据
     * @param areaVo 地区对应Model
     * @return 将结果已json格式返回
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(AreaVo areaVo){
        return iAreaService.addAreaData(areaVo);
    }
    /**
     * 修改地区数据
     * @param areaVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(AreaVo areaVo){
        return iAreaService.updateAreaData(areaVo);
    }

    /**
     * 删除地区数据
     * @param icId 地区ID
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{icId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int icId) {
        return iAreaService.deleteAreaData(icId);
    }
}
