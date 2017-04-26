package com.meirengu.erp.controller.cms;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CMS意见反馈控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    /**
     * 跳转到意见反馈列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String feedbackView() {
        return "/cms/feedbackList";
    }

    /**
     * 意见反馈列表数据请求
     * @param input
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput feedbackList(@Valid DataTablesInput input) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        String paramStr = queryVo.getParamsStr();
        paramStr = paramStr.replace("pageNum", "page").replace("pageSize", "per_page").replace("sortColumn", "sortby");

        String url = ConfigUtil.getConfig("news.feedback.list") + "?" + paramStr;
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
     * 意见反馈修改
     * @param feedbackId
     * @param feedbackContent
     * @param status
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam(value = "feedbackId", required = true)Integer feedbackId,
                         @RequestParam(value = "feedbackContent", required = false)String feedbackContent,
                         @RequestParam(value = "status", required = false)Integer  status) {
        if (StringUtils.isEmpty(feedbackContent) && status == null) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("news.feedback.update") + "/" + feedbackId;
        Map<String, String> params = new HashMap<String, String>();
        params.put("feedback_content", feedbackContent);
        params.put("status", status.toString());
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
