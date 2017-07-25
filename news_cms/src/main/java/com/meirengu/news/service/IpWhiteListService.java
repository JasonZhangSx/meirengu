package com.meirengu.news.service;
import com.meirengu.news.model.IpWhiteList;
import com.meirengu.service.BaseService;

import java.util.List;

/**
 * IpWhiteList服务接口 
 * @author 建新
 * @create Mon Jul 17 15:45:05 CST 2017
 */
public interface IpWhiteListService extends BaseService<IpWhiteList>{

    /**
     * 从数据库取出数据放入redis
     */
    void setCache();

}
