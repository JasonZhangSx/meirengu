package com.meirengu.trade.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.service.RebateService;
import com.meirengu.trade.service.RebateUsedService;
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
@RequestMapping("/rebate_used")
public class RebateUsedController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateUsedController.class);

    @Autowired
    private RebateUsedService rebateUsedService;

    /**
     * 抵扣券核销列表
      * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param rebateSn
     * @param rebateBatchId
     * @param userId
     * @param userPhone
     * @param orderSn
     * @param orderState
     * @param verifyStatus
     * @param createTimeBegin
     * @param createTimeEnd
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "rebate_sn", required = false) String rebateSn,
                          @RequestParam(value = "batch_id", required = false) Integer rebateBatchId,
                          @RequestParam(value = "user_id", required = false) Integer userId,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "order_sn", required = false) String orderSn,
                          @RequestParam(value = "order_state", required = false) String orderState,
                          @RequestParam(value = "verify_status", required = false) Integer verifyStatus,
                          @RequestParam(value = "create_time_begin", required = false) Date createTimeBegin,
                          @RequestParam(value = "create_time_end", required = false) Date createTimeEnd){
        Map<String, Object> map = new HashMap<>();
        map.put("rebateSn", rebateSn);
        map.put("rebateBatchId", rebateBatchId);
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        map.put("orderSn", orderSn);
        map.put("orderState", orderState);
        map.put("verifyStatus", verifyStatus);
        map.put("createTimeBegin", createTimeBegin);
        map.put("createTimeEnd", createTimeEnd);
        if ("create_time".equals(sortBy)) {
            sortBy = "tb.create_time";
        }
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page page = new Page();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = rebateUsedService.getVerifyInfoByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 更新抵扣券使用记录
     * @param id
     * @param verifyStatus
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateRebateUsed(@RequestParam(value = "rebate_used_id", required = false) Integer id,
                                   @RequestParam(value = "verify_status", required = false) Integer verifyStatus){
        try{
            rebateUsedService.updateRebateUsed(id, verifyStatus);
            return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
