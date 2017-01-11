package com.meirengu.mall.controller;

import com.meirengu.mall.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 购物车controller
 *
 * @author 建新
 * @create 2017-01-10 19:35
 */
@Controller
@RequestMapping("/order")
public class OrderController extends  BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    public Map<String, Object> list(@RequestParam(value = "user_id", required = false) Integer userId,
                                    @RequestParam(value = "state", required = false) Integer state){

        return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
    }
}
