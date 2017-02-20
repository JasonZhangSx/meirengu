package com.meirengu.mall.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.controller.BaseController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.common.Constants;
import com.meirengu.common.StatusCode;
import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.OrderService;
import com.meirengu.mall.service.RefundService;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 购物车controller
 * @author 建新
 * @create 2017-01-10 19:35
 */
@Controller
@RequestMapping("/order")
@CrossOrigin
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
    public Result list(@RequestParam(value = "user_id", required = false) Integer userId,
                                    @RequestParam(value = "state", required = false) String state,
                                    @RequestParam(value = "per_page", required = false) Integer pageSize,
                                    @RequestParam(value = "page", required = false) Integer pageNum){

        if(null == userId){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
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

        Page<Order> page = new Page<>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        page = orderService.getPageList(page, paramMap);

        if(page.getList().size() > 0){
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }

    }


    /**
     * 生成订单接口
     * @param params 传入购物车json参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value="gen", method = RequestMethod.POST)
    public Result genOrder(@RequestParam(value = "param", required = false) String params){
        if(params == null){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        JSONObject jsonParams = JSONObject.parseObject(params);
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            //用户手机号
            Object userPhone = jsonParams.get("user_phone");

            String cartList = jsonParams.get("cart_list") == null?"":jsonParams.get("cart_list").toString();
            JSONArray array = JSONArray.parseArray(cartList);

            if(array.size() <= 0){
                return super.setResult(50401, null, "至少购买一件商品");
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
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        //生成订单服务
        Map<String, Object> returnMap = orderService.genOrders(list);
        if(returnMap == null){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        } else {
            return super.setResult(StatusCode.OK, returnMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }

    }

    /**
     * 订单详情列表接口
     * @param unionSN
     * @return
     */
    @ResponseBody
    @RequestMapping(value="detail", method = RequestMethod.POST)
    public Result detail(@RequestParam(value = "union_sn", required = false) String unionSN){

        if(StringUtils.isEmpty(unionSN)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        List<Map<String, Object>> detailList = orderService.orderDetail(unionSN);

        if(detailList != null){
            if(detailList.size() > 0){
                return super.setResult(StatusCode.OK, detailList, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, detailList, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }


    }

    @ResponseBody
    @RequestMapping(value="toRefund", method = RequestMethod.POST)
    public Result toRefund(@RequestParam(value = "order_id", required = false) Integer orderId){

        if(orderId == null){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Map<String, Object> orderMap = orderService.getDetailById(orderId);
        if(orderMap == null){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }else {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("hospitalName",orderMap.get("hospitalName"));
            resultMap.put("doctorName",orderMap.get("doctorName"));
            resultMap.put("orderSN",orderMap.get("orderSN"));
            resultMap.put("itemPrice",orderMap.get("itemPrice"));
            resultMap.put("orderId",orderMap.get("orderId"));
            return super.setResult(StatusCode.OK, resultMap, StatusCode.codeMsgMap.get(StatusCode.OK));
        }

    }


    /**
     * 删除订单接口
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="{order_id}", method = RequestMethod.DELETE)
    public Result refund(@PathVariable(value = "order_id", required = false) Integer orderId){

        if(null == orderId){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int delOrderNum = orderService.deleteOrder(orderId);
        if(delOrderNum == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else if(delOrderNum == 2){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 申请退款接口
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="refund-apply", method = RequestMethod.POST)
    public Result refundApply(@RequestParam(value = "order_id", required = false) Integer orderId,
                              @RequestParam(value = "user_id", required = false) Integer userId,
                              @RequestParam(value = "user_message", required = false) String userMessage,
                              @RequestParam(value = "refund_message", required = false) String refundMessage){

        if(null == orderId || null == userId || null == userMessage){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        // 0 抛出异常  1 添加退款信息失败 2 订单信息不存在  3 修改订单状态失败  4 成功  5 订单状态不能退款
        Map<String, Object> resultMap = refundService.refundApply(orderId, userId, userMessage, refundMessage);
        if(resultMap == null){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }else{
            String state = resultMap.get("state") == null?"":resultMap.get("state").toString();
            Double price = resultMap.get("price") == null? null : Double.parseDouble(resultMap.get("price").toString());
            if("0".equals(state)){
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }else if("1".equals(state)){
                LOGGER.info("add refund msg fail...");
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }else if("2".equals(state)){
                LOGGER.info("order msg doesn't exist ...");
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }else if("3".equals(state)){
                LOGGER.info("modify order state fail ...");
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }else if("4".equals(state)){
                Map<String, Object> map = new HashMap<>();
                resultMap.put("refundAmount", price);
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else if("5".equals(state)){
                LOGGER.info("order state doesn't refund ...");
                return super.setResult(50400, null, "该订单状态下不能进行退款操作");
            }else{
                LOGGER.info("refund apply return state error : {}", state);
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }

    }

    /**
     * 支付成功业务接口
     * @param unionSN
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pay_success", method = RequestMethod.POST)
    public Result paySuccess(@RequestParam(value = "union_sn", required = false) String unionSN,
                             @RequestParam(value = "transaction_sn", required = false) String transactionSN,
                             @RequestParam(value = "bank_type", required = false) String bankType,
                             @RequestParam(value = "deviceInfo", required = false) String deviceInfo,
                             @RequestParam(value = "return_msg", required = false) String returnMsg){

        if(StringUtil.isEmpty(unionSN) || StringUtil.isEmpty(transactionSN) || StringUtil.isEmpty(bankType) || StringUtil.isEmpty(returnMsg) || StringUtil.isEmpty(deviceInfo)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int result = orderService.paySuccess(unionSN, transactionSN, bankType, deviceInfo, returnMsg);

        if(result == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 支付失败业务接口
     * @param unionSN
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pay_fail", method = RequestMethod.POST)
    public Result payFail(@RequestParam(value = "union_sn", required = false) String unionSN,
                          @RequestParam(value = "transaction_sn", required = false) String transactionSN,
                          @RequestParam(value = "bank_type", required = false) String bankType,
                          @RequestParam(value = "deviceInfo", required = false) String deviceInfo,
                          @RequestParam(value = "return_msg", required = false) String returnMsg){

        if(StringUtil.isEmpty(unionSN) || StringUtil.isEmpty(transactionSN) || StringUtil.isEmpty(bankType) || StringUtil.isEmpty(returnMsg) || StringUtil.isEmpty(deviceInfo)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int result = orderService.payFail(unionSN, transactionSN, bankType, deviceInfo, returnMsg);

        if(result == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 退款操作（通过/拒绝）
     * @param refundId 退款ID
     * @param operate 1.通过 2.拒绝
     * @return
     */
    @ResponseBody
    @RequestMapping(value="refund-operate", method = RequestMethod.POST)
    public Result refundOperate(@RequestParam(value = "refund_id", required = false) Integer refundId,
                                @RequestParam(value = "operate", required = false) Integer operate){
        if(null == refundId || null == operate){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        LOGGER.info("refund_id="+refundId+" | operate="+operate);
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    @ResponseBody
    @RequestMapping(value="test", method = RequestMethod.POST)
    public void test(){
        Map<String, String> map = new HashMap<>();
        map.put("refund_id", "12");
        map.put("operate", "33");
        HttpUtil.HttpResult hr = HttpUtil.doPostForm("http://localhost:8080/order/refund-operate", map);
        //HttpUtil.HttpResult hr = HttpUtil.doPost("http://localhost:8080/order/refund-operate", map);
        LOGGER.info(JSON.toJSON(hr).toString());
    }
}
