package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.service.RebateBatchService;
import com.meirengu.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券批次控制类
 * Created by maoruxin on 2017/3/23.
 */
@RestController
@RequestMapping("/rebate_batch")
public class RebateBatchController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateBatchController.class);

    @Autowired
    private RebateBatchService rebateBatchService;

    /**
     * 抵扣券批次新增
     * @param rebateType
     * @param rebateName
     * @param rebateScope
     * @param rebateAmount
     * @param rebateLimit
     * @param validDays
     * @param validStartTime
     * @param validEndTime
     * @param validType
     * @param batchCount
     * @param channel
     * @param budgetAmount
     * @param remarks
     * @param operateAccount
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result refundApply(@RequestParam(value = "rebate_type", required = false)Integer rebateType,
                              @RequestParam(value = "rebate_limit_amount", required = false)BigDecimal rebateLimitAmount,
                              @RequestParam(value = "rebate_use_range", required = false)Integer rebateUseRange,
                              @RequestParam(value = "rebate_use_range_value", required = false)Integer rebateUseRangeValue,
                              @RequestParam(value = "rebate_mark", required = false)Integer rebateMark,
                              @RequestParam(value = "rebate_name", required = false) String rebateName,
                              @RequestParam(value = "rebate_scope", required = false) String rebateScope,
                              @RequestParam(value = "rebate_amount", required = false) BigDecimal rebateAmount,
                              @RequestParam(value = "rebate_limit", required = false) Integer rebateLimit,
                              @RequestParam(value = "valid_days", required = false) Integer validDays,
                              @RequestParam(value = "valid_start_time", required = false) Date validStartTime,
                              @RequestParam(value = "valid_end_time", required = false) Date validEndTime,
                              @RequestParam(value = "valid_type", required = false) Integer validType,
                              @RequestParam(value = "batch_count", required = false) Integer batchCount,
                              @RequestParam(value = "channel", required = false) String channel,
                              @RequestParam(value = "budget_amount", required = false) BigDecimal budgetAmount,
                              @RequestParam(value = "remarks", required = false) String remarks,
                              @RequestParam(value = "operate_account", required = false) String operateAccount){

        if (NumberUtil.isNullOrZero(rebateType) || NumberUtil.isNullOrZero(rebateUseRange) || NumberUtil.isNullOrZero(rebateMark)
                || StringUtils.isEmpty(rebateName) || StringUtils.isEmpty(rebateScope) || NumberUtil.isNullOrZero(rebateAmount)
                || NumberUtil.isNullOrZero(rebateLimit) || NumberUtil.isNullOrZero(validType) || NumberUtil.isNullOrZero(batchCount)
                || NumberUtil.isNullOrZero(budgetAmount) || StringUtils.isEmpty(operateAccount) ){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        //如果券类型为满减型，限额字段必须有值
        if (rebateType == Constant.REBATE_TYPE_CONDITIONAL) {
            if (NumberUtil.isNullOrZero(rebateLimitAmount)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        //如果券范围不是所有项目，使用范围值字段必须有值
        if (rebateUseRange != Constant.REBATE_USE_ALL) {
            if (NumberUtil.isNullOrZero(rebateUseRangeValue)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        //如果有效期判断类型为 1按绝对时间过期 则有效期开始时间，有效期截至时间字段必传
        if (validType == Constant.REBATE_EXPIRE_BY_ABSOLUTE_TIME) {
            if (validStartTime == null || validEndTime == null) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
            //如果有效期判断类型为 2按相对时间过期 则有效天数字段必传
        } else if (validType == Constant.REBATE_EXPIRE_BY_RELATIVE_TIME) {
            if (NumberUtil.isNullOrZero(validDays)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        if (NumberUtil.isNullOrZero(rebateLimitAmount)) {
            rebateLimitAmount = new BigDecimal(0);
        }
        if (StringUtils.isEmpty(channel)) {
            channel = "";
        }

        //抵扣券批次信息
        RebateBatch rebateBatch = new RebateBatch();
        rebateBatch.setRebateType(rebateType);
        rebateBatch.setRebateLimitAmount(rebateLimitAmount);
        rebateBatch.setRebateUseRange(rebateUseRange);
        rebateBatch.setRebateUseRangeValue(rebateUseRangeValue);
        rebateBatch.setRebateMark(rebateMark);
        rebateBatch.setRebateName(rebateName);
        rebateBatch.setRebateScope(rebateScope);
        rebateBatch.setRebateAmount(rebateAmount);
        rebateBatch.setRebateLimit(rebateLimit);
        rebateBatch.setValidDays(validDays);
        rebateBatch.setValidStartTime(validStartTime);
        rebateBatch.setValidEndTime(validEndTime);
        rebateBatch.setValidType(validType);
        rebateBatch.setBatchStatue(Constant.YES);//默认有效
        rebateBatch.setBatchCount(batchCount);
        rebateBatch.setChannel(channel);
        rebateBatch.setBudgetAmount(budgetAmount);
        rebateBatch.setRemarks(remarks);
        rebateBatch.setCreateTime(new Date());
        rebateBatch.setOperateAccount(operateAccount);

        try {
            Result result = rebateBatchService.insertRebateBatch(rebateBatch);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        } catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 抵扣券批次列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param batchId
     * @param channel
     * @param batchStatue
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "batch_id", required = false) Integer batchId,
                          @RequestParam(value = "channel", required = false) String channel,
                          @RequestParam(value = "batch_statue", required = false) Integer batchStatue){
        Map<String, Object> map = new HashMap<>();
        map.put("batchId", batchId);
        map.put("channel", channel);
        map.put("batchStatue", batchStatue);
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page<RebateBatch> page = new Page<RebateBatch>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = rebateBatchService.getListByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @RequestMapping(value = "/update/{batch_id}", method = RequestMethod.POST)
    public Result handle(@PathVariable("batch_id") Integer batchId,
                         @RequestParam(value = "status", required = false) Integer status,
                         @RequestParam(value = "operate_account", required = false) String operateAccount){
        if (NumberUtil.isNullOrZero(batchId) || status == null) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        RebateBatch rebateBatch = new RebateBatch();
        rebateBatch.setBatchId(batchId);
        rebateBatch.setBatchStatue(status);
        rebateBatch.setOperateAccount(operateAccount);
        try{
            int i = rebateBatchService.update(rebateBatch);
            if (i == 1) {
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        } catch (Exception e){
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
