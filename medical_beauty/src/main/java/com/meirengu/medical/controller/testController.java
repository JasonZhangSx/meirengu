package com.meirengu.medical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/9 20:44.
 */
@CrossOrigin
@Controller
@RequestMapping("/testImg")
public class testController {
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addHospital() {
        return "http://image.meirenguvip.com/hospital/49/doctor/10/148464488418_200_200.png";
    }
}
