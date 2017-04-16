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
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 预约订单控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/order_appointment")
public class OrderAppointmentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderAppointmentController.class);

    /**
     * 跳转到预约列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String orderAppointmentView() throws IOException {
        return "/trade/orderAppointmentList";
    }

    /**
     * 预约列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataTablesOutput orderAppointmentList(@Valid @RequestBody DataTablesInput input) throws IOException {
        // 组装请求参数
        TradeQuery tradeQuery = new TradeQuery(input);
        //查询预约状态的订单
        tradeQuery.setOrderState(1);

        String url = ConfigUtil.getConfig("order.list.url") + "?" + tradeQuery.getParamsStr();
        Map<String,Object> httpData = null;
        List<Map<String,Object>> list = null;
        int totalCount = 0;
        try {
            httpData = (Map<String,Object>)httpGet(url);
            list = (List<Map<String,Object>>) httpData.get("list");
            totalCount = Integer.parseInt(httpData.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:", e);
        }
        return setDataTablesOutput(input, list, totalCount);
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
