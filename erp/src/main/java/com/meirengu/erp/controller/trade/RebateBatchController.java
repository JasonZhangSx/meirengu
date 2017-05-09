package com.meirengu.erp.controller.trade;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.vo.QueryVo;
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
}
