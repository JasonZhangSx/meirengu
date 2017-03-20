package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.service.OrderService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Order服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{

    /**
     *获取后台订单列表
     * @param page
     * @param map
     * @return
     */
    public Page getSystemPage(Page page, Map map) throws IOException{
        page = getListByPage(page, map);
        List<Map<String, Object>> aList = page.getList();
        if (aList != null && aList.size() > 0) {
            //地址ID的set
            Set<Integer> addressIdSet = new HashSet<Integer>();
            //档位ID的set
            Set<Integer> itemLeavel = new HashSet<Integer>();
            for (Map<String, Object> orderMap : aList){
                int addressId = (int)((long)orderMap.get("userAddressId"));
                addressIdSet.add(addressId);
                int itemLevelId = (int)((long)orderMap.get("itemLevelId"));
                itemLeavel.add(itemLevelId);
            }
            //请求user_center服务获取用户地址信息
            Map<String, String> addressParams = new HashMap<String, String>();
            String addressIdsStr = addressIdSet.toString();
            String addressIds = addressIdsStr.substring(addressIdsStr.indexOf("[")+1,addressIdsStr.indexOf("]"));
            addressParams.put("address_id", addressIds);
            HttpResult addressHttpResult = HttpUtil.doGet("http://192.168.0.135:8084/uc" + Constant.ADDRESS_URL, addressParams);
            if (addressHttpResult.getStatusCode() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(addressHttpResult.getContent());
                int code = resultJson.getIntValue("code");
                if (code == HttpStatus.SC_OK) {
                    JSONArray addressArray = resultJson.getJSONArray("data");
                    for (int i = 0; i < addressArray.size(); i++) {
                        JSONObject addressJson = addressArray.getJSONObject(i);
                        System.out.print(addressJson.getIntValue("addressId"));
                        System.out.print(addressJson.getIntValue("nick"));
                        System.out.print(addressJson.getIntValue("userPhone"));
                        System.out.print(addressJson.getIntValue("province"));
                        System.out.print(addressJson.getIntValue("city"));
                    }
                }

            }
            //请求crowd_funding服务获取用户地址信息
        }

        return page;
    }
}
