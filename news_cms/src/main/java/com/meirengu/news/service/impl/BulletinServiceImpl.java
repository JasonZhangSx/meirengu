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
    public int insert(String bulletinTitle,String bulletinContent,String operateAccount){
        Bulletin bulletin = new Bulletin();
        bulletin.setBulletinTitle(bulletinTitle);
        bulletin.setBulletinContent(bulletinContent);
        bulletin.setOperateAccount(operateAccount);
        bulletin.setCreateTime(new Date());
        return bulletinDao.insert(bulletin);
    }

    /**
     * 修改公告
     * @param bulletin
     * @return
     * @throws Exception
     */
    @Override
    public int update(Bulletin bulletin) {
        return bulletinDao.update(bulletin);
    }

    @Override
    public Page<Bulletin> getPageList(Page<Bulletin> page, Map map) {
        return pageService.getListByPage(page, map, bulletinDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, bulletinDao);
    }

    /**
     * 通过公共ID获取详情
     * @param bulletinId
     * @return
     */
    public Map<String, Object> detail(int bulletinId) {
        return bulletinDao.getById(bulletinId);
    }

}
