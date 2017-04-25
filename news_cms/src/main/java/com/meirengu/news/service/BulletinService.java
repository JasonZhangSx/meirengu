package com.meirengu.news.service;

import com.meirengu.news.model.Bulletin;

import java.util.Map;


/**
 * 公告服务接口类
 * Created by maoruxin on 2017/3/10.
 */
public interface BulletinService extends PageBaseService<Bulletin>{

    /**
     * 新增公告
     * @param bulletinTitle
     * @param bulletinContent
     * @return
     * @throws Exception
     */
    int insert(String bulletinTitle,String bulletinContent,String operateAccount);

    /**
     * 修改公告
     * @param bulletin
     * @return
     * @throws Exception
     */
    int update(Bulletin bulletin);

    /**
     * 通过公共ID获取详情
     * @param bulletinId
     * @return
     */
    Map<String, Object> detail(int bulletinId);





}
