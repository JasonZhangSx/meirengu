package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
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
 * 优惠券控制类
 * Created by maoruxin on 2017/3/24.
 */
@RestController
@RequestMapping("/rebate")
public class RebateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateController.class);

    @Autowired
    private RebateBatchService rebateBatchService;

    /**
     * 领取优惠券
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

        if (rebateType == 0 || StringUtils.isEmpty(rebateName) || StringUtils.isEmpty(rebateScope)
                || StringUtils.isEmpty(operateAccount) || rebateAmount == null || rebateAmount.equals(BigDecimal.ZERO) || validType == 0 || batchCount == 0
                || StringUtils.isEmpty(channel) || budgetAmount == null || budgetAmount.equals(BigDecimal.ZERO)
                ){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        //如果券类型为满减型，限额字段必须有值
        if (rebateType == RebateTypeEnum.FULL_REBATE.getValue()) {
            if (rebateLimit == 0) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        //如果有效期判断类型为 1按绝对时间过期 则有效期开始时间，有效期截至时间字段必传
        if (validType == 1) {
            if (validStartTime == null || validEndTime == null) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
            //如果有效期判断类型为 2按相对时间过期 则有效天数字段必传
        } else if (validType == 2) {
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
        rebateBatch.setBatchStatue(1);//默认有效
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

//    /**
//     * 退款审核
//     * @param refundId
//     * @param orderId
//     * @param refundState
//     * @param adminMessage
//     * @return
//     */
//    @RequestMapping(value = "/audit/{refund_id}", method = RequestMethod.POST)
//    public Result refundAudit(@PathVariable("refund_id") int refundId,
//                              @RequestParam(value = "order_id", required = false) int orderId,
//                              @RequestParam(value = "refund_state", required = false) int refundState,
//                              @RequestParam(value = "admin_message", required = false) String adminMessage) {
//
//        if (refundId == 0 || orderId == 0 || refundState == 0 || StringUtils.isEmpty(adminMessage)) {
//            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
//        }
//
//        //退款申请记录表修改信息
//        Refund refund = new Refund();
//        refund.setRefundId(refundId);
//        refund.setAdminMessage(adminMessage);
//        refund.setAdminTime(new Date());
//        refund.setRefundState(refundState);
//
//        //订单表修改信息
//        Order order = new Order();
//        order.setOrderId(orderId);
//        if (refundState == 2) {
//            order.setOrderState(OrderStateEnum.REFUND_CONFIRM.getValue());
//        } else if (refundState == 3) {
//            order.setOrderState(OrderStateEnum.REFUND_REFUSE.getValue());
//        }
//
//        try {
//            Result result = refundService.refundAudit(refund, order);
//            logger.info("Request getResponse: {}", JSON.toJSON(result));
//            return result;
//        } catch (Exception e) {
//            logger.error("throw exception:", e);
//            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
//        }
//    }
//
//    /**
//     * 退款列表
//     * @param pageNum
//     * @param pageSize
//     * @param sortBy
//     * @param order
//     * @param orderSn
//     * @param userId
//     * @param userPhone
//     * @param refundState
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
//                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
//                          @RequestParam(value = "sort_by", required = false) String sortBy,
//                          @RequestParam(value = "order", required = false) String order,
//                          @RequestParam(value = "order_sn", required = false) String orderSn,
//                          @RequestParam(value = "user_id", required = false) String userId,
//                          @RequestParam(value = "user_phone", required = false) String userPhone,
//                          @RequestParam(value = "refund_state", required = false) String refundState){
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderSn", orderSn);
//        map.put("userId", userId);
//        map.put("userPhone", userPhone);
//        map.put("refundState", refundState);
//        map.put("sortBy", sortBy);
//        map.put("order", order);
//        Page<Refund> page = new Page<Refund>();
//        page.setPageNow(pageNum);
//        page.setPageSize(pageSize);
//        try{
//            page = refundService.getListByPage(page, map);
//            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
//        }catch (Exception e){
//            logger.error("throw exception:", e);
//            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
//        }
//    }
        @InitBinder
        public void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

}
