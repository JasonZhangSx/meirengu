package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 订单控制类
 * Created by maoruxin on 2017/3/14.
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/appointment", method = RequestMethod.POST)
    public Result insertAppointment(@RequestParam(value = "item_id", required = false) int itemId,
                                    @RequestParam(value = "item_name", required = false) String itemName,
                                    @RequestParam(value = "item_level_id", required = false) int itemLevelId,
                                    @RequestParam(value = "item_level_name", required = false) String itemLevelName,
                                    @RequestParam(value = "item_level_amount", required = false) BigDecimal itemLevelAmount,
                                    @RequestParam(value = "item_num", required = false) int itemNum,
                                    @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount,
                                    @RequestParam(value = "rebate_amount", required = false) BigDecimal rebateAmount,
                                    @RequestParam(value = "cost_amount", required = false) BigDecimal costAmount,
                                    @RequestParam(value = "user_id", required = false) int userId,
                                    @RequestParam(value = "user_name", required = false) String userName,
                                    @RequestParam(value = "user_phone", required = false) String userPhone,
                                    @RequestParam(value = "user_address_id", required = false) int userAddressId,
                                    @RequestParam(value = "order_message", required = false) String orderMessage,
                                    @RequestParam(value = "order_from", required = false) String orderFrom,
                                    @RequestParam(value = "user_weixin", required = false) String userWeixin){

        Order order = new Order();
        try{
            int i = orderService.insert(order);
            if(i > 0){
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.BULLETIN_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.BULLETIN_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }
}
