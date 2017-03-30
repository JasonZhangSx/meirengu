package com.meirengu.news.service;

import com.meirengu.news.model.FaqClass;
import com.meirengu.news.po.ListAllFaqClassPo;

import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
public interface FaqClassService extends PageBaseService<FaqClass>{

    /**
     * 新增问题分类
     * @param faqClass
     * @return
     */
    public int insert(FaqClass faqClass) throws Exception;

    /**
     * 修改问题分类
     * @param faqClass
     * @return
     */
    public int update(FaqClass faqClass) throws Exception;

    /**
     * 删除问题分类
     * @param id
     * @return
     */
    public int delete(int id) throws Exception;

    /**
     * 文章详情
     * @return
     */
    public Map<String, Object> detail(int id) throws Exception;

    /**
     * 根据分类名称查询该类是否存在
     * @param faqClass
     * @return
     */
    public int getFaqClass(FaqClass faqClass);

    public List<ListAllFaqClassPo> listAllFaqClass(FaqClass faqClass);

    Integer updateStatus(Integer classId, Byte status, String operateAccount);
}
