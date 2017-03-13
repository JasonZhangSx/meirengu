package com.meirengu.news.dao;

import com.meirengu.news.model.Faq;

import java.util.Map;

/**
 * Created by huoyan403 on 3/10/2017.
 */
public interface FaqDao extends PageDao<Faq>{
    /**
     * 新增问题
     * @param faq
     * @return
     */
    public int insert(Faq faq);

    /**
     * 修改问题
     * @param faq
     * @return
     */
    public int update(Faq faq);

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

    /**
     * 根据问题名称或者编号获取问题
     * @param faq
     * @return
     */
    public Faq getFaq(Faq faq);
}
