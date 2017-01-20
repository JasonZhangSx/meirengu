package com.meirengu.mall.service;

import com.meirengu.mall.model.RecommendPosition;

import java.util.List;
import java.util.Map;

/**
 * 推荐位service
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface RecommendPositionService extends PageBaseService<RecommendPosition>{

    /**
     * 新增广告位
     * @param rp
     * @return
     */
    boolean add(RecommendPosition rp);

    /**
     * 修改广告位
     * @param rp
     * @return
     */
    int update(RecommendPosition rp);

    /**
     * 获取推荐位详情
     * @param id
     * @return
     */
    RecommendPosition detail(int id);

    /**
     * 获取推荐
     * @return
     */
    List<Map<String, Object>> getRpList(String rp_ids);
}
