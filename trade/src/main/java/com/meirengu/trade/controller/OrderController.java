package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderRpcException;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.utils.OrderSNUtils;
import com.meirengu.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
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

        if (itemId == 0 || StringUtils.isEmpty(itemName) || itemLevelId == 0 || StringUtils.isEmpty(itemLevelName)
                || itemLevelAmount == null || itemLevelAmount.equals(BigDecimal.ZERO) || itemNum == 0
                || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) || userId == 0
                || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(orderFrom)){
            //备注和微信号选填
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        //根据项目信息请求项目服务查询地址是否必填，校验地址
        Order order = new Order();
        order.setOrderSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_BOOK_SN_PREFIX));
        order.setItemId(itemId);
        order.setItemName(itemName);
        order.setItemLevelId(itemLevelId);
        order.setItemLevelName(itemLevelName);
        order.setItemLevelAmount(itemLevelAmount);
        order.setItemNum(itemNum);
        order.setOrderAmount(orderAmount);
        order.setCostAmount(costAmount);
        order.setRebateAmount(rebateAmount);
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setUserEmail("");//目前没有邮箱，设为空字符
        order.setUserAddressId(userAddressId);
        order.setOrderType(1);//订单类型目前都是普通
        order.setPaymentMethod(0);//支付类型默认为余额支付
        order.setOutSn("");//目前为预扣库存  第三方支付号为空
        order.setFinishedTime(new Date());//目前众筹订单中完成时间字段没有意义
        order.setReceipt("");//目前没有发票
        order.setOrderMessage(orderMessage==null?"":orderMessage);
        order.setOrderState(OrderStateEnum.BOOK.getValue());
        order.setOrderFrom(orderFrom);
        order.setOperateAccount("");//目前后台不生成订单
        order.setUserWeixin(userWeixin==null?"":userWeixin);
        try{
            Result result = orderService.appointment(order);
            return result;
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
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


        if (itemId == 0 || StringUtils.isEmpty(itemName) || itemLevelId == 0 || StringUtils.isEmpty(itemLevelName)
                || itemLevelAmount == null || itemLevelAmount.equals(BigDecimal.ZERO) || itemNum == 0
                || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) || userId == 0
                || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(orderFrom)) {
            //备注和微信号选填
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        //根据项目信息请求项目服务查询地址是否必填，校验地址
        Order order = new Order();
        order.setOrderSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_ORDER_SN_PREFIX));
        order.setItemId(itemId);
        order.setItemName(itemName);
        order.setItemLevelId(itemLevelId);
        order.setItemLevelName(itemLevelName);
        order.setItemLevelAmount(itemLevelAmount);
        order.setItemNum(itemNum);
        order.setOrderAmount(orderAmount);
        order.setCostAmount(costAmount);
        order.setRebateAmount(rebateAmount);
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setUserEmail("");//目前没有邮箱，设为空字符
        order.setUserAddressId(userAddressId);
        order.setOrderType(1);//订单类型目前都是普通
        order.setPaymentMethod(0);//支付类型默认为余额支付
        order.setOutSn("");//目前为预扣库存  第三方支付号为空
        order.setFinishedTime(new Date());//目前众筹订单中完成时间字段没有意义
        order.setReceipt("");//目前没有发票
        order.setOrderMessage(orderMessage==null?"":orderMessage);
        order.setOrderState(OrderStateEnum.UNPAID.getValue());
        order.setOrderFrom(orderFrom);
        order.setOperateAccount("");//目前后台不生成订单
        order.setUserWeixin(userWeixin==null?"":userWeixin);
        try{
            Result result = orderService.insertSubscriptions(order);
            return result;
        } catch (OrderRpcException oe) {
            logger.error("throw OrderRpcException:", oe);
            if (oe.getErrorCode()!= 0 && StatusCode.codeMsgMap.get(oe.getErrorCode()) != null) {
                return setResult(oe.getErrorCode(), null, StatusCode.codeMsgMap.get(oe.getErrorCode()));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
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
    public Result appointmentAudit(@PathVariable("order_id") int orderId ,
                                    @RequestParam(value = "status") int orderStatus){
        if (orderId == 0 || !(orderStatus == OrderStateEnum.BOOK_ADUIT_FAIL.getValue() || orderStatus == OrderStateEnum.BOOK_ADUIT_PASS.getValue())) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(orderStatus);
        try{
            Result result = orderService.appointmentAudit(order);
            return  result;
        }catch (OrderRpcException oe){
            logger.error("throw OrderRpcException:", oe);
            if (oe.getErrorCode()!= 0 && StatusCode.codeMsgMap.get(oe.getErrorCode()) != null) {
                return setResult(oe.getErrorCode(), null, StatusCode.codeMsgMap.get(oe.getErrorCode()));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/{order_id}",  method = RequestMethod.DELETE)
    public Result delete(@PathVariable("order_id") int orderId){
        if (orderId == 0 ) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setFlag(0);//逻辑删除状态 0为删除，1为未删除
        try{
            Result result = orderService.deleteOrder(order);
            return result;
        }catch (Exception e){
            logger.error("throw exception:", e);
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
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "order_sn", required = false) String orderSn,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "item_name", required = false) String itemName,
                          @RequestParam(value = "user_id", required = false) String userId,
                          @RequestParam(value = "order_state", required = false) String orderState,
                          @RequestParam(value = "need_avatar", required = false, defaultValue = "0") int needAvatar,
                          @RequestParam(value = "item_id", required = false, defaultValue = "0") int itemId){
        Map<String, Object> map = new HashMap<>();
        //前台不展示删除订单
        map.put("flag", 1);//逻辑删除状态 0为删除，1为未删除
        map.put("orderSn", orderSn);
        map.put("userPhone", userPhone);
        map.put("itemName", itemName);
        map.put("userId", userId);
        map.put("orderState", orderState);
        map.put("needAvatar", needAvatar);
        if (itemId != 0) {
            map.put("itemId", itemId);
        }
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page<Order> page = new Page<Order>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = orderService.getPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception:", e);
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
    public Result getSystemPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                                @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                                @RequestParam(value = "sort_by", required = false) String sortBy,
                                @RequestParam(value = "order", required = false) String order,
                                @RequestParam(value = "order_sn", required = false) String orderSn,
                                @RequestParam(value = "user_phone", required = false) String userPhone,
                                @RequestParam(value = "item_name", required = false) String itemName,
                                @RequestParam(value = "order_state", required = false) String orderState){
        Map<String, Object> map = new HashMap<>();
        map.put("orderSn", orderSn);
        map.put("userPhone", userPhone);
        map.put("itemName", itemName);
        map.put("orderState", orderState);
        map.put("sortBy", sortBy);
        map.put("order", order);

        Page page = new Page();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = orderService.getSystemPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }


    }
    /**
     * 取消预约订单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "appointment/cancel/{order_id}",  method = RequestMethod.POST)
    public Result appointmentCancel(@PathVariable("order_id") int orderId){
        if (orderId == 0 ) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(OrderStateEnum.LOSS_EFFICACY.getValue());
        try{
            Result result = orderService.appointmentCancel(order);
            return result;
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 订单详情
     * @return
     */
    @RequestMapping(value = "detail/{order_id}",  method = RequestMethod.GET)
    public Result detail(@PathVariable("order_id") int orderId){
        if (orderId == 0 ) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try{
            Map<String, Object> orderDetailMap = orderService.orderDetail(orderId);
            return setResult(StatusCode.OK, orderDetailMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    public static void main(String[] args) throws Exception {
        Set<Integer> sets = new HashSet<Integer>();
        sets.add(514802872);
        sets.add(514802873);
        String addressIdsStr = sets.toString();
        String addressIds = addressIdsStr.substring(addressIdsStr.indexOf("[")+1,addressIdsStr.indexOf("]"));

        String url = "http://192.168.0.135:8084/uc" + Constant.ADDRESS_URL + "?" + URLEncoder.encode("address_id="+addressIds, "UTF-8");;
        HttpUriRequest request = new HttpGet(url);
        System.out.println(request.getURI());
        HttpGet get = new HttpGet("http://192.168.0.135:8084/uc" + Constant.ADDRESS_URL + "?address_id=" + addressIds);
        System.out.println(get.getURI());
        HttpUtil.HttpResult httpResult = HttpUtil.doGet("http://192.168.0.135:8084/uc" + Constant.ADDRESS_URL + "?address_id=" + addressIds);

    }

}
