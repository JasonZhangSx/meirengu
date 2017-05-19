package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 跳转到订单列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String orderView() throws IOException {
        return "/trade/orderList";
    }

    /**
     * 订单列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput orderList(@Valid DataTablesInput input) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        //查询待支付的订单
        if (queryVo.getOrderState() == null || queryVo.getOrderState() == 4) {
            queryVo.setOrderState(null);
            List<Integer> orderStateList = new ArrayList<Integer>();
            orderStateList.add(2);
            orderStateList.add(4);
            queryVo.setOrderStateList(orderStateList);
        }

        String url = ConfigUtil.getConfig("order.list.url") + "?" + queryVo.getParamsStr();
        Map<String,Object> httpData = null;
        List<Map<String,Object>> list = null;
        int totalCount = 0;
        try {
            httpData = (Map<String,Object>)httpGet(url);
            list = (List<Map<String,Object>>) httpData.get("list");
            totalCount = Integer.parseInt(httpData.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }

    /**
     * 申请退款
     * @param orderId
     * @param userMessage
     * @param refundMessage
     * @return
     */
    @RequestMapping(value = "/refund/application", method = RequestMethod.POST)
    @ResponseBody
    public Result refundApply(@RequestParam(value = "orderId") Integer orderId ,
                              @RequestParam(value = "userMessage") String userMessage,
                              @RequestParam(value = "refundMessage") String refundMessage) {
        if (orderId == null || orderId == 0 || StringUtils.isEmpty(userMessage) || StringUtils.isEmpty(refundMessage)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("refund.application.url");
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_id", orderId.toString());
        params.put("user_message", userMessage);
        params.put("refund_message", refundMessage);
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
            logger.error("throw exception:{}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}
