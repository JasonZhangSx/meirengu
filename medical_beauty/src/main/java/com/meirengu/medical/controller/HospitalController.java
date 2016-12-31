package com.meirengu.medical.controller;

import com.meirengu.medical.service.IHospitalService;
import com.meirengu.medical.vo.HospitalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    @RequestMapping(value = "/getHospitalData",method = RequestMethod.GET)
    @ResponseBody
    public String getHospitalData(HospitalVo hospitalVo) {
        return iHospitalService.getHospitalData(hospitalVo);
    }
    /**
     * 甄选医院页面
     * @param hospitalVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/selectionHospital",method = RequestMethod.GET)
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
    @RequestMapping(value = "/addHospital",method = RequestMethod.POST)
    @ResponseBody
    public String addHospital(HospitalVo hospitalVo, @RequestParam("certificatePicOne") MultipartFile certificatePicOne,
                              @RequestParam("certificatePicTwo") MultipartFile certificatePicTwo) {
        hospitalVo.setCertificatePic(certificatePicOne.getOriginalFilename()+","+certificatePicTwo.getOriginalFilename());
        return iHospitalService.insertSelective(hospitalVo);
    }

    /**
     * 修改医院
     * @param hospitalVo
     * @return 将结果以json格式返回
     * TODO 医院图片未定
     */
    @RequestMapping(value = "/updateHospital",method = RequestMethod.PUT)
    @ResponseBody
    public String updateHospital(HospitalVo hospitalVo) {
        return iHospitalService.conditionUpdate(hospitalVo);
    }


    /**
     * 逻辑删除删除医院
     * @param hospitalVo
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/deleteHospital",method = RequestMethod.PATCH)
    @ResponseBody
    public String deleteHospital(HospitalVo hospitalVo) {
        return iHospitalService.conditionDelete(hospitalVo);
    }
}
