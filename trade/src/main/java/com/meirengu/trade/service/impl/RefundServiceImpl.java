package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Refund;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.service.RefundService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Refund服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund> implements RefundService{

    @Autowired
    private OrderService orderService;

    /**
     * 退款申请
     * @param refund
     * @param order
     * @return
     */
    public Result refundApply(Refund refund, Order order) throws Exception{
        Result result = new Result();
        int i = insert(refund);
        int j = orderService.update(order);
        if (i == 1 && j == 1 ) {
            result.setCode(StatusCode.OK);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            return result;
        } else {
            throw new Exception();
        }
    }

    /**
     * 退款审核
     * @param refund
     * @param order
     * @return
     */
    public Result refundAudit(Refund refund, Order order) throws Exception{
        Result result = new Result();
        int i = update(refund);
        int j = orderService.update(order);
        if (i == 1 && j == 1 ) {
            result.setCode(StatusCode.OK);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            return result;
        } else {
            throw new Exception();
        }
    }
}
