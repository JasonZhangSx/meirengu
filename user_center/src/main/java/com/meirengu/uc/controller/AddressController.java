package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huoyan403 on 3/18/2017.
 */
@RestController
public class AddressController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService service;// 调用业务层方法
    @RequestMapping(value = "/showProvinceList" ,method = RequestMethod.GET)
    public Result showProvinceList(HttpServletRequest request, HttpServletResponse response) {
        return setResult(StatusCode.OK, service.showProvinceList(), StatusCode.codeMsgMap.get(StatusCode.OK));
    }
    @RequestMapping(value = "/showCityListByPid",method = RequestMethod.GET)
    public Result showCityListByPid(HttpServletRequest request, HttpServletResponse response, int pid) throws IOException {
        return setResult(StatusCode.OK, service.showCityListByPid(pid), StatusCode.codeMsgMap.get(StatusCode.OK));
    }
    @RequestMapping(value = "/showAreasByCityId",method = RequestMethod.GET)
    public Result showAreasByCityId(HttpServletRequest request, HttpServletResponse response, Integer citysId) throws IOException {
        return setResult(StatusCode.OK, service.showAreaListBycid(citysId), StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /*@RequestMapping("/showProByCityId")
    public Result showProByCityId(HttpServletRequest request, HttpServletResponse response, Integer citysId) throws IOException {
        return setResult(StatusCode.OK, service.showProByCityId(citysId), StatusCode.codeMsgMap.get(StatusCode.OK));
    }
    @RequestMapping("/showProCityByAreaId")
    public Result showProCityByAreaId(HttpServletRequest request, HttpServletResponse response, Integer citysId) throws IOException {
        return setResult(StatusCode.OK, service.showProCityByAreaId(citysId), StatusCode.codeMsgMap.get(StatusCode.OK));
    }*/
}
