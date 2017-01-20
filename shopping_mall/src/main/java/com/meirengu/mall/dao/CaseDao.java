package com.meirengu.mall.dao;

import com.meirengu.mall.model.Case;

/**
 * 案例dao
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface CaseDao extends PageDao<Case>{

    /**
     * 案例新增
     * @param c
     * @return
     */
    int add(Case c);

    /**
     * 修改案例信息
     * @param c
     * @return
     */
    int update(Case c);

    /**
     * 获取详情
     * @param c
     * @return
     */
    Case detail(Case c);
}
