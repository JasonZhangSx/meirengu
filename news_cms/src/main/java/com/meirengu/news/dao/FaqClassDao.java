package com.meirengu.news.dao;

import com.meirengu.news.model.FaqClass;

import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
public interface FaqClassDao extends PageDao<FaqClass>{
    /**
     * 新增问题
     * @param faqClass
     * @return
     */
    public int insert(FaqClass faqClass);

    /**
     * 修改问题
     * @param faqClass
     * @return
     */
    public int update(FaqClass faqClass);

    /**
     * 删除问题
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * 问题详情
     * @return
     */
    public Map<String, Object> detail(int id);


    public int getFaqClass(FaqClass faqClass);

    public List<FaqClass> listAllFaqClass();
}
