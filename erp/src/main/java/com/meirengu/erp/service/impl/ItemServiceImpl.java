package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.common.Constants;
import com.meirengu.erp.model.*;
import com.meirengu.erp.service.ItemService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目服务实现
 * @author 建新
 * @create 2017-03-29 19:32
 */
@Service
public class ItemServiceImpl implements ItemService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Override
    public Map<String, Object> getItemListByPage(int page, int perPage, boolean isPage, Integer itemId, String itemName, String itemStatus) {

        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));

        url.append("?is_page=").append(isPage);
        url.append("&per_page=").append(perPage).append("&page=").append(page);
        if(!StringUtil.isEmpty(itemId)){
            url.append("&item_id=").append(itemId);
        }
        if(!StringUtil.isEmpty(itemName)){
            url.append("&item_name=").append(itemName);
        }
        if(!StringUtil.isEmpty(itemStatus)){
            url.append("&item_status=").append(itemStatus);
        }

        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public Object itemInsert(Map<String, String> params) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.insert"));

        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return jsonObject.get("data");
            }
        }
        return null;
    }

    @Override
    public List<ItemClass> getItemClassList() {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.class.list"));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    List<ItemClass> list = (List) jsonObject.get("data");
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object contentInsert(Map<String, String> params) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.content.insert"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return jsonObject.get("data");
            }
        }
        return null;
    }

    @Override
    public Object levelInsert(Map<String, String> params) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.level.insert"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return jsonObject.get("data");
            }
        }
        return null;
    }

    @Override
    public boolean itemUpdate(Map<String, String> params) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.update"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contentUpdate(Map<String, String> params) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.content.update"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean levelUpdate(Map<String, String> params) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.level.update"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean submitVerify(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.update"));
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemId));
        params.put("item_status", String.valueOf(Constants.ITEM_FIRST_VERIFY));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map itemDetail(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.detail"));
        url.append("/").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    JSONObject itemJson = (JSONObject) jsonObject.get("data");
                    return JSON.parseObject(itemJson.toJSONString(), Map.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getContentList(Integer itemId, Integer contentType) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.content.list"));
        url.append("?item_id=").append(itemId);
        if(contentType != null && contentType != 0){
            url.append("&content_type=").append(contentType);
        }
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return (List) jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getLevelList(Integer itemId) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.level.list"));
        url.append("?item_id=").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return (List) jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean verify(Integer itemId, Integer operateStatus, String operateRemark) {

        String url = ConfigUtil.getConfig("item.verify");
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemId));
        params.put("operate_status", String.valueOf(operateStatus));
        params.put("operate_remark", String.valueOf(operateRemark));
        params.put("operate_account", "admin");
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean setCooperate(Map<String, String> map) {
        String url = ConfigUtil.getConfig("item.cooperate");
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, map);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean review(Integer itemId, Integer operateStatus, String operateRemark) {

        String url = ConfigUtil.getConfig("item.review");
        Map<String, String> params = new HashMap<>();
        params.put("item_id", String.valueOf(itemId));
        params.put("operate_status", String.valueOf(operateStatus));
        params.put("operate_remark", String.valueOf(operateRemark));
        params.put("operate_account", "admin");
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List getOperateRecordList(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.operate.record.list"));
        url.append("?item_id=").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return (List) jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map getCooperateInfo(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.cooperate.insert"));
        url.append("/").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return (Map) jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean publish(Date publishTime, Integer type, Integer itemId, String operateAccount) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.publish"));
        Map<String, String> params = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(publishTime == null){
            publishTime = new Date();
        }

        params.put("item_id", itemId == null ? "0" : String.valueOf(itemId));
        params.put("appoint_date", format.format(publishTime).toString());
        params.put("type", type == null ? "2" : String.valueOf(type));
        params.put("operate_account", operateAccount);

        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer notifyPaymentCommitBonus(Map<String, String> map) {
        LOGGER.info("ItemServiceImpl notifyPaymentCommitBonus started :{}",new Date());
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.notify.paymentCommitBonus"));
        Map<String, String> params = new HashMap<>();

        params.put("itemId",map.get("itemId"));
        params.put("itemName", map.get("itemName"));

        Map<String, String> contentParams = new HashMap<>();
        contentParams.put("content",JacksonUtil.toJSon(params));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), contentParams);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    LOGGER.info("ItemServiceImpl notifyPaymentCommitBonus successful :{}",new Date());
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer notifyPaymentCommit(Map<String, String> map) {
        LOGGER.info("ItemServiceImpl notifyPaymentCommit started :{}",new Date());
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.notify.paymentCommit"));
        Map<String, Object> params = new HashMap<>();

        params.put("itemId",map.get("itemId"));
        params.put("itemName", map.get("itemName"));
        params.put("partnerId", map.get("partnerId"));
        params.put("partnerName", map.get("partnerName"));
        params.put("targetAmount", map.get("targetAmount"));
        params.put("completedAmount", map.get("completedAmount"));
        params.put("endDate", DateAndTime.dateFormat(map.get("crowdEndTime").toString(), "yyyy-MM-dd"));
        params.put("firstRatio", map.get("firstRatio"));
        Integer firstRatio = (Integer) params.get("firstRatio");
        params.put("endRatio", String.valueOf(100 - firstRatio));
        params.put("loanMode", map.get("loanMode"));

        Map<String, String> contentParams = new HashMap<>();
        contentParams.put("content",JacksonUtil.toJSon(params));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(),contentParams);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    LOGGER.info("ItemServiceImpl notifyPaymentCommit successful :{}",new Date());
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer notifyPaymentCollectionList(Map<String, String> map) {
        LOGGER.info("ItemServiceImpl notifyPaymentCollectionList started :{}",new Date());
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.notify.paymentCollectionList"));
        Map<String, String> params = new HashMap<>();

        params.put("itemId",map.get("itemId"));
        params.put("itemName", map.get("itemName"));
        params.put("partnerId", map.get("partnerId"));
        params.put("partnerName", map.get("partnerName"));
        params.put("targetAmount", map.get("targetAmount"));

        Map<String, String> contentParams = new HashMap<>();
        contentParams.put("content",JacksonUtil.toJSon(params));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), contentParams);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    LOGGER.info("ItemServiceImpl notifyPaymentCollectionList successful :{}",new Date());
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
