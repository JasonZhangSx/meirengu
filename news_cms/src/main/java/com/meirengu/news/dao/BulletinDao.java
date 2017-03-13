package com.meirengu.news.dao;


import com.meirengu.news.model.Bulletin;
import org.springframework.stereotype.Repository;

/**
 * 公告数据操作
 * Created by maoruxin on 2017/3/10.
 */
@Repository
public interface BulletinDao extends PageDao<Bulletin>{

    /**
     * 新增公告
     * @param bulletin
     * @return
     */
    public int insert(Bulletin bulletin);

    /**
     * 修改公告
     * @param bulletin
     * @return
     */
    public int update(Bulletin bulletin);

    /**
     * 查看公告
     * @param id
     * @return
     */
    public Bulletin getById(int id);
}
