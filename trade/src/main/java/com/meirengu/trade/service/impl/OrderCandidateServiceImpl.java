package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.model.OrderCandidate;
import com.meirengu.trade.service.OrderCandidateService;
import com.meirengu.trade.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * OrderCandidate服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class OrderCandidateServiceImpl extends BaseServiceImpl<OrderCandidate> implements OrderCandidateService{

    /**
     * 新增候补预约
     * @param orderCandidate
     * @return
     */
    public int insertCandidate(OrderCandidate orderCandidate) throws IOException {
        //获取项目名称
        int itemId = orderCandidate.getItemId();
        if (itemId != 0) {
            //查询项目头图
            String url = ConfigUtil.getConfig("item.url") + "/" + itemId + "?user_id=0";
            HttpUtil.HttpResult itemResult = HttpUtil.doGet(url);
            if (itemResult.getStatusCode() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(itemResult.getContent());
                int code = resultJson.getIntValue("code");
                if (code == StatusCode.OK) {
                    JSONObject item = resultJson.getJSONObject("data");
                    String itemName = item.getString("itemName");
                    orderCandidate.setItemName(itemName);
                }
            }
        }
        return insert(orderCandidate);
    }

}
