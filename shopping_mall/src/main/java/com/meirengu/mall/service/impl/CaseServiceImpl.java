package com.meirengu.mall.service.impl;

import com.meirengu.mall.dao.CaseDao;
import com.meirengu.mall.dao.PageDao;
import com.meirengu.mall.model.Case;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.CaseService;
import com.meirengu.mall.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 案例业务实现类
 * @author 建新
 * @create 2017-01-10 17:15
 */
@Service
@Transactional(readOnly = true)
public class CaseServiceImpl implements CaseService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private PageService<Case> pageService;

    @Override
    public Page<Case> getPageList(Page<Case> page, Map map) {
        return pageService.getListByPage(page, map, caseDao);
    }
    

    @Override
    @Transactional(readOnly = false)
    public boolean add(Case c) {
        try {
            int addNum = caseDao.add(c);
            if(addNum > 0){
                return true;
            }else {
                return false;
            }
        } catch (RuntimeException e){
            LOGGER.error("add case throw exception: ", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Case c) {
        try {
            int updateNum = caseDao.update(c);
            if(updateNum > 0){
                return 1;
            }else {
                return 2;
            }
        } catch (RuntimeException e){
            LOGGER.error("update case throw exception: ", e);
            return 0;
        }
    }

    @Override
    public Case detail(Case c) {
        try {
            return caseDao.detail(c);
        }catch (RuntimeException e){
            LOGGER.error("get case detail throw exception: ", e);
            return null;
        }
    }
}
