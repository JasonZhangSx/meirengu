package com.meirengu.mall.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.mall.service.ServiceCodeService;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meirengu.controller.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务码控制层
 * @author 建新
 * @create 2017-02-23 19:57
 */
@Controller
@RequestMapping("service_code")
public class ServiceCodeController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeController.class);

    @Autowired
    private ServiceCodeService serviceCodeService;

    /**
     * 验证服务码接口
     * @param code
     * @param hospitalId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validate", method = RequestMethod.POST)
    public Result validate(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "hospital_id", required = false) String hospitalId){

        if(StringUtil.isEmpty(code) || StringUtil.isEmpty(hospitalId)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        if(!StringUtil.isInteger(code) || !StringUtil.isInteger(hospitalId)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        int result = serviceCodeService.validateCode(Integer.parseInt(code), Integer.parseInt(hospitalId));
        LOGGER.info(">> validate service code return : {}", result);
        // 0抛出异常 1验证通过 2code已使用 3code不存在 4未使用的code不存在 5该医院无权限验证 6服务码过期
        switch (result){
            case 0:
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            case 1:
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            case 2:
                return super.setResult(StatusCode.SERVICE_CODE_USED, null, StatusCode.codeMsgMap.get(StatusCode.SERVICE_CODE_USED));
            case 3:
                return super.setResult(StatusCode.SERVICE_CODE_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.SERVICE_CODE_NOT_EXISTED));
            case 4:
                return super.setResult(StatusCode.SERVICE_CODE_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.SERVICE_CODE_NOT_EXISTED));
            case 5:
                return super.setResult(StatusCode.SERVICE_CODE_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.SERVICE_CODE_NOT_EXISTED));
            case 6:
                return super.setResult(StatusCode.SERVICE_CODE_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode.SERVICE_CODE_EXPIRE));
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
    }

    /**
     * 生成服务码接口
     * @param hospitalId
     * @param orderSN
     * @param userPhone
     * @param itemDesc
     * @param originalPrice
     * @param orderPrice
     * @param restPrice
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Result create(@RequestParam(value = "hospital_id", required = false) String hospitalId,
                         @RequestParam(value = "order_sn", required = false) String orderSN,
                         @RequestParam(value = "user_phone", required = false) String userPhone,
                         @RequestParam(value = "item_desc", required = false) String itemDesc,
                         @RequestParam(value = "original_price", required = false) String originalPrice,
                         @RequestParam(value = "order_price", required = false) String orderPrice,
                         @RequestParam(value = "rest_price", required = false) String restPrice,
                         @RequestParam(value = "item_id", required = false) String itemId){

        if(StringUtil.isEmpty(hospitalId) || StringUtil.isEmpty(orderSN) || StringUtil.isEmpty(userPhone) ||
                StringUtil.isEmpty(itemDesc) || StringUtil.isEmpty(originalPrice) || StringUtil.isEmpty(orderPrice) ||
                StringUtil.isEmpty(restPrice) || StringUtil.isEmpty(itemId)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(!StringUtil.isInteger(hospitalId) || !StringUtil.isNumeric(originalPrice) || !StringUtil.isNumeric(orderPrice) || !StringUtil.isNumeric(restPrice) || !StringUtil.isInteger(itemId)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        String code = serviceCodeService.create(Integer.parseInt(hospitalId), orderSN, userPhone, itemDesc, Double.parseDouble(originalPrice), Double.parseDouble(orderPrice), Double.parseDouble(restPrice), Integer.parseInt(itemId));
        if(StringUtil.isEmpty(code)){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }else {
            Map<String, Object> result = new HashMap<>();
            result.put("serviceCode", code);
            return super.setResult(StatusCode.OK, result, StatusCode.codeMsgMap.get(StatusCode.OK));
        }

    }
}
