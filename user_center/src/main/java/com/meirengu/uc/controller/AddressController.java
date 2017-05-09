package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.vo.request.AddressVO;
import com.meirengu.uc.service.AddressService;
import com.meirengu.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huoyan403 on 3/18/2017.
 */
@RestController
@RequestMapping("address")
public class AddressController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService service;

    /**
     *查询所有省
     */
    @RequestMapping(value = "/province" ,method = RequestMethod.GET)
    public Result showProvinceList(HttpServletRequest request, HttpServletResponse respons) {
        logger.info("address/province run:{} ");
        try {
            return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(service.showProvinceList(),List.class,Area.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("AddressController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    /**
     *查询省下所有市
     */
    @RequestMapping(value = "/city",method = RequestMethod.GET)
    public Result showCityListByPid(int pid){
        logger.info("address/province run:{} params: ",pid);
        try {
            return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(service.showCityListByPid(pid),List.class,Area.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("AddressController.showCityListByPid:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    /**
     *查询市下县区
     */
    @RequestMapping(value = "/area",method = RequestMethod.GET)
    public Result showAreasByCityId(Integer citys_id){
        logger.info("address/area run:{} params: ",citys_id);
        try {
            List<Area> areaList = service.showAreaListBycid(citys_id);
            if(areaList.size()!=0){
                return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(service.showAreaListBycid(citys_id),List.class,Area.class), StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("AddressController.showAreasByCityId:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }
    /**
     *查询上级 省市区
     */
   @RequestMapping(value = "/superarea",method = RequestMethod.GET)
    public Result showProByCityId(Integer area_id){
       try {
            AddressVO addressVO = service.showAddress(area_id);
           if(addressVO !=null){
               return setResult(StatusCode.OK, ObjectUtils.getNotNullObject(addressVO,AddressVO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
           }else{
               return setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
           }
       }catch (Exception e){
           logger.error("AddressController.showProByCityId:{}",e.getMessage());
           return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
       }

    }
}
