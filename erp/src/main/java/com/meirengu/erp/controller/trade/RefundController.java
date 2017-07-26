package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.commons.authority.common.enums.OperationTypeEnum;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
 * 退款订单控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/refund")
public class RefundController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RefundController.class);

    /**
     * 跳转到退款审核订单列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String refundView() throws IOException {
        return "/trade/refundList";
    }
    /**
     * 跳转到退款订单列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/received/view", method = RequestMethod.GET)
    public String receivedView() throws IOException {
        return "/trade/refundedList";
    }

    /**
     * 退款订单列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput refundList(@Valid DataTablesInput input,
                                       @RequestParam(value = "refundState", required = false) Integer refundState) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        queryVo.setRefundState(refundState);
        //查询退款待审核状态的订单
        if (queryVo.getRefundState() == null) {
            queryVo.setRefundState(1);
        }

        String url = ConfigUtil.getConfig("refund.list.url") + "?" + queryVo.getParamsStr();
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
     * 退款审核
     * @param refundId
     * @param orderId
     * @param refundState
     * @param adminMessage
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ResponseBody
    public Result refundAudit(@RequestParam(value = "refundId", required = false) int refundId,
                              @RequestParam(value = "orderId", required = false) Integer orderId,
                              @RequestParam(value = "refundState", required = false) Integer refundState,
                              @RequestParam(value = "adminMessage", required = false) String adminMessage) {
        if (refundId == 0 || orderId == 0 || refundState == 0 || StringUtils.isEmpty(adminMessage)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("refund.audit.url") + "/" + refundId;
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_id", orderId.toString());
        params.put("refund_state", refundState.toString());
        params.put("admin_message", adminMessage);
        String userName = getLoginUser().getUserName();
        params.put("operate_account", userName);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer code = jsonObject.getIntValue("code");
                if(code != null && code == StatusCode.OK){
                    //添加操作日志
                    String detailStr = "refund_state|1|" + refundState.toString() + "," +
                            "admin_message| |" + adminMessage + ", " +
                            "operate_account| |" + userName ;
                    addLogOperation("退款订单审核", OperationTypeEnum.UPDATE.getIndex(),refundId + "",detailStr);
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
