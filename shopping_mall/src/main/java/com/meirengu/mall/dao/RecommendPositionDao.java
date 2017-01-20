package com.meirengu.mall.dao;

import com.meirengu.mall.model.Recommend;
import com.meirengu.mall.model.RecommendPosition;

import java.util.List;
import java.util.Map;

/**
 * 推荐位dao
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface RecommendPositionDao extends PageDao<RecommendPosition>{


    /**
     * 新增推荐位内容
     * @param rp
     * @return
     */
    int add(RecommendPosition rp);

    /**
     * 修改推荐位
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
     * 不分页获取列表
     * @param map
     * @return
     */
    List<Map<String, Object>> getByPage(Map map);

}
