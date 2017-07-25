package com.meirengu.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.model.LogOperationDetail;
import com.meirengu.commons.authority.service.LogOperationService;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-28 16:46
 */
public class BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private LogOperationService logOperationService;

    /**
     * 封装返回数据对象
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Result setResult(int code, Object data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (code == 200 && (data != null && !"".equals(data))){
            result.setData(data);
        }else {
            //result.setData("");
        }
        LOGGER.info("Request getResponse: {}", JSON.toJSON(result));
        return result;
    }

    public Object httpGet(String url) throws IOException {
        LOGGER.info("BaseController httpGet url :{}",url);
        HttpUtil.HttpResult hr = HttpUtil.doGet(url);
        Object data = getData(hr);
        return data;
    }

    public Object httpPost(String url, Map<String, String> params){
        LOGGER.info("BaseController httpPost url :{} params:{}",url,params);
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
        Object data = getData(hr);
        return data;
    }
    public Object httpPut(String url, Map<String, String> params){
        LOGGER.info("BaseController httpPut url :{} params:{}",url,params);
        HttpUtil.HttpResult hr = HttpUtil.doPut(url, params);
        Object data = getData(hr);
        return data;
    }

    private Object getData(HttpUtil.HttpResult hr){
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(200)){
                Object data = jsonObject.get("data");
                return data;
            }
        }
        return null;
    }

    public <T> DataTablesOutput<T> setDataTablesOutput(DataTablesInput input, List<T> data, int totalCount) {
        DataTablesOutput<T> output = new DataTablesOutput<>();
        output.setDraw(input.getDraw());
        if (input.getLength() == 0) {
            return output;
        }

        try {
            long recordsTotal = totalCount;
            if (recordsTotal == 0) {
                return output;
            }
            output.setRecordsTotal(recordsTotal);
            output.setData(data);
            output.setRecordsFiltered(recordsTotal);

        } catch (Exception e) {
            output.setError(e.toString());
        }


        return output;
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public Account getLoginUser(){
        return (Account)SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 新增操作日志，在业务方法成功后添加(避免事务问题)
     * @param businessName  业务模块名称
     * @param operationType  OperationTypeEnum
     * @param primaryKey    业务主键
     * @param detailStr     修改的内容，格式为column_name|column_old_value|column_new_value，多个字段已逗号相隔
     */
    public void addLogOperation(String businessName, Integer operationType, String primaryKey, String detailStr){
        Account account = getLoginUser();
        logOperationService.addLogOperation(businessName,operationType,primaryKey,account.getId(),account.getUserName(),detailStr);
    }
}
