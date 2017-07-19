package com.meirengu.news.service.impl;
import com.meirengu.common.Constants;
import com.meirengu.common.RedisClient;
import com.meirengu.news.dao.IpWhiteListDao;
import com.meirengu.news.model.IpWhiteList;
import com.meirengu.news.service.IpWhiteListService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.utils.StringUtil;
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

    @Autowired
    private IpWhiteListDao ipWhiteListDao;

    @Override
    public List getWhiteList(){
        List ipWhiteList = redisClient.getList("mrg_ip_white_list");
        if(ipWhiteList == null || ipWhiteList.size() <= 0){
            ipWhiteList = updateWhiteList();
        }

        return ipWhiteList;
    }

    @Override
    public List updateWhiteList() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 1);
        List ipWhiteList = getList(params);
        redisClient.setList("mrg_ip_white_list", ipWhiteList, 60*60*24*30);
        return ipWhiteList;
    }

    @Override
    public boolean insertIp(IpWhiteList ipWhiteList) {
        ipWhiteListDao.insert(ipWhiteList);
        setCache();
        return false;
    }

    @Override
    public boolean updateIp(IpWhiteList ipWhiteList) {
        ipWhiteListDao.update(ipWhiteList);
        setCache();
        return false;
    }

    public String getIpFromWhiteList(String key) {
        String url = redisClient.get(Constants.IP_WHITE_PREFIX+key);
        if(StringUtil.isEmpty(url)){
            setCache();
            url = redisClient.get(Constants.IP_WHITE_PREFIX+key);
        }
        return url;
    }

    @Override
    public void setCache(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 1);
        List<Map<String, Object>> ipWhiteList = getList(params);
        for(Map<String, Object> whiteList : ipWhiteList){
            String ip = whiteList.get("ip").toString();
            String url = whiteList.get("url").toString();
            redisClient.set(Constants.IP_WHITE_PREFIX+ip, url);
        }
    }

}
