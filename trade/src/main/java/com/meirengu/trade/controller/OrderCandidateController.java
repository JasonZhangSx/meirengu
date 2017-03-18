package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.model.OrderCandidate;
import com.meirengu.trade.service.OrderCandidateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "user_id", required = false) String userId,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "item_name", required = false) String itemName,
                          @RequestParam(value = "state", required = false) String state){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        map.put("itemName", itemName);
        map.put("state", state);
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

    public static void main(String[] args) throws Exception{
        Set<Integer> set = new HashSet<Integer>();
        set.add(33);
        set.add(66);
        set.add(88);
        System.out.println("转换前："+set.toString());
        String str = StringUtils.join(set.toArray(), ";");
        System.out.println("特殊数组转换："+str);


    }


}
