package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.service.RebateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券控制类
 * Created by maoruxin on 2017/3/24.
 */
@RestController
@RequestMapping("/rebate")
public class RebateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateController.class);

    @Autowired
    private RebateService rebateService;

    /**
     * 抵扣券列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param rebateId
     * @param rebateSn
     * @param rebateBatchId
     * @param channel
     * @param userId
     * @param userPhone
     * @param receiveStatus
     * @param orderSn
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "rebate_id", required = false) Integer rebateId,
                          @RequestParam(value = "rebate_sn", required = false) String rebateSn,
                          @RequestParam(value = "rebate_batch_id", required = false) Integer rebateBatchId,
                          @RequestParam(value = "channel", required = false) String channel,
                          @RequestParam(value = "user_id", required = false) Integer userId,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "receive_status", required = false) Integer receiveStatus,
                          @RequestParam(value = "order_sn", required = false) String orderSn,
                          @RequestParam(value = "used_time_begin", required = false) Date usedTimeBegin,
                          @RequestParam(value = "used_time_end", required = false) Date usedTimeEnd){
        Map<String, Object> map = new HashMap<>();
        map.put("rebateId", rebateId);
        map.put("rebateSn", rebateSn);
        map.put("rebateBatchId", rebateBatchId);
        map.put("batchChannel", channel);
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        map.put("receiveStatus", receiveStatus);
        map.put("orderSn", orderSn);
        map.put("usedTimeBegin", usedTimeBegin);
        map.put("usedTimeEnd", usedTimeEnd);
        if ("create_time".equals(sortBy)) {
            sortBy = "tb.create_time";
        }
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page page = new Page();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = rebateService.getRebateFullInfoByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
