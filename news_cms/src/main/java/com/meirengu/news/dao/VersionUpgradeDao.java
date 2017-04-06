package com.meirengu.news.dao;
import com.meirengu.news.model.VersionUpgrade;
import com.meirengu.dao.BaseDao;
/**
 * VersionUpgradeDao 
 * @author 建新
 * @create Mon Mar 27 11:05:06 CST 2017
 */
public interface VersionUpgradeDao extends BaseDao<VersionUpgrade>{

    int updateVersion(VersionUpgrade vu);

    VersionUpgrade getLastVersion(VersionUpgrade vu);
}
