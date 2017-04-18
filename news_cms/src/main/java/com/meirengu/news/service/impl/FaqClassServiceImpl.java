package com.meirengu.news.service.impl;

import com.meirengu.news.dao.FaqClassDao;
import com.meirengu.news.model.FaqClass;
import com.meirengu.news.model.Page;
import com.meirengu.news.po.ListAllFaqClassPo;
import com.meirengu.news.service.FaqClassService;
import com.meirengu.news.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
@Service
public class FaqClassServiceImpl implements FaqClassService {


    @Autowired
    private FaqClassDao faqClassDao;
    @Autowired
    private PageService<FaqClass> pageService;

    @Override
    public Page<FaqClass> getPageList(Page<FaqClass> page, Map map) {
        return pageService.getListByPage(page, map, faqClassDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, faqClassDao);
    }

    @Override
    public int insert(FaqClass faqClass) throws Exception {
        faqClass.setCreateTime(new Date());
        faqClass.setStatus(new Byte("0"));
        faqClass.setUpdateTime(new Date());
        return faqClassDao.insert(faqClass);
    }

    @Override
    public int update(FaqClass faqClass) throws Exception {
        return 0;
    }

    @Override
    public int delete(int id) throws Exception {
        return faqClassDao.delete(id);
    }

    @Override
    public Map<String, Object> detail(int id) throws Exception {
        return faqClassDao.detail(id);
    }

    @Override
    public int getFaqClass(FaqClass faqClass) {
        return faqClassDao.getFaqClass(faqClass);
    }

    @Override
    public List<ListAllFaqClassPo> listAllFaqClass(FaqClass faClass) {
        List<FaqClass> faqClassList = faqClassDao.listAllFaqClass(faClass);
        List<ListAllFaqClassPo> listAllFaqClassPo = new ArrayList<ListAllFaqClassPo>();
        for(FaqClass faqClass :faqClassList){
            ListAllFaqClassPo faqClassPo = new ListAllFaqClassPo();
            faqClassPo.setClassId(faqClass.getClassId()+"");
            faqClassPo.setClassName(faqClass.getClassName());
            listAllFaqClassPo.add(faqClassPo);
        }
        return listAllFaqClassPo;
    }

    @Override
    public Integer updateStatus(Integer classId, Byte status, String operateAccount,String className) {
        FaqClass faqClass = new FaqClass();
        faqClass.setClassId(classId);
        faqClass.setClassName(className);
        faqClass.setStatus(status);
        faqClass.setUpdateTime(new Date());
        faqClass.setOperateAccount(operateAccount);
        return faqClassDao.update(faqClass);
    }
}
