package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.ItemExtention;
import com.meirengu.erp.service.ItemExtentionService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目扩展信息服务层
 * @author 建新
 * @create 2017-03-29 21:34
 */
@Service
public class ItemExtentionServiceImpl implements ItemExtentionService{
    @Override
    public Object query(int page, int perPage, boolean isPage) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.extention.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    return jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> detail(int id) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.extention.detail"));
        url.append("/").append(id);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    return (Map) jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> add(ItemExtention extention) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.extention.add"));

        params.put("item_id", String.valueOf(extention.getItemId()));
        params.put("sell_share", String.valueOf(extention.getSellShare()));
        params.put("finance_amount", String.valueOf(extention.getFinanceAmount()));
        params.put("register_capital", String.valueOf(extention.getRegisterCapital()));
        params.put("captital_reserve", String.valueOf(extention.getCaptitalReserve()));
        params.put("after_register_capital", String.valueOf(extention.getAfterRegisterCapital()));
        params.put("lead_investor_id", String.valueOf(extention.getLeadInvestorId()));
        params.put("lead_investor_amount", String.valueOf(extention.getLeadInvestorAmount()));
        params.put("executive_partner", extention.getExecutivePartner());
        params.put("lead_investor_reason", extention.getLeadInvestorReason());
        params.put("limited_partnership_id1", String.valueOf(extention.getLimitedPartnershipId1() == null ? 0 : extention.getLimitedPartnershipId1()));
        params.put("limited_partnership_id2", String.valueOf(extention.getLimitedPartnershipId2() == null ? 0 : extention.getLimitedPartnershipId2()));
        params.put("limited_partnership_id3", String.valueOf(extention.getLimitedPartnershipId3() == null ? 0 : extention.getLimitedPartnershipId3()));
        params.put("limited_partnership_id4", String.valueOf(extention.getLimitedPartnershipId4() == null ? 0 : extention.getLimitedPartnershipId4()));
        params.put("equity_template", extention.getEquityTemplate());
        params.put("partnership_template", extention.getPartnershipTemplate());

        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return (Map) jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> update(ItemExtention extention) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.extention.update"));

        params.put("item_id", String.valueOf(extention.getItemId()));
        params.put("sell_share", String.valueOf(extention.getSellShare()));
        params.put("finance_amount", String.valueOf(extention.getFinanceAmount()));
        params.put("register_capital", String.valueOf(extention.getRegisterCapital()));
        params.put("captital_reserve", String.valueOf(extention.getCaptitalReserve()));
        params.put("after_register_capital", String.valueOf(extention.getAfterRegisterCapital()));
        params.put("lead_investor_id", String.valueOf(extention.getLeadInvestorId()));
        params.put("lead_investor_amount", String.valueOf(extention.getLeadInvestorAmount()));
        params.put("executive_partner", extention.getExecutivePartner());
        params.put("lead_investor_reason", extention.getLeadInvestorReason());
        params.put("limited_partnership_id1", String.valueOf(extention.getLimitedPartnershipId1() == null ? 0 : extention.getLimitedPartnershipId1()));
        params.put("limited_partnership_id2", String.valueOf(extention.getLimitedPartnershipId2() == null ? 0 : extention.getLimitedPartnershipId2()));
        params.put("limited_partnership_id3", String.valueOf(extention.getLimitedPartnershipId3() == null ? 0 : extention.getLimitedPartnershipId3()));
        params.put("limited_partnership_id4", String.valueOf(extention.getLimitedPartnershipId4() == null ? 0 : extention.getLimitedPartnershipId4()));
        params.put("equity_template", extention.getEquityTemplate());
        params.put("partnership_template", extention.getPartnershipTemplate());

        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return (Map) jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.delete"));
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return (Map) jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
