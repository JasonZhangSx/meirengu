package com.meirengu.mall.service.impl;

import com.meirengu.mall.dao.RecommendDao;
import com.meirengu.mall.dao.RecommendPositionDao;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.model.Recommend;
import com.meirengu.mall.model.RecommendPosition;
import com.meirengu.mall.service.PageService;
import com.meirengu.mall.service.RecommendPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2017/1/9.
 */
@Service
@Transactional(readOnly = true)
public class RecommendPositionServiceImpl implements RecommendPositionService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendPositionServiceImpl.class);

    @Autowired
    private RecommendPositionDao recommendPositionDao;

    @Autowired
    private RecommendDao recommendDao;

    @Autowired
    private PageService<RecommendPosition> pageService;

    @Override
    public Page<RecommendPosition> getPageList(Page<RecommendPosition> page, Map map) {
        return pageService.getListByPage(page, map, recommendPositionDao);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean add(RecommendPosition rp) {
        try {
            int addNum = recommendPositionDao.add(rp);
            if(addNum > 0){
                return true;
            }else {
                return false;
            }
        }catch (RuntimeException e){
            LOGGER.error("add recommend position throw exception: ", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int update(RecommendPosition rp) {
        try {
            int updateNum = recommendPositionDao.update(rp);
            if(updateNum > 0){
                return 1;
            }else {
                return 2;
            }
        }catch (RuntimeException e){
            LOGGER.error("update recommend position throw exception: ", e);
            return 0;
        }
    }

    @Override
    public RecommendPosition detail(int id) {

        try {
            RecommendPosition rp = recommendPositionDao.detail(id);
            return rp;
        }catch (RuntimeException e){
            LOGGER.error("get recommend position detail throw exception: ", e);
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getRpList(String rpIds) {
        Map<String, Object> map = new HashMap<>();
        String[] rpIdArray = rpIds.split(",");
        map.put("id", rpIdArray);
        List<Map<String, Object>> rpList = recommendPositionDao.getByPage(map);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (int i = 0; i < rpList.size(); i++ ){
            Map<String, Object> rpMap = rpList.get(i);
            int rpId = rpMap.get("id") == null?0:Integer.parseInt(rpMap.get("id").toString());
            Map<String, Object> pParamMap = new HashMap<>();
            pParamMap.put("rpId", rpId);
            List<Map<String, Object>> recommendList = recommendDao.getByPage(pParamMap);
            rpMap.put("recommendList", recommendList);
            resultList.add(rpMap);
        }

        return resultList;
    }
}
