package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.service.TypeService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-29 21:27
 */
@Service
public class TypeServiceImpl implements TypeService{

    @Override
    public List getTypeList() {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.class.list"));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    List list = (List) jsonObject.get("data");
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
