package com.meirengu.news.service;

import com.meirengu.news.model.Faq;

/**
 * Created by huoyan403 on 3/10/2017.
 */
public interface FaqService extends PageBaseService<Faq>{

    /**
     * 根据名称查找问题
     */
    public int getFaq(Faq faq);

    /**
     * 新增常见问题
     * @param faq
     * @return
     */
    int insert(Faq faq);

    /**
     * 根据问题编号查找问题
     * @param classId
     * @return
     */
    Faq getFaqById(Integer classId);

    int update(Faq faq);
}
