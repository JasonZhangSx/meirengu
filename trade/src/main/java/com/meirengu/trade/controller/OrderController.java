package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.model.Order;
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
 * 订单控制类
 * Created by maoruxin on 2017/3/14.
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 预约新增接口
     * @param itemId
     * @param itemName
     * @param itemLevelId
     * @param itemLevelName
     * @param itemLevelAmount
     * @param itemNum
     * @param orderAmount
     * @param rebateAmount
     * @param costAmount
     * @param userId
     * @param userName
     * @param userPhone
     * @param userAddressId
     * @param orderMessage
     * @param orderFrom
     * @param userWeixin
     * @return
     */
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

        if(itemId == 0 || StringUtils.isEmpty(itemName) || itemLevelId == 0 || StringUtils.isEmpty(itemLevelName)
                || itemLevelAmount == null || itemLevelAmount.equals(BigDecimal.ZERO) || itemNum == 0
                || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) || userId == 0
                || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(orderFrom)){
            //备注和微信号选填
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));

        }
        //根据项目信息请求项目服务查询地址是否必填，校验地址
        Order order = new Order();
        order.setOrderSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_BOOK_SN_PREFIX));
        order.setItemId(itemId);
        order.setItemName(itemName);
        order.setItemLevelId(itemLevelId);
        order.setItemLevelName(itemLevelName);
        order.setItemLevelAmount(itemLevelAmount.doubleValue());
        order.setItemNum(itemNum);
        order.setOrderAmount(orderAmount.doubleValue());
        order.setCostAmount(costAmount.doubleValue());
        order.setRebateAmount(rebateAmount.doubleValue());
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setUserEmail("");//目前没有邮箱，设为空字符
        order.setUserAddressId(userAddressId);
        order.setOrderType(1);//订单类型目前都是普通
        order.setOutSn("");//目前为预扣库存  第三方支付号为空
        order.setFinishedTime(new Date());//目前众筹订单中完成时间字段没有意义
        order.setReceipt("");//目前没有发票
        order.setOrderMessage(orderMessage==null?"":orderMessage);
        order.setOrderState(OrderStateEnum.BOOK.getValue());
        order.setOrderFrom(orderFrom);
        order.setOperateAccount("");//目前后台不生成订单
        order.setUserWeixin(userWeixin==null?"":userWeixin);
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

    /**
     * 认购新增接口
     * @param itemId
     * @param itemName
     * @param itemLevelId
     * @param itemLevelName
     * @param itemLevelAmount
     * @param itemNum
     * @param orderAmount
     * @param rebateAmount
     * @param costAmount
     * @param userId
     * @param userName
     * @param userPhone
     * @param userAddressId
     * @param orderMessage
     * @param orderFrom
     * @param userWeixin
     * @return
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.POST)
    public Result insertSubscriptions(@RequestParam(value = "item_id", required = false) int itemId,
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
                                      @RequestParam(value = "user_weixin", required = false) String userWeixin
                                    ){


        if(itemId == 0 || StringUtils.isEmpty(itemName) || itemLevelId == 0 || StringUtils.isEmpty(itemLevelName)
                || itemLevelAmount == null || itemLevelAmount.equals(BigDecimal.ZERO) || itemNum == 0
                || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) || userId == 0
                || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(orderFrom)){
            //备注和微信号选填
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));

        }
        //根据项目信息请求项目服务查询地址是否必填，校验地址
        Order order = new Order();
        order.setOrderSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_ORDER_SN_PREFIX));
        order.setItemId(itemId);
        order.setItemName(itemName);
        order.setItemLevelId(itemLevelId);
        order.setItemLevelName(itemLevelName);
        order.setItemLevelAmount(itemLevelAmount.doubleValue());
        order.setItemNum(itemNum);
        order.setOrderAmount(orderAmount.doubleValue());
        order.setCostAmount(costAmount.doubleValue());
        order.setRebateAmount(rebateAmount.doubleValue());
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setUserEmail("");//目前没有邮箱，设为空字符
        order.setUserAddressId(userAddressId);
        order.setOrderType(1);//订单类型目前都是普通
        order.setOutSn("");//目前为预扣库存  第三方支付号为空
        order.setFinishedTime(new Date());//目前众筹订单中完成时间字段没有意义
        order.setReceipt("");//目前没有发票
        order.setOrderMessage(orderMessage==null?"":orderMessage);
        order.setOrderState(OrderStateEnum.UNPAID.getValue());
        order.setOrderFrom(orderFrom);
        order.setOperateAccount("");//目前后台不生成订单
        order.setUserWeixin(userWeixin==null?"":userWeixin);
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
