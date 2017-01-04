package com.meirengu.news.dao;
import com.meirengu.news.model.Article;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/27.
 */
public interface ArticleDao extends PageDao<Article>{

    /**
     * 新增文章
     * @param article
     * @return
     */
    public int insert(Article article);

    /**
     * 修改文章
     * @param article
     * @return
     */
    public int update(Article article);

    /**
     * 删除文章
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * 文章详情
     * @return
     */
    public Map<String, Object> detail(int id);


}
