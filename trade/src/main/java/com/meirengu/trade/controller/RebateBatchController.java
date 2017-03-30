package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.RebateTypeEnum;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.service.RebateBatchService;
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
    public Result refundApply(@RequestParam(value = "rebate_type", required = false)int rebateType,
                              @RequestParam(value = "rebate_mark", required = false)int rebateMark,
                              @RequestParam(value = "rebate_name", required = false) String rebateName,
                              @RequestParam(value = "rebate_scope", required = false) String rebateScope,
                              @RequestParam(value = "rebate_amount", required = false) BigDecimal rebateAmount,
                              @RequestParam(value = "rebate_limit", required = false) int rebateLimit,
                              @RequestParam(value = "valid_days", required = false) int validDays,
                              @RequestParam(value = "valid_start_time", required = false) Date validStartTime,
                              @RequestParam(value = "valid_end_time", required = false) Date validEndTime,
                              @RequestParam(value = "valid_type", required = false) int validType,
                              @RequestParam(value = "batch_count", required = false) int batchCount,
                              @RequestParam(value = "channel", required = false) String channel,
                              @RequestParam(value = "budget_amount", required = false) BigDecimal budgetAmount,
                              @RequestParam(value = "remarks", required = false) String remarks,
                              @RequestParam(value = "operate_account", required = false) String operateAccount){

        if (rebateType == 0 || rebateMark == 0 || StringUtils.isEmpty(rebateName) || StringUtils.isEmpty(rebateScope)
                || StringUtils.isEmpty(operateAccount) || rebateAmount == null || rebateAmount.equals(BigDecimal.ZERO) || validType == 0 || batchCount == 0
                || StringUtils.isEmpty(channel) || budgetAmount == null || budgetAmount.equals(BigDecimal.ZERO)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        //如果券类型为满减型，限额字段必须有值
        if (rebateType == RebateTypeEnum.FULL_REBATE.getValue()) {
            if (rebateLimit == 0) {
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
            if (validDays == 0) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }

        //抵扣券批次信息
        RebateBatch rebateBatch = new RebateBatch();
        rebateBatch.setRebateType(rebateType);
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
