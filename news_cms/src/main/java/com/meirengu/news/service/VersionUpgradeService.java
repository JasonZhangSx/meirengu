package com.meirengu.news.service;
import com.meirengu.news.model.VersionUpgrade;
import com.meirengu.service.BaseService;

/**
 * VersionUpgrade服务接口 
 * @author 建新
 * @create Mon Mar 27 11:05:06 CST 2017
 */
public interface VersionUpgradeService extends BaseService<VersionUpgrade>{

    boolean updateVersion(VersionUpgrade vu);

    VersionUpgrade getLastVersion(Integer appId, Integer appChannel, Integer status);
}
