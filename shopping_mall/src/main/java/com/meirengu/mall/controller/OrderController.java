package com.meirengu.mall.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.common.Constants;
import com.meirengu.mall.common.StatusCode;
import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.OrderService;
import com.meirengu.mall.service.RefundService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private RefundService refundService;

    /**
     * 获取订单列表接口
     * @param userId
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Map<String, Object> list(@RequestParam(value = "user_id", required = false) Integer userId,
                                    @RequestParam(value = "state", required = false) String state,
                                    @RequestParam(value = "per_page", required = false) Integer pageSize,
                                    @RequestParam(value = "page", required = false) Integer pageNum){

        if(null == userId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "user_id"));
        }

        if(null == pageNum){
            pageNum = Constants.PAGE_NUM_DEFAULT;
        }

        if(null == pageSize){
            pageSize = Constants.PAGE_SIZE_DEFAULT;
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("state", state);

        Page<Order> page = super.setPageParams(pageSize, pageNum);

        page = orderService.getPageList(page, paramMap);

        if(page.getList().size() > 0){
            return super.setReturnMsg(StatusCode.STATUS_200, page, StatusCode.STATUS_200_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_501, null, StatusCode.STATUS_501_MSG);
        }

    }


    /**
     * 生成订单接口
     * @param params 传入购物车json参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value="gen", method = RequestMethod.POST)
    public Map<String, Object> genOrder(@RequestParam(value = "param", required = false) String params){
        if(params == null){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "user_id"));
        }
        JSONObject jsonParams = JSONObject.parseObject(params);
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            //用户手机号
            Object userPhone = jsonParams.get("user_phone");

            String cartList = jsonParams.get("cart_list") == null?"":jsonParams.get("cart_list").toString();
            JSONArray array = JSONArray.parseArray(cartList);

            if(array.size() <= 0){
                return super.setReturnMsg(StatusCode.STATUS_4212, null, StatusCode.STATUS_4212_MSG);
            }
            for (int i = 0; i < array.size(); i++ ){
                JSONObject cartObject = (JSONObject) array.get(i);
                double itemPrice = cartObject.get("item_price") == null?0:Double.parseDouble(cartObject.get("item_price").toString());
                int cartId = cartObject.get("cart_id") == null?0:Integer.parseInt(cartObject.get("cart_id").toString());
                int userId = cartObject.get("user_id") == null?0:Integer.parseInt(cartObject.get("user_id").toString());
                int itemNum = cartObject.get("item_num") == null?0:Integer.parseInt(cartObject.get("item_num").toString());
                int itemId = cartObject.get("item_id") == null?0:Integer.parseInt(cartObject.get("item_id").toString());
                int hospitalId = cartObject.get("hospital_id") == null?0:Integer.parseInt(cartObject.get("hospital_id").toString());
                int doctorId = cartObject.get("doctor_id") == null?0:Integer.parseInt(cartObject.get("doctor_id").toString());
                String itemName = cartObject.get("item_name") == null?"":cartObject.get("item_name").toString();
                int orderFrom = cartObject.get("order_from") == null?0:Integer.parseInt(cartObject.get("order_from").toString());
                String itemImage = cartObject.get("item_image") == null?"":cartObject.get("item_image").toString();


                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("userId", userId);
                paramMap.put("itemPrice", itemPrice);
                paramMap.put("itemNum", itemNum);
                paramMap.put("itemId", itemId);
                paramMap.put("hospitalId", hospitalId);
                paramMap.put("doctorId", doctorId);
                paramMap.put("itemName", itemName);
                paramMap.put("userPhone", userPhone);
                paramMap.put("orderFrom", orderFrom);
                paramMap.put("itemImage", itemImage);
                paramMap.put("cartId", cartId);

                list.add(paramMap);
            }
        }catch (Exception e){
            LOGGER.error("request json params error: ", e);
            return super.setReturnMsg(StatusCode.STATUS_4211, null, StatusCode.STATUS_4211_MSG);
        }

        //生成订单服务
        String unionSN = orderService.genOrders(list);
        if(StringUtils.isEmpty(unionSN)){
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        } else {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("unionSN", unionSN);
            return super.setReturnMsg(StatusCode.STATUS_200, returnMap, StatusCode.STATUS_200_MSG);
        }

    }

    /**
     * 订单详情列表接口
     * @param unionSN
     * @return
     */
    @ResponseBody
    @RequestMapping(value="detail", method = RequestMethod.POST)
    public Map<String, Object> detail(@RequestParam(value = "union_sn", required = false) String unionSN){

        if(StringUtils.isEmpty(unionSN)){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "union_sn"));
        }

        List<Map<String, Object>> detailList = orderService.orderDetail(unionSN);

        if(detailList != null){
            if(detailList.size() > 0){
                return super.setReturnMsg(StatusCode.STATUS_200, detailList, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_501, null, StatusCode.STATUS_501_MSG);
            }
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, detailList, StatusCode.STATUS_500_MSG);
        }


    }

    @ResponseBody
    @RequestMapping(value="toRefund", method = RequestMethod.POST)
    public Map<String, Object> toRefund(@RequestParam(value = "order_id", required = false) Integer orderId){

        if(orderId == null){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "order_id"));
        }

        Map<String, Object> orderMap = orderService.getDetailById(orderId);
        if(orderMap == null){
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }else {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("hospitalName",orderMap.get("hospitalName"));
            resultMap.put("doctorName",orderMap.get("doctorName"));
            resultMap.put("orderSN",orderMap.get("orderSN"));
            resultMap.put("itemPrice",orderMap.get("itemPrice"));
            resultMap.put("orderId",orderMap.get("orderId"));
            return super.setReturnMsg(StatusCode.STATUS_200, resultMap, StatusCode.STATUS_200_MSG);
        }

    }


    /**
     * 删除订单接口
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="{order_id}", method = RequestMethod.DELETE)
    public Map<String, Object> refund(@PathVariable(value = "order_id", required = false) Integer orderId){

        if(null == orderId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "order_id"));
        }

        int delOrderNum = orderService.deleteOrder(orderId);
        if(delOrderNum == 1){
            return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
        }else if(delOrderNum == 2){
            return super.setReturnMsg(StatusCode.STATUS_4213, null, StatusCode.STATUS_4213_MSG);
        }else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }


    /**
     * 申请退款接口
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="refund-apply", method = RequestMethod.POST)
    public Map<String, Object> refundApply(@RequestParam(value = "order_id", required = false) Integer orderId,
                                         @RequestParam(value = "user_id", required = false) Integer userId,
                                         @RequestParam(value = "user_message", required = false) String userMessage,
                                         @RequestParam(value = "refund_message", required = false) String refundMessage){

        if(null == orderId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "order_id"));
        }

        if(null == userId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "user_id"));
        }

        if(null == userMessage){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "user_message"));
        }

        // 0 抛出异常  1 添加退款信息失败 2 订单信息不存在  3 修改订单状态失败  4 成功  5 订单状态不能退款
        Map<String, Object> resultMap = refundService.refundApply(orderId, userId, userMessage, refundMessage);
        if(resultMap == null){
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }else{
            String state = resultMap.get("state") == null?"":resultMap.get("state").toString();
            Double price = resultMap.get("price") == null? null : Double.parseDouble(resultMap.get("price").toString());
            if("0".equals(state)){
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }else if("1".equals(state)){
                LOGGER.info("add refund msg fail...");
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }else if("2".equals(state)){
                LOGGER.info("order msg doesn't exist ...");
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }else if("3".equals(state)){
                LOGGER.info("modify order state fail ...");
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }else if("4".equals(state)){
                Map<String, Object> map = new HashMap<>();
                resultMap.put("refundAmount", price);
                return super.setReturnMsg(StatusCode.STATUS_200, map, StatusCode.STATUS_200_MSG);
            }else if("5".equals(state)){
                LOGGER.info("order state doesn't refund ...");
                return super.setReturnMsg(StatusCode.STATUS_4214, null, StatusCode.STATUS_4214_MSG);
            }else{
                LOGGER.info("refund apply return state error : {}", state);
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }
        }

    }
}
