package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.Partner;
import com.meirengu.erp.model.PartnerClass;
import com.meirengu.erp.service.PartnerService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作方服务层
 * @author 建新
 * @create 2017-03-29 21:34
 */
@Service
public class PartnerServiceImpl implements PartnerService{
    @Override
    public Object query(int page, int perPage, boolean isPage) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        url.append("&flag=1");
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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.detail"));
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
    public Map<String, Object> add(Partner partner) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.insert"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        params.put("type_id", String.valueOf(partner.getTypeId()));
        params.put("partner_name", partner.getPartnerName());
        params.put("partner_label", partner.getPartnerLabel());
        params.put("partner_telphone", partner.getPartnerTelphone());
        params.put("partner_img", partner.getPartnerImg());
        params.put("partner_create_day", format.format(partner.getPartnerCreateDay()).toString());
        params.put("partner_regist_capital", String.valueOf(partner.getPartnerRegistCapital()));
        params.put("partner_valuation", String.valueOf(partner.getPartnerValuation()));
        params.put("account_id", String.valueOf(partner.getAccountId()));
        params.put("enterprise_name", partner.getEnterpriseName());
        params.put("id_number", String.valueOf(partner.getIdNumber()));
        params.put("enterprise_address", partner.getEnterpriseAddress());
        params.put("principal_name", partner.getPrincipalName());
        params.put("principal_idcard", partner.getPrincipalIdcard());
        params.put("principal_telephone", partner.getPrincipalTelephone());
        params.put("principal_fax", String.valueOf(partner.getPrincipalFax()));
        params.put("principal_address", partner.getPrincipalAddress());
        params.put("contacts_name", partner.getContactsName());
        params.put("contacts_idcard", partner.getContactsIdcard());
        params.put("contacts_telephone", partner.getContactsTelephone());
        params.put("contacts_fax", String.valueOf(partner.getContactsFax()));
        params.put("bank_name", partner.getBankName());
        params.put("bank_account", partner.getBankAccount());
        params.put("bank_card", String.valueOf(partner.getBankCard()));
        params.put("image_principal", partner.getImagePrincipal());
        params.put("image_business_licence", partner.getImageProfessionalLicense());
        params.put("image_bank", partner.getImageBank());
        params.put("image_professional_license", partner.getImageProfessionalLicense());
        params.put("contacts_address", partner.getContactsAddress());
        params.put("operate_account", "admin");

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
    public Map<String, Object> update(Partner partner) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.update"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        params.put("partner_id", String.valueOf(partner.getPartnerId()));
        params.put("type_id", String.valueOf(partner.getTypeId()));
        params.put("partner_label", partner.getPartnerLabel());
        params.put("partner_telphone", partner.getPartnerTelphone());
        params.put("partner_img", partner.getPartnerImg());
        params.put("partner_name", partner.getPartnerName());
        params.put("partner_create_day", format.format(partner.getPartnerCreateDay()).toString());
        params.put("partner_regist_capital", String.valueOf(partner.getPartnerRegistCapital()));
        params.put("partner_valuation", String.valueOf(partner.getPartnerValuation()));
        params.put("account_id", String.valueOf(partner.getAccountId()));
        params.put("enterprise_name", partner.getEnterpriseName());
        params.put("id_number", String.valueOf(partner.getIdNumber()));
        params.put("enterprise_address", partner.getEnterpriseAddress());
        params.put("principal_name", partner.getPrincipalName());
        params.put("principal_idcard", partner.getPrincipalIdcard());
        params.put("principal_telephone", partner.getPrincipalTelephone());
        params.put("principal_fax", String.valueOf(partner.getPrincipalFax()));
        params.put("principal_address", partner.getPrincipalAddress());
        params.put("contacts_name", partner.getContactsName());
        params.put("contacts_idcard", partner.getContactsIdcard());
        params.put("contacts_telephone", partner.getContactsTelephone());
        params.put("contacts_fax", String.valueOf(partner.getContactsFax()));
        params.put("bank_name", partner.getBankName());
        params.put("bank_account", partner.getBankAccount());
        params.put("bank_card", String.valueOf(partner.getBankCard()));
        params.put("image_principal", partner.getImagePrincipal());
        params.put("image_business_licence", partner.getImageProfessionalLicense());
        params.put("image_bank", partner.getImageBank());
        params.put("image_professional_license", partner.getImageProfessionalLicense());
        params.put("contacts_address", partner.getContactsAddress());
        params.put("operate_account", "admin");
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
        params.put("partner_id", String.valueOf(id));
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
