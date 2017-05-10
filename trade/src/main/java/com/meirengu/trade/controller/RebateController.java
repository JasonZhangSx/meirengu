package com.meirengu.trade.controller;

import com.meirengu.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 优惠券控制类
 * Created by maoruxin on 2017/3/24.
 */
@RestController
@RequestMapping("/rebate")
public class RebateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateController.class);


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
