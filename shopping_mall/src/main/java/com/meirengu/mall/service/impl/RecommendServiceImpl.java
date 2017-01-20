package com.meirengu.mall.service.impl;

import com.meirengu.mall.dao.RecommendDao;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.model.Recommend;
import com.meirengu.mall.service.PageService;
import com.meirengu.mall.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2017/1/9.
 */
@Service
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService{


    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private RecommendDao recommendDao;

    @Autowired
    private PageService<Recommend> pageService;

    @Override
    public Page<Recommend> getPageList(Page<Recommend> page, Map map) {
        return pageService.getListByPage(page, map, recommendDao);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean add(Recommend recommend) {
        try {
            int addNum = recommendDao.add(recommend);
            if(addNum > 0){
                return true;
            }else {
                return false;
            }
        }catch (RuntimeException e){
            LOGGER.error("add recommend throw exception: ", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Recommend recommend) {
        try {
            int updateNum = recommendDao.update(recommend);
            if(updateNum > 0){
                return 1;
            }else {
                return 2;
            }
        }catch (RuntimeException e){
            LOGGER.error("update recommend throw exception: ", e);
            return 0;
        }
    }

    @Override
    public Recommend detail(int id) {
        try {
            Recommend recommend = recommendDao.detail(id);
            return recommend;
        }catch (RuntimeException e){
            LOGGER.error("get recommend detail throw exception: ", e);
            return null;
        }
    }
}
