package com.meirengu.erp.controller.partner;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
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
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/partner/partnerClassList", map);
    }
}
