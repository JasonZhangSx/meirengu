package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.OrderService;
import com.meirengu.webview.utils.ConfigUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 订单服务实现
 * @author 建新
 * @create 2017-05-10 11:34
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public Map getSupportList(int itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_ORDER"));
        url.append("?need_avatar=1&page_num=1&sort_by=create_time&order=desc&page_size=6&item_id=").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    Map map = (Map) jsonObject.get("data");
                    return map;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
