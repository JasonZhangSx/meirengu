package com.meirengu.uc.controller;

import com.meirengu.uc.model.Areas;
import com.meirengu.uc.model.Citys;
import com.meirengu.uc.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by huoyan403 on 3/18/2017.
 */
@RestController
public class AddressController {


    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService service;// 调用业务层方法
    @RequestMapping("/showProvinceList")
    public String showProvinceList(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        session.setAttribute("proList", service.showProvinceList());// 存入作用域中
        return "modules/sys/userModifyPwd";
        //return "/index.jsp";
    }

    // 根据ajax返回到控制器的省id来查询相对应的城市名
    @RequestMapping("/showCityListByPid")
    public void showCityListByPid(HttpServletRequest request, HttpServletResponse response, int pid) throws IOException {

        List<Citys> city1ist = service.showCityListByPid(pid);


    }
    @RequestMapping("/showAreasByCityId")
    public void showAreasByCityId(HttpServletRequest request, HttpServletResponse response, Integer citysId) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        List<Areas> areasList = service.showAreaListBycid(citysId);


    }







}
