package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.service.RebateReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券领取控制类
 * Created by maoruxin on 2017/3/24.
 */
@RestController
@RequestMapping("/rebate_receive")
public class RebateReceiveController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateReceiveController.class);

    @Autowired
    private RebateReceiveService rebateReceiveService;

    /**
     * 领取优惠券
     * @param userId
     * @param userPhone
     * @param batchIdList
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result receiveRebate(@RequestParam(value = "user_id", required = false)int userId,
                              @RequestParam(value = "user_phone", required = false)String userPhone,
                              @RequestParam(value = "batch_ids", required = false)List<Integer> batchIdList,
                              @RequestParam(value = "activity_identification", required = false)String activityIdentification){

        if (userId == 0 || batchIdList == null || batchIdList.size() == 0){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        try {
            Result result = rebateReceiveService.receiveRebate(userId, userPhone, batchIdList ,activityIdentification);
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

    /**
     * 抵扣券列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param userId
     * @param userPhone
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
                          @RequestParam(value = "status", required = false) int status){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        if (status != 0) {
            map.put("status", status);
        }
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page<RebateReceive> page = new Page<RebateReceive>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = rebateReceiveService.getListByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
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
