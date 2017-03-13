package com.meirengu.news.service;

import com.meirengu.news.model.Bulletin;


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
    int insert(String bulletinTitle,String bulletinContent,int operateAccount) throws Exception;

    /**
     * 修改公告
     * @param bulletinId
     * @param bulletinTitle
     * @param bulletinContent
     * @param status
     * @return
     * @throws Exception
     */
    int update(int bulletinId,String bulletinTitle,String bulletinContent,int status) throws Exception;





}
