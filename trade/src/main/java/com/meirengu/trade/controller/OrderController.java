package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.rocketmq.RocketmqEvent;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.service.OrderService;
import com.meirengu.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private RedisClient redisClient;

    /**
     * 预约新增接口
     * @param itemId
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
    public Result insertAppointment(@RequestParam(value = "item_id", required = false) Integer itemId,
                                    @RequestParam(value = "item_level_id", required = false) Integer itemLevelId,
                                    @RequestParam(value = "item_level_name", required = false) String itemLevelName,
                                    @RequestParam(value = "item_level_amount", required = false) BigDecimal itemLevelAmount,
                                    @RequestParam(value = "item_num", required = false) Integer itemNum,
                                    @RequestParam(value = "share_hold_rate", required = false, defaultValue = "0.0000") BigDecimal shareHoldRate,
                                    @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount,
                                    @RequestParam(value = "rebate_amount", required = false) BigDecimal rebateAmount,
                                    @RequestParam(value = "cost_amount", required = false) BigDecimal costAmount,
                                    @RequestParam(value = "user_id", required = false) Integer userId,
                                    @RequestParam(value = "user_name", required = false) String userName,
                                    @RequestParam(value = "user_phone", required = false) String userPhone,
                                    @RequestParam(value = "user_address_id", required = false) Integer userAddressId,
                                    @RequestParam(value = "order_message", required = false) String orderMessage,
                                    @RequestParam(value = "order_from", required = false) String orderFrom,
                                    @RequestParam(value = "user_weixin", required = false) String userWeixin,
                                    @RequestParam(value = "rebate_receive_id", required = false) Integer rebateReceiveId,
                                    @RequestParam(value = "token", required = false) String token){

        //验证token
        if (!redisClient.existsObject(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }

        if (NumberUtil.isNullOrZero(itemId) || NumberUtil.isNullOrZero(itemLevelId)
                || StringUtils.isEmpty(itemLevelName)|| NumberUtil.isNullOrZero(itemLevelAmount)
                || NumberUtil.isNullOrZero(itemNum)|| NumberUtil.isNullOrZero(orderAmount)
                || NumberUtil.isNullOrZero(userId)|| StringUtils.isEmpty(userName)
                || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(orderFrom)){
            //备注和微信号选填
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        //根据项目信息请求项目服务查询地址是否必填，校验地址
        Order order = new Order();
        order.setItemId(itemId);
        order.setItemLevelId(itemLevelId);
        order.setItemLevelName(itemLevelName);
        order.setItemLevelAmount(itemLevelAmount);
        order.setItemNum(itemNum);
        order.setShareHoldRate(shareHoldRate);
        order.setOrderAmount(orderAmount);
        order.setCostAmount(costAmount);
        order.setRebateAmount(rebateAmount);
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setUserEmail("");//目前没有邮箱，设为空字符
        order.setUserAddressId(userAddressId);
        order.setOrderType(Constant.ORDER_TYPE_ORDINARY);//订单类型目前都是普通
        order.setPaymentMethod(Constant.PAYMENT_METHOD_BALANCE);//支付类型默认为余额支付
        order.setOutSn("");//目前为预扣库存  第三方支付号为空
        order.setFinishedTime(new Date());//先插入当前时间，支付完成后跟新时间
        order.setReceipt("");//目前没有发票
        order.setOrderMessage(orderMessage==null?"":orderMessage);
        order.setOrderState(OrderStateEnum.BOOK.getValue());
        order.setOrderFrom(orderFrom);
        order.setOperateAccount("");//目前后台不生成订单
        order.setUserWeixin(userWeixin==null?"":userWeixin);
        try{
            Result result = orderService.insertAppointment(order, rebateReceiveId);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        }catch (Exception e){
            logger.error("throw exception:{}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 认购新增接口
     * @param itemId
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
    public Result insertSubscriptions(@RequestParam(value = "item_id", required = false) Integer itemId,
                                      @RequestParam(value = "item_level_id", required = false) Integer itemLevelId,
                                      @RequestParam(value = "item_level_name", required = false) String itemLevelName,
                                      @RequestParam(value = "item_level_amount", required = false) BigDecimal itemLevelAmount,
                                      @RequestParam(value = "item_num", required = false) Integer itemNum,
                                      @RequestParam(value = "share_hold_rate", required = false, defaultValue = "0.0000") BigDecimal shareHoldRate,
                                      @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount,
                                      @RequestParam(value = "rebate_amount", required = false) BigDecimal rebateAmount,
                                      @RequestParam(value = "cost_amount", required = false) BigDecimal costAmount,
                                      @RequestParam(value = "user_id", required = false) Integer userId,
                                      @RequestParam(value = "user_name", required = false) String userName,
                                      @RequestParam(value = "user_phone", required = false) String userPhone,
                                      @RequestParam(value = "user_address_id", required = false) Integer userAddressId,
                                      @RequestParam(value = "order_message", required = false) String orderMessage,
                                      @RequestParam(value = "order_from", required = false) String orderFrom,
                                      @RequestParam(value = "user_weixin", required = false) String userWeixin,
                                      @RequestParam(value = "rebate_receive_id", required = false) Integer rebateReceiveId,
                                      @RequestParam(value = "payment_method", required = false)Integer paymentMethod,
                                      @RequestParam(value = "token", required = false) String token){

        //验证token
        if (!redisClient.existsObject(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }

        if (NumberUtil.isNullOrZero(itemId) || NumberUtil.isNullOrZero(itemLevelId)
                || StringUtils.isEmpty(itemLevelName) || NumberUtil.isNullOrZero(itemLevelAmount)
                || NumberUtil.isNullOrZero(itemNum) || NumberUtil.isNullOrZero(orderAmount)
                || NumberUtil.isNullOrZero(userId)  || StringUtils.isEmpty(userName)
                || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(orderFrom)) {
            //备注和微信号选填
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        //根据项目信息请求项目服务查询地址是否必填，校验地址
        Order order = new Order();
        order.setItemId(itemId);
        order.setItemLevelId(itemLevelId);
        order.setItemLevelName(itemLevelName);
        order.setItemLevelAmount(itemLevelAmount);
        order.setItemNum(itemNum);
        order.setShareHoldRate(shareHoldRate);
        order.setOrderAmount(orderAmount);
        order.setCostAmount(costAmount);
        order.setRebateAmount(rebateAmount);
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setUserEmail("");//目前没有邮箱，设为空字符
        order.setUserAddressId(userAddressId);
        order.setOrderType(Constant.ORDER_TYPE_ORDINARY);//订单类型目前都是普通
        order.setPaymentMethod(paymentMethod);
        order.setOutSn("");//目前为预扣库存  第三方支付号为空
        order.setFinishedTime(new Date());//先插入当前时间，支付完成后跟新时间
        order.setReceipt("");//目前没有发票
        order.setOrderMessage(orderMessage==null?"":orderMessage);
        order.setOrderState(OrderStateEnum.UNPAID.getValue());
        order.setOrderFrom(orderFrom);
        order.setOperateAccount("");//目前后台不生成订单
        order.setUserWeixin(userWeixin==null?"":userWeixin);
        try{
            Result result = orderService.insertSubscriptions(order, rebateReceiveId);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        } catch (OrderException oe) {
            logger.error("throw OrderException: {}", oe);
            if (oe.getErrorCode()!= null && StatusCode.codeMsgMap.get(oe.getErrorCode()) != null) {
                return setResult(oe.getErrorCode(), null, StatusCode.codeMsgMap.get(oe.getErrorCode()));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 预约审核
     * @param orderId
     * @param orderStatus
     * @return
     */
    @RequestMapping(value = "/appointment/audit/{order_id}",  method = RequestMethod.POST)
    public Result appointmentAudit(@PathVariable("order_id") Integer orderId,
                                    @RequestParam(value = "status") Integer orderStatus,
                                    @RequestParam(value = "operate_account") String operateAccount){
        if (NumberUtil.isNullOrZero(orderId) || NumberUtil.isNullOrZero(orderStatus) || !(orderStatus == OrderStateEnum.BOOK_ADUIT_FAIL.getValue() || orderStatus == OrderStateEnum.BOOK_ADUIT_PASS.getValue())) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(orderStatus);
        order.setOperateAccount(operateAccount);
        try{
            Result result = orderService.appointmentAudit(order);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return  result;
        }catch (OrderException oe){
            logger.error("throw OrderException: {}", oe);
            if (oe.getErrorCode()!= null && StatusCode.codeMsgMap.get(oe.getErrorCode()) != null) {
                return setResult(oe.getErrorCode(), null, StatusCode.codeMsgMap.get(oe.getErrorCode()));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/{order_id}",  method = RequestMethod.POST)
    public Result delete(@PathVariable("order_id") Integer orderId,
                         @RequestParam(value = "token", required = false) String token){

        //验证token
        if (!redisClient.existsObject(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }

        if (NumberUtil.isNullOrZero(orderId)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setFlag(Constant.DELETE);//逻辑删除状态 0为删除，1为未删除
        try{
            Result result = orderService.deleteOrder(order);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 根据条件查询订单
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param orderSn
     * @param userPhone
     * @param itemName
     * @param userId
     * @param orderState
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "order_sn", required = false) String orderSn,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "item_name", required = false) String itemName,
                          @RequestParam(value = "user_id", required = false) Integer userId,
                          @RequestParam(value = "order_state", required = false) Integer orderState,
                          @RequestParam(value = "need_avatar", required = false, defaultValue = "0") Integer needAvatar,
                          @RequestParam(value = "item_id", required = false, defaultValue = "0") Integer itemId){
        //如果需要头像，则必传项目ID，因为此时需要去获取项目的最新状态，查出该项目的订单
        if (needAvatar != 0 ) {
            if (NumberUtil.isNullOrZero(itemId)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        Map<String, Object> map = new HashMap<>();
        //前台不展示删除订单
        map.put("flag", Constant.NOT_DELETE);//逻辑删除状态 0为删除，1为未删除
        if (StringUtils.isNotBlank(orderSn)) {
            map.put("orderSn", orderSn);
        }
        if (StringUtils.isNotBlank(userPhone)) {
            map.put("userPhone", userPhone);
        }
        if (StringUtils.isNotBlank(itemName)) {
            map.put("itemName", itemName);
        }
        if (!NumberUtil.isNullOrZero(userId)) {
            map.put("userId", userId);
        }
        if (!NumberUtil.isNullOrZero(orderState)) {
            map.put("orderState", orderState);
        }
        if (needAvatar != null) {
            map.put("needAvatar", needAvatar);
        }
        if (!NumberUtil.isNullOrZero(itemId)) {
            map.put("itemId", itemId);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            map.put("sortBy", sortBy);
        }
        if (StringUtils.isNotBlank(order)) {
            map.put("order", order);
        }
        Page<Order> page = new Page<Order>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = orderService.getPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 后台订单列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param orderSn
     * @param userPhone
     * @param itemName
     * @param orderState
     * @return
     */
    @RequestMapping(value = "/system", method = RequestMethod.GET)
    public Result getSystemPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "sort_by", required = false) String sortBy,
                                @RequestParam(value = "order", required = false) String order,
                                @RequestParam(value = "order_sn", required = false) String orderSn,
                                @RequestParam(value = "user_phone", required = false) String userPhone,
                                @RequestParam(value = "item_name", required = false) String itemName,
                                @RequestParam(value = "order_state", required = false) Integer orderState,
                                @RequestParam(value = "order_state_list", required = false) List<Integer> orderStateList){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(orderSn)) {
            map.put("orderSn", orderSn);
        }
        if (StringUtils.isNotBlank(userPhone)) {
            map.put("userPhone", userPhone);
        }
        if (StringUtils.isNotBlank(itemName)) {
            map.put("itemName", itemName);
        }
        if (!NumberUtil.isNullOrZero(orderState)) {
            map.put("orderState", orderState);
        }
        if (orderStateList != null) {
            map.put("orderStateList", orderStateList);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            map.put("sortBy", sortBy);
        }
        if (StringUtils.isNotBlank(order)) {
            map.put("order", order);
        }
        Page page = new Page();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = orderService.getSystemPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    /**
     * 取消预约订单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "appointment/cancel/{order_id}",  method = RequestMethod.POST)
    public Result appointmentCancel(@PathVariable("order_id") Integer orderId,
                                    @RequestParam(value = "token", required = false) String token){

        //验证token
        if (!redisClient.existsObject(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }

        if (!NumberUtil.isNullOrZero(orderId)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(OrderStateEnum.LOSS_EFFICACY.getValue());
        try{
            Result result = orderService.appointmentCancel(order);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 订单详情
     * @return
     */
    @RequestMapping(value = "detail/{order_id}",  method = RequestMethod.GET)
    public Result detail(@PathVariable("order_id") Integer orderId){
        if (NumberUtil.isNullOrZero(orderId)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try{
            Map<String, Object> orderDetailMap = orderService.orderDetail(orderId);
            return setResult(StatusCode.OK, orderDetailMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 订单详情(后台)
     * @return
     */
    @RequestMapping(value = "/system/detail/{order_id}",  method = RequestMethod.GET)
    public Result systemDetail(@PathVariable("order_id") Integer orderId){
        if (NumberUtil.isNullOrZero(orderId)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try{
            Map<String, Object> orderDetailMap = orderService.systemOrderDetail(orderId);
            return setResult(StatusCode.OK, orderDetailMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 订单详情
     * @return
     */
    @RequestMapping(value = "detailsn/{order_sn}",  method = RequestMethod.GET)
    public Result detail(@PathVariable("order_sn") String orderSn){
        if (StringUtils.isEmpty(orderSn)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try{
            Map<String, Object> orderDetailMap = orderService.orderDetailBySn(orderSn);
            return setResult(StatusCode.OK, orderDetailMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 查询用户某个档位已购次数
     * @param itemLevelId
     * @param userId
     * @return
     */
    @RequestMapping(value = "user",  method = RequestMethod.GET)
    public Result user(@RequestParam(value = "item_level_id", required = false) Integer itemLevelId,
                       @RequestParam(value = "user_id", required = false) Integer userId){
        if (NumberUtil.isNullOrZero(itemLevelId) || NumberUtil.isNullOrZero(userId)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("itemLevelId", itemLevelId);
        paramMap.put("userId", userId);
        //预约、审核通过，待支付，已支付
        List<Integer> orderStateList = new ArrayList<Integer>();
        orderStateList.add(OrderStateEnum.BOOK.getValue());
        orderStateList.add(OrderStateEnum.BOOK_ADUIT_PASS.getValue());
        orderStateList.add(OrderStateEnum.UNPAID.getValue());
        orderStateList.add(OrderStateEnum.PAID.getValue());
        paramMap.put("orderStateList", orderStateList);
        try {
            int count = orderService.getHasPurchaseCount(paramMap);
            return setResult(StatusCode.OK, count, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (Exception e) {
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }


    /**
     * 订单支付回调
     * @param orderSn
     * @param paymentMethod
     * @param outSn
     * @return
     */
    @RequestMapping(value = "payment",  method = RequestMethod.POST)
    public Result paymentCallBack(@RequestParam(value = "order_sn", required = false)String orderSn,
                                  @RequestParam(value = "payment_method", required = false)Integer paymentMethod,
                                  @RequestParam(value = "out_sn", required = false)String outSn) {
        logger.debug("支付成功回调订单号：{} 支付方式 {} 第三方支付号 {}", orderSn, paymentMethod, outSn);
        if (StringUtils.isEmpty(orderSn)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderSn(orderSn);
        if (paymentMethod != null) {
            order.setPaymentMethod(paymentMethod);
        }
        if (StringUtils.isNotBlank(outSn)) {
            order.setOutSn(outSn);
        }
        order.setFinishedTime(new Date());
        order.setOrderState(OrderStateEnum.PAID.getValue());
        try {
            orderService.paymentCallBack(order);
            return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (Exception e) {
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 生成3天订单txt文件
     */
    @RequestMapping(value = "/generate_order_txt")
    public Result  generateOrderTxt() {
        try {
            orderService.generateOrderTxt();
            return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (IOException ie) {
            logger.error("throw IOException: {}", ie);
            ie.printStackTrace();
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }  catch (Exception e) {
            logger.error("throw exception: {}", e);
            e.printStackTrace();
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 根据用户id查询用户投资金额
     * 目前不支持批量查询，后期修改
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/get_sum_amount_by_user_id")
    public Result getSumAmountByUserIds(@RequestParam(value = "user_ids", required = true) String userIds) {
        if (StringUtils.isEmpty(userIds)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try {
            List<Map<String, Object>> sumAmountMap = orderService.getSumAmountByUserIds(userIds);
            return setResult(StatusCode.OK, sumAmountMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (Exception e) {
            logger.error("throw exception: {}", e);
            e.printStackTrace();
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    @RequestMapping(value = "/getOrderInfoList", method = RequestMethod.GET)
    public Result getSystemPage(@RequestParam(value = "order_sn", required = false) String orderSn,
                                @RequestParam(value = "user_phone", required = false) String userPhone,
                                @RequestParam(value = "item_id", required = false) Integer itemId,
                                @RequestParam(value = "order_state", required = false) Integer orderState,
                                @RequestParam(value = "item_level_id", required = false) Integer itemLevelId){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(orderSn)) {
            map.put("orderSn", orderSn);
        }
        if (StringUtils.isNotBlank(userPhone)) {
            map.put("userPhone", userPhone);
        }
        if (NumberUtil.isNullOrZero(itemId)) {
            map.put("itemId", itemId);
        }
        if (!NumberUtil.isNullOrZero(orderState)) {
            map.put("orderState", orderState);
        }
        if (!NumberUtil.isNullOrZero(itemLevelId)) {
            map.put("itemLevelId", itemLevelId);
        }
        try{
            List<Map<String, Object>> list = orderService.getList(map);
            return setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 通过档位ID查询该档位下的投资金额
     * @param levelIds
     * @return
     */
    @RequestMapping(value = "/get_sum_amount_by_level_ids")
    public Result getSumAmountByLevelIds(@RequestParam(value = "level_ids", required = false) String levelIds,
                                         @RequestParam(value = "item_ids", required = false) String itemIds) {
        if (StringUtils.isEmpty(levelIds)&&StringUtils.isEmpty(itemIds)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try {
            List<Map<String, Object>> sumAmountMap = orderService.getSumAmountByLevelIds(levelIds,itemIds);
            return setResult(StatusCode.OK, sumAmountMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (Exception e) {
            logger.error("throw exception: {}", e);
            e.printStackTrace();
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 手动订单失效消息
     * @return
     */
    @RequestMapping(value = "/order_loss_efficacy")
    public void orderLoseEfficacy(@RequestParam(value = "order_sn", required = true) String orderSn) throws Exception {
        logger.info("订单置失效");
        orderService.orderLoseEfficacy(orderSn);
    }

    /**
     * 订单失效消息监听
     * @return
     */
    @org.springframework.context.event.EventListener(condition = "#event.topic=='trade' && #event.tag=='orderLoseEfficacy'")
    public void listenOrderLoseEfficacy(RocketmqEvent event) throws Exception {
        logger.info("listenOrderLoseEfficacy: {}", event.getMsg());
        String orderSn = event.getMsg();
        //TODO 进行业务处理
        orderService.orderLoseEfficacy(orderSn);
    }

    /**
     * 订单失效前提醒消息监听
     * @return
     */
    @org.springframework.context.event.EventListener(condition = "#event.topic=='trade' && #event.tag=='orderRemindForPay'")
    public void listenOrderRemindForPay(RocketmqEvent event) throws Exception {
        logger.info("listenOrderRemindForPay: {}", event.getMsg());
        String orderSn = event.getMsg();
        orderService.orderRemindForPay(orderSn);
    }


}
