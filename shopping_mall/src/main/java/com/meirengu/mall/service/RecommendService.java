package com.meirengu.mall.service;

import com.meirengu.mall.model.Recommend;

/**
 * 推荐位service
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface RecommendService extends PageBaseService<Recommend>{

    /**
     * 新增推荐内容
     * @param recommend
     * @return
     */
    boolean add(Recommend recommend);

    /**
     * 修改推荐内容
     * @param recommend
     * @return
     */
    int update(Recommend recommend);

    /**
     * 获取推荐详情
     * @param id
     * @return
     */
    Recommend detail(int id);


}
