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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CMS公告控制类
 * Created by maoruxin on 2017/3/30.
 */
@Controller
@RequestMapping("/bulletin")
public class BulletinController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(BulletinController.class);

    /**
     * 跳转到公告列表页面
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String bulletinView() throws IOException {
        return "/cms/bulletinList";
    }

    /**
     * 公告新增
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String bulletinAddView() throws IOException {
        return "/cms/bulletinAdd";
    }

    /**
     * 公告修改
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
    public String bulletinUpdateView(@PathVariable("id") Integer bulletinId, ModelMap model) throws IOException {
        String url = ConfigUtil.getConfig("news.bulletin.detail") + "/" + bulletinId;
        Map<String,Object> httpData = (Map<String,Object>)httpGet(url);
        model.addAttribute("bulletin", httpData);
        return "/cms/bulletinUpdate";
    }

    /**
     * 公告详情
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/toDetail/{id}", method = RequestMethod.GET)
    public String bulletinDetailView(@PathVariable("id") Integer bulletinId, ModelMap model) throws IOException {
        String url = ConfigUtil.getConfig("news.bulletin.detail") + "/" + bulletinId;
        Map<String,Object> httpData = (Map<String,Object>)httpGet(url);
        model.addAttribute("bulletin", httpData);
        return "/cms/bulletinDetail";
    }


    /**
     * 公告列表数据请求
     * @param input
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput bulletinList(@Valid DataTablesInput input) throws IOException {
        // 组装请求参数
        QueryVo queryVo = new QueryVo(input);
        String paramStr = queryVo.getParamsStr();
        paramStr = paramStr.replace("page_num", "page").replace("page_size", "per_page").replace("sort_by", "sortby");

        String url = ConfigUtil.getConfig("news.bulletin.list") + "?" + paramStr;
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
     * 公共新增
     * @param bulletinTitle
     * @param bulletinContent
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(@RequestParam(value = "bulletinTitle", required = true)String bulletinTitle,
                         @RequestParam(value = "bulletinContent", required = true)String  bulletinContent) {
        if (StringUtils.isEmpty(bulletinTitle) || StringUtils.isEmpty(bulletinContent)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("news.bulletin.insert");
        Map<String, String> params = new HashMap<String, String>();
        params.put("bulletin_title", bulletinTitle);
        params.put("bulletin_content", bulletinContent);
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

    /**
     * 公告修改
     * @param bulletinTitle
     * @param bulletinContent
     * @param status
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam(value = "bulletinId", required = true)Integer bulletinId,
                         @RequestParam(value = "bulletinTitle", required = false)String bulletinTitle,
                         @RequestParam(value = "bulletinContent", required = false)String  bulletinContent,
                         @RequestParam(value = "status", required = false)Integer  status) {
        if (StringUtils.isEmpty(bulletinTitle) && StringUtils.isEmpty(bulletinContent) && status == null) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        String url = ConfigUtil.getConfig("news.bulletin.update") + "/" + bulletinId;
        Map<String, String> params = new HashMap<String, String>();
        params.put("bulletin_title", bulletinTitle);
        params.put("bulletin_content", bulletinContent);
        if (status != null) {
            params.put("status", status.toString());
        }
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
