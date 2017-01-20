package com.meirengu.mall.dao;

import com.meirengu.mall.model.Recommend;

import java.util.List;
import java.util.Map;

/**
 * 推荐dao
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface RecommendDao extends PageDao<Recommend>{


    /**
     * 新增推荐内容
     * @param recommend
     * @return
     */
    int add(Recommend recommend);

    /**
     * 修改推荐内容
     * @param recommend
     * @return
     */
    int update(Recommend recommend);

    /**
     * 获取推荐内容详情
     * @param id
     * @return
     */
    Recommend detail(int id);

    /**
     * 不分页获取列表数据
     * @param map
     * @return
     */
    List<Map<String,Object>> getByPage(Map map);

}
