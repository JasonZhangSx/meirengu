package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.TradeQuery;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 预约订单控制类
 * Created by maoruxin on 2017/3/30.
 */
@RestController
@RequestMapping("/order_appointment")
public class OrderAppointmentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderAppointmentController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView orderAppointmentList(TradeQuery query) throws IOException {
        ModelAndView mv = new ModelAndView();
        query.setOrderState(1);
        String url = ConfigUtil.getConfig("order.list.url") + "?" + query.getParamsStr();
        Object data = null;
        try {
            data = httpGet(url);
        } catch (Exception e) {
            logger.error("throw exception:", e);
        }
        mv.addObject("page", data);
        mv.addObject("query", query);
        mv.setViewName("/trade/orderAppointmentList");
        return mv;
    }

    /**
     * 预约审核
     * @param orderId
     * @param orderState
     * @return
     */
    @RequestMapping(value = "/{order_id}", method = RequestMethod.POST)
    @ResponseBody
    public Result appointmentAudit(@PathVariable("order_id") Integer orderId ,
                                   @RequestParam(value = "order_state") Integer orderState) {
        if (orderId == null || orderId == 0 || orderState == null) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("order.appointment.audit.url") + "/" + orderId;
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", orderState.toString());
        params.put("operate_account", "admin");//稍后修改
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer code = jsonObject.getIntValue("code");
                if(code != null && code == StatusCode.OK){
                    return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    return setResult(code, null, StatusCode.codeMsgMap.get(code));
                }
            } else {
                return setResult(statusCode, null, StatusCode.codeMsgMap.get(statusCode));
            }
        } catch (Exception e) {
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}
