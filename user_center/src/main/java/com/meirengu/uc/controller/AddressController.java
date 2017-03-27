package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.po.AddressPO;
import com.meirengu.uc.service.AddressService;
import com.meirengu.uc.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by huoyan403 on 3/18/2017.
 */
@RestController
@RequestMapping("address")
public class AddressController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService service;// 调用业务层方法
    @RequestMapping(value = "/province" ,method = RequestMethod.GET)
    public Result showProvinceList(HttpServletRequest request, HttpServletResponse respons) {
        try {
            return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(service.showProvinceList(),List.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info("AddressController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    @RequestMapping(value = "/city",method = RequestMethod.GET)
    public Result showCityListByPid(int pid) throws IOException {
        try {
            return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(service.showCityListByPid(pid),List.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info("AddressController.showCityListByPid:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    @RequestMapping(value = "/area",method = RequestMethod.GET)
    public Result showAreasByCityId(Integer citys_id) throws IOException {
        try {
            List<Area> areaList = service.showAreaListBycid(citys_id);
            if(areaList.size()!=0){
                return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(service.showAreaListBycid(citys_id),List.class), StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("AddressController.showAreasByCityId:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

   @RequestMapping(value = "/superarea",method = RequestMethod.GET)
    public Result showProByCityId(Integer area_id) throws IOException {
       try {
            AddressPO addressPO = service.showAddress(area_id);
           if(addressPO !=null){
            return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(addressPO,AddressPO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
           }else{
               return setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
           }
       }catch (Exception e){
           logger.info("AddressController.showProByCityId:{}",e.getMessage());
           return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
       }

    }

     /*@RequestMapping("/showProCityByAreaId")
    public Result showProCityByAreaId(HttpServletRequest request, HttpServletResponse response, Integer citysId) throws IOException {
        return setResult(StatusCode.OK, service.showProCityByAreaId(citysId), StatusCode.codeMsgMap.get(StatusCode.OK));
    }*/
}
