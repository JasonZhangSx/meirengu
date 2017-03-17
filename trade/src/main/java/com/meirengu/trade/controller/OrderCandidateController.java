package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.OrderCandidate;
import com.meirengu.trade.service.OrderCandidateService;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.utils.OrderSNUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 候补订单控制类
 * Created by maoruxin on 2017/3/14.
 */
@RestController
@RequestMapping("/orderCandidate")
public class OrderCandidateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderCandidateController.class);

    @Autowired
    private OrderCandidateService orderCandidateService;

    /**
     * 候补预约新增接口
     * @param userId
     * @param userName
     * @param userPhone
     * @param userWeixin
     * @param itemId
     * @param itemName
     * @param itemLevelId
     * @param itemLevelName
     * @param itemNum
     * @param orderAmount
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "user_id", required = false) int userId,
                                    @RequestParam(value = "user_name", required = false) String userName,
                                    @RequestParam(value = "user_phone", required = false) String userPhone,
                                    @RequestParam(value = "user_weixin", required = false) String userWeixin,
                                    @RequestParam(value = "item_id", required = false) int itemId,
                                    @RequestParam(value = "item_name", required = false) String itemName,
                                    @RequestParam(value = "item_level_id", required = false) int itemLevelId,
                                    @RequestParam(value = "item_level_name", required = false) String itemLevelName,
                                    @RequestParam(value = "item_num", required = false) int itemNum,
                                    @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount
                                    ){

        if (userId == 0 || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(userWeixin)
                || itemId == 0 || StringUtils.isEmpty(itemName) || itemLevelId == 0 || StringUtils.isEmpty(itemLevelName)
                || itemNum == 0 || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) ){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        OrderCandidate orderCandidate = new OrderCandidate();
        orderCandidate.setUserId(userId);
        orderCandidate.setUserName(userName);
        orderCandidate.setUserPhone(userPhone);
        orderCandidate.setUserWeixin(userWeixin);
        orderCandidate.setItemId(itemId);
        orderCandidate.setItemName(itemName);
        orderCandidate.setItemLevelId(itemLevelId);
        orderCandidate.setItemLevelName(itemLevelName);
        orderCandidate.setItemNum(itemNum);
        orderCandidate.setOrderAmount(orderAmount);
        orderCandidate.setStatus(0);//新增为未处理状态
        orderCandidate.setOperateAccount("");//新增默认为空

        try{
            int i = orderCandidateService.insert(orderCandidate);
            if (i > 0) {
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                return setResult(StatusCode.CANDIDATE_ORDER_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.CANDIDATE_ORDER_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }



}
