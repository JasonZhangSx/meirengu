package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.common.Constants;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抵扣券批次控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/rebate_batch")
public class RebateBatchController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RebateBatchController.class);

    /**
     * 跳转到抵扣券批次页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String orderCandidateView() throws IOException {
        return "/trade/rebateBatchList";
    }

    /**
     * 抵扣券批次新增
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String bulletinAddView() throws IOException {
        return "/trade/rebateBatchAdd";
    }

    /**
     * 抵扣券批次列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput rebateBatchList(@Valid DataTablesInput input) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);

        String url = ConfigUtil.getConfig("rebate.batch.list.url") + "?" + queryVo.getParamsStr();
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
     * 新增抵扣券批次
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/handle/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result handle(@PathVariable("id") Integer id ,
                         @RequestParam(value = "status") Integer status) {
        if (id == null || id == 0 || status == null) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("order.candidate.handle.url") + "/" + id;
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", status.toString());
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

    /**
     * 抵扣券批次新增
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(@RequestParam String rebateName,
                         @RequestParam String rebateScope,
                         @RequestParam Integer rebateType,
                         @RequestParam BigDecimal rebateLimitAmount,
                         @RequestParam Integer rebateMark,
                         @RequestParam BigDecimal rebateAmount,
                         @RequestParam Integer rebateUseRange,
                         @RequestParam Integer rebateUseRangeValue,
                         @RequestParam Integer rebateLimit,
                         @RequestParam Integer batchCount,
                         @RequestParam Integer validType,
                         @RequestParam String validStartTime,
                         @RequestParam String validEndTime,
                         @RequestParam Integer validDays,
                         @RequestParam BigDecimal budgetAmount,
                         @RequestParam String channel,
                         @RequestParam String remarks) {
        if (StringUtils.isEmpty(rebateName) || StringUtils.isEmpty(rebateScope) || NumberUtil.isNullOrZero(rebateType)
                || NumberUtil.isNullOrZero(rebateMark) || NumberUtil.isNullOrZero(rebateAmount) || NumberUtil.isNullOrZero(rebateUseRange)
                || NumberUtil.isNullOrZero(rebateLimit) || NumberUtil.isNullOrZero(batchCount) || NumberUtil.isNullOrZero(validType)
                || NumberUtil.isNullOrZero(budgetAmount)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        // 满减型券校验限额
        if (rebateType == Constants.REBATE_TYPE_CONDITIONAL) {
            if (NumberUtil.isNullOrZero(rebateLimitAmount)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        // 限制类型为固定项目和某类项目时，校验限制类型值
        if (rebateUseRange == Constants.REBATE_USE_SPECIFIC || rebateUseRange == Constants.REBATE_USE_CATEGORY) {
            if (NumberUtil.isNullOrZero(rebateUseRangeValue)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        // 有效期值校验
        if (validType == Constants.REBATE_EXPIRE_BY_ABSOLUTE_TIME) {
            if (validStartTime == null || validEndTime == null) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        } else if (validType == Constants.REBATE_EXPIRE_BY_RELATIVE_TIME) {
            if (NumberUtil.isNullOrZero(validDays)) {
                return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        String url = ConfigUtil.getConfig("news.bulletin.insert");
        Map<String, String> params = new HashMap<String, String>();
        params.put("rebateName", rebateName);
        params.put("rebateScope", rebateScope);
        params.put("rebateType", rebateType.toString());
        params.put("rebateLimitAmount", rebateLimitAmount.toString());
        params.put("rebateMark", rebateMark.toString());
        params.put("rebateAmount", rebateAmount.toString());
        params.put("rebateUseRange", rebateUseRange.toString());
        params.put("rebateUseRangeValue", rebateUseRangeValue.toString());
        params.put("rebateLimit", rebateLimit.toString());
        params.put("batchCount", batchCount.toString());
        params.put("validType", validType.toString());
        params.put("validStartTime", validStartTime);
        params.put("validEndTime", validEndTime);
        params.put("validDays", validDays.toString());
        params.put("budgetAmount", budgetAmount.toString());
        params.put("channel", channel);
        params.put("remarks", remarks);
        params.put("operate_account", "admin");//稍后修改
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
            logger.debug("Request: {} getResponse: {}", url, hr);
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
