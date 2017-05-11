package com.meirengu.trade.controller;

import com.meirengu.utils.NumberUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.model.OrderCandidate;
import com.meirengu.trade.service.OrderCandidateService;
import com.meirengu.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import com.meirengu.rocketmq.Producer;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 候补订单控制类
 * Created by maoruxin on 2017/3/14.
 */
@RestController
@RequestMapping("/order_candidate")
public class OrderCandidateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderCandidateController.class);

    @Autowired
    private OrderCandidateService orderCandidateService;

    @Autowired
    private Producer producer;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 候补预约新增接口
     * @param userId
     * @param userName
     * @param userPhone
     * @param userWeixin
     * @param itemId
     * @param itemLevelId
     * @param itemLevelName
     * @param itemNum
     * @param orderAmount
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result insertCandidate(@RequestParam(value = "user_id", required = false) Integer userId,
                          @RequestParam(value = "user_name", required = false) String userName,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "user_weixin", required = false) String userWeixin,
                          @RequestParam(value = "item_id", required = false) Integer itemId,
                          @RequestParam(value = "item_level_id", required = false) Integer itemLevelId,
                          @RequestParam(value = "item_level_name", required = false) String itemLevelName,
                          @RequestParam(value = "item_num", required = false) Integer itemNum,
                          @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount,
                          @RequestParam(value = "token", required = false) String token){

        //验证密码
        if (!TokenUtils.authToken(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }

        if (NumberUtil.isNullOrZero(userId) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone)
                || StringUtils.isEmpty(userWeixin) || NumberUtil.isNullOrZero(itemId) || NumberUtil.isNullOrZero(itemLevelId)
                || StringUtils.isEmpty(itemLevelName) || NumberUtil.isNullOrZero(itemNum) || NumberUtil.isNullOrZero(orderAmount)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        OrderCandidate orderCandidate = new OrderCandidate();
        orderCandidate.setUserId(userId);
        orderCandidate.setUserName(userName);
        orderCandidate.setUserPhone(userPhone);
        orderCandidate.setUserWeixin(userWeixin);
        orderCandidate.setItemId(itemId);
        orderCandidate.setItemName("");
        orderCandidate.setItemLevelId(itemLevelId);
        orderCandidate.setItemLevelName(itemLevelName);
        orderCandidate.setItemNum(itemNum);
        orderCandidate.setOrderAmount(orderAmount);
        orderCandidate.setStatus(Constant.NO);//新增为未处理状态
        orderCandidate.setOperateAccount("");//新增默认为空

        try{
            int i = orderCandidateService.insertCandidate(orderCandidate);
            if (i > 0) {
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                return setResult(StatusCode.CANDIDATE_ORDER_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.CANDIDATE_ORDER_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 候补预约列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param userId
     * @param userPhone
     * @param itemName
     * @param status
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "user_id", required = false) String userId,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "item_name", required = false) String itemName,
                          @RequestParam(value = "status", required = false) Integer status){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        map.put("itemName", itemName);
        map.put("status", status);
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page<OrderCandidate> page = new Page<OrderCandidate>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = orderCandidateService.getListByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 预约订单处理
     * @param id
     * @param status
     * @param operateAccount
     * @return
     */
    @RequestMapping(value = "/handle/{id}", method = RequestMethod.POST)
    public Result handle(@PathVariable("id") Integer id,
                         @RequestParam(value = "status") Integer status,
                         @RequestParam(value = "operate_account") String operateAccount){
        if (NumberUtil.isNullOrZero(id) || NumberUtil.isNullOrZero(status)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        OrderCandidate orderCandidate = new OrderCandidate();
        orderCandidate.setId(id);
        orderCandidate.setStatus(status);
        orderCandidate.setOperateAccount(operateAccount);
        try{
            int i = orderCandidateService.update(orderCandidate);
            if (i == 1) {
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                return setResult(StatusCode.ORDER_CANDIDATE_HANDLE_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.ORDER_CANDIDATE_HANDLE_ERROR));
            }
        } catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }


    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Result sendMessage(@RequestParam(value = "content")String content) throws Exception{

        Message msg = new Message("deploy", "orderRemindForPay", content.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = producer.getDefaultMQProducer().send(msg);
            logger.info(sendResult+"");
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
        }
//        producer.getDefaultMQProducer().send(msg, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                logger.info("sendResult: {}, key: {}", sendResult, key);
//            }
//
//            @Override
//            public void onException(Throwable e) {
//                logger.error("发送消息失败 key: {}, Exception: {}",  key, e);
//            }
//        });
        return new Result();

    }

    @RequestMapping(value = "/auth_token", method = RequestMethod.POST)
    public Result authToken(HttpServletRequest request,
                            @RequestParam(value = "key")String key){
        String token = request.getHeader("token");
        if (TokenUtils.authToken(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }
        return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }


}
