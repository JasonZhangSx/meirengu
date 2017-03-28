package com.meirengu.news.service.impl;
import com.meirengu.exception.BusinessException;
import com.meirengu.news.dao.VersionUpgradeDao;
import com.meirengu.news.model.VersionUpgrade;
import com.meirengu.news.service.VersionUpgradeService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VersionUpgrade服务实现层 
 * @author 建新
 * @create Mon Mar 27 11:05:06 CST 2017
 */
@Service
public class VersionUpgradeServiceImpl extends BaseServiceImpl<VersionUpgrade> implements VersionUpgradeService{

    private static Logger LOGGER = LoggerFactory.getLogger(VersionUpgradeServiceImpl.class);

    @Autowired
    VersionUpgradeDao versionUpgradeDao;

    @Override
    @Transactional
    public boolean updateVersion(VersionUpgrade vu) {

        try {
            int insertNum = insert(vu);
            if(insertNum == 1){
                versionUpgradeDao.updateVersion(vu);
                return true;
            }else {
                LOGGER.warn(">> VersionUpgradeServiceImpl.insert fail......");
                return false;
            }
        }catch (Exception e){
            throw new BusinessException("VersionUpgradeServiceImpl.updateVersion throw exception: {}", e);
        }

    }
}
