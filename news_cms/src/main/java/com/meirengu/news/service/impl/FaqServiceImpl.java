package com.meirengu.news.service.impl;

import com.meirengu.news.dao.FaqDao;
import com.meirengu.news.model.Article;
import com.meirengu.news.model.Faq;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.FaqService;
import com.meirengu.news.service.PageService;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
@Service
public class FaqServiceImpl implements FaqService{


    @Autowired
    private FaqDao faqDao;
    @Autowired
    private PageService<Faq> pageService;

    @Override
    public Page<Faq> getPageList(Page<Faq> page, Map map) {
        return pageService.getListByPage(page, map, faqDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, faqDao);
    }

    @Override
    public int getFaq(Faq faq) {
        Faq newFaq = faqDao.getFaq(faq);
        if(StringUtil.isEmpty(newFaq)){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int insert(Faq faq) {
        faq.setStatus(new Byte("0"));
        faq.setCreateTime(new Date());
        faq.setUpdateTime(new Date());
        return faqDao.insert(faq);
    }

    @Override
    public Faq getFaqById(Integer classId) {
        Faq newFaq = new Faq();
        newFaq.setFaqId(classId);
        return faqDao.getFaq(newFaq);
    }

    @Override
    public Integer updateStatus(Integer faqId,Byte status,String operateAccount) {
        Faq faq = new Faq();
        faq.setFaqId(faqId);
        faq.setStatus(status);
        faq.setUpdateTime(new Date());
        faq.setOperateAccount(operateAccount);
        return faqDao.update(faq);
    }
}
