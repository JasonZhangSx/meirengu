package com.meirengu.news.service.impl;

import com.meirengu.news.model.Bulletin;
import com.meirengu.news.service.BulletinService;
import com.meirengu.news.dao.BulletinDao;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公告服务类
 * Created by maoruxin on 2017/3/11.
 */
@Service
public class BulletinServiceImpl implements BulletinService{

    private static Logger LOGGER = LoggerFactory.getLogger(BulletinServiceImpl.class);

    @Autowired
    private BulletinDao bulletinDao;
    @Autowired
    private PageService<Bulletin> pageService;

    /**
     * 新增公告
     * @param bulletinTitle
     * @param bulletinContent
     * @param operateAccount
     * @return
     * @throws Exception
     */
    @Override
    public int insert(String bulletinTitle,String bulletinContent,int operateAccount) throws Exception{
        Bulletin bulletin = new Bulletin();
        bulletin.setBulletinTitle(bulletinTitle);
        bulletin.setBulletinContent(bulletinContent);
        bulletin.setOperateAccount(operateAccount);
        bulletin.setCreateTime(new Date());
        try{
            return bulletinDao.insert(bulletin);
        }catch (Exception e){
            throw new Exception("insert article faile:", e);
        }
    }

    /**
     * 修改公告
     * @param bulletinId
     * @param bulletinTitle
     * @param bulletinContent
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public int update(int bulletinId,String bulletinTitle,String bulletinContent,int status) throws Exception {
        Bulletin bulletin = new Bulletin();
        bulletin.setBulletinId(bulletinId);
        bulletin.setBulletinTitle(bulletinTitle);
        bulletin.setBulletinContent(bulletinContent);
        bulletin.setStatus(status);
        try{
            return bulletinDao.update(bulletin);
        }catch (Exception e){
            throw new Exception("update article faile:", e);
        }
    }

    @Override
    public Page<Bulletin> getPageList(Page<Bulletin> page, Map map) {
        return pageService.getListByPage(page, map, bulletinDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, bulletinDao);
    }

}
