package com.meirengu.news.service.impl;
import com.meirengu.common.Constants;
import com.meirengu.common.RedisClient;
import com.meirengu.news.model.IpWhiteList;
import com.meirengu.news.service.IpWhiteListService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IpWhiteList服务实现层 
 * @author 建新
 * @create Mon Jul 17 15:45:05 CST 2017
 */
@Service
@Transactional
public class IpWhiteListServiceImpl extends BaseServiceImpl<IpWhiteList> implements IpWhiteListService{

    @Autowired
    private RedisClient redisClient;

    @Override
    public void setCache(){
        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("status", 1);
        List<Map<String, Object>> ipWhiteList = getList(params);
        for(Map<String, Object> whiteList : ipWhiteList){
            String ip = whiteList.get("ip").toString();
            String url = whiteList.get("url").toString();
            String status = whiteList.get("status").toString();
            if(status.equals("1")){
                redisClient.set(Constants.IP_WHITE_PREFIX+ip, url);
            }else if(status.equals("2")){
                redisClient.removeNoPrefix(Constants.IP_WHITE_PREFIX+ip);
            }

        }
    }

}
