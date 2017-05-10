package com.meirengu.erp.controller.partner;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.erp.model.Partner;
import com.meirengu.erp.service.PartnerService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作伙伴控制层
 * @author 建新
 * @create 2017-03-25 17:47
 */
@RestController
@RequestMapping("partner")
public class PartnerController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    PartnerService partnerService;


    @RequestMapping("list")
    public ModelAndView partnerList(){
        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.list"));
        url.append("?is_page=true");
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
        List classList = partnerService.getPartnerClassList();
        map.put("classList", classList);
        return new ModelAndView("/partner/partnerList", map);
    }


    @RequestMapping("class/list")
    public ModelAndView partnerClassList(HttpServletRequest request, String className) throws UnsupportedEncodingException {

        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.list"));
        url.append("?is_page=true");
        if(!StringUtil.isEmpty(className)){
            className = new String(className.getBytes("ISO-8859-1"), "utf-8");
            url.append("&class_name=").append(className);
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

        map.put("className", className);
        return new ModelAndView("/partner/partnerClassList", map);
    }

    @RequestMapping("class/to_add")
    public ModelAndView toClassAdd(){

        return new ModelAndView("/partner/partnerClassAdd");
    }

    @RequestMapping("class/add")
    public ModelAndView classAdd(String className, String classDescription, HttpServletRequest request){

        try {
            Map<String,Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("partner.class.insert");
            Map<String, String> params = new HashMap<>();
            params.put("class_name", className);
            params.put("class_description", classDescription);
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/partner/class/list");
    }

    @RequestMapping("class/delete")
    public ModelAndView classDelete(String classDescription, HttpServletRequest request){

        try {
            request.setCharacterEncoding("utf-8");
            String className = request.getParameter("className");
            Map<String,Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("partner.class.insert");
            Map<String, String> params = new HashMap<>();
            params.put("class_name", className);
            params.put("class_description", classDescription);
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/partner/class/list");
    }

    @RequestMapping("to_add")
    public ModelAndView toAdd(){
        Map<String, Object> map = new HashMap<>();
        List classList = partnerService.getPartnerClassList();
        map.put("classList", classList);
        return new ModelAndView("/partner/partnerAdd", map);
    }

    @RequestMapping("add")
    public ModelAndView add(Integer typeId, String partnerName, Date partnerCreateDay, Integer partnerRegistCapital, Integer partnerValuation,
                            Integer accountId, String enterpriseName, String idNumber, String enterpriseAddress, String principalName,
                            String principalIdcard, String principalTelephone, String principalFax, String principalAddress, String contactsName,
                            String contactsIdcard, String contactsTelephone, String contactsFax, String bankName, String bankAccount,
                            String bankCard, String imagePrincipal, String imageBusinessLicence, String imageBank, String imageProfessionalLicense,
                            String contactsAddress){
        Map<String, String> params = new HashMap<String, String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        params.put("type_id", String.valueOf(typeId));
        params.put("partner_name", partnerName);
        params.put("partner_create_day", format.format(partnerCreateDay).toString());
        params.put("partner_regist_capital", String.valueOf(partnerRegistCapital));
        params.put("partner_valuation", String.valueOf(partnerValuation));
        params.put("account_id", String.valueOf(accountId));
        params.put("enterprise_name", enterpriseName);
        params.put("id_number", idNumber);
        params.put("enterprise_address", enterpriseAddress);
        params.put("principal_name", principalName);
        params.put("principal_idcard", principalIdcard);
        params.put("principal_telephone", principalTelephone);
        params.put("principal_fax", principalFax);
        params.put("principal_address", principalAddress);
        params.put("contacts_name", contactsName);
        params.put("contacts_idcard", contactsIdcard);
        params.put("contacts_telephone", contactsTelephone);
        params.put("contacts_fax", contactsFax);
        params.put("bank_name", bankName);
        params.put("bank_account", bankAccount);
        params.put("bank_card", bankCard);
        params.put("image_principal", String.valueOf(imagePrincipal));
        params.put("image_business_licence", imageBusinessLicence);
        params.put("image_bank", imageBank);
        params.put("image_professional_license", imageProfessionalLicense);
        params.put("contacts_address", contactsAddress);
        params.put("operate_account", "admin");
        partnerService.partnerAdd(params);
        return new ModelAndView("redirect:/partner/list");
    }
}


