package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.commons.authority.common.enums.OperationTypeEnum;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.NumberUtil;
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
 * 抵扣券控制类
 */
@Controller
@RequestMapping("/rebate_used")
public class RebateUsedController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateUsedController.class);

    /**
     * 跳转到抵扣券核销列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String orderCandidateView() throws IOException {
        return "/trade/rebateUsedList";
    }

    /**
     * 抵扣券核销列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput rebateUsedList(@Valid DataTablesInput input,
                                           @RequestParam(value = "createTimeBegin", required = false) String createTimeBegin,
                                           @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        queryVo.setCreateTimeBegin(createTimeBegin);
        queryVo.setCreateTimeEnd(createTimeEnd);
        queryVo.setOrderState(6);//核销已支付订单的优惠券
        String url = ConfigUtil.getConfig("rebate.used.list.url") + "?" + queryVo.getParamsStr();
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
     * 核销抵扣券
     * @param rebateUsedId
     * @return
     */
    @RequestMapping(value = "/verifyRebateUsed/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result verifyRebateUsed(@PathVariable("id") Integer rebateUsedId) {
        if (NumberUtil.isNullOrZero(rebateUsedId)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("rebate.used.update.url");
        Map<String, String> params = new HashMap<String, String>();
        params.put("rebate_used_id", rebateUsedId.toString());
        params.put("verify_status", 1 + "");
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer code = jsonObject.getIntValue("code");
                if(code != null && code == StatusCode.OK){
                    //添加操作日志
                    addLogOperation("抵扣券核销", OperationTypeEnum.UPDATE.getIndex(),rebateUsedId.toString(),"verify_status|0|1");
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
