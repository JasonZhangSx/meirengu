package com.meirengu.trade.controller;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.model.OrderCandidate;
import com.meirengu.trade.rocketmq.Consumer;
import com.meirengu.trade.rocketmq.Producer;
import com.meirengu.trade.service.OrderCandidateService;
import com.meirengu.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private Consumer consumer;


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
    public Result insertCandidate(@RequestParam(value = "user_id", required = false) int userId,
                          @RequestParam(value = "user_name", required = false) String userName,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "user_weixin", required = false) String userWeixin,
                          @RequestParam(value = "item_id", required = false) int itemId,
                          @RequestParam(value = "item_level_id", required = false) int itemLevelId,
                          @RequestParam(value = "item_level_name", required = false) String itemLevelName,
                          @RequestParam(value = "item_num", required = false) int itemNum,
                          @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount,
                          @RequestParam(value = "token", required = false) String token){

        //验证密码
        if (!TokenUtils.authToken(token)) {
            return setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }

        if (userId == 0 || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(userWeixin)
                || itemId == 0  || itemLevelId == 0 || StringUtils.isEmpty(itemLevelName)
                || itemNum == 0 || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) ){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        OrderCandidate orderCandidate = new OrderCandidate();
        orderCandidate.setUserId(userId);
        orderCandidate.setUserName(userName);
        orderCandidate.setUserPhone(userPhone);
        orderCandidate.setUserWeixin(userWeixin);
        orderCandidate.setItemId(itemId);
        orderCandidate.setItemName("待会改为从项目服务获取");
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
            logger.error("throw exception:", e);
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
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @RequestMapping(value = "/handle/{id}", method = RequestMethod.POST)
    public Result handle(@PathVariable("id") int id,
                         @RequestParam(value = "status") int status,
                         @RequestParam(value = "operate_account") String operateAccount){
        if (id == 0) {
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
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }


    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Result sendMessage(@RequestParam(value = "content")String content){

        Message msg = new Message("deploy", "MyTag", content.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = producer.getDefaultMQProducer().send(msg);
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
        }
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
