package com.meirengu.uc.service.impl;
import com.meirengu.uc.dao.UserNotifyDao;
import com.meirengu.uc.model.UserNotify;
import com.meirengu.uc.service.UserNotifyService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserNotify服务实现层 
 * @author 建新
 * @create Wed Mar 22 21:53:51 CST 2017
 */
@Service
public class UserNotifyServiceImpl extends BaseServiceImpl<UserNotify> implements UserNotifyService{

    @Autowired
    UserNotifyDao userNotifyDao;

    @Override
    public int insertBatch(List<UserNotify> unList) {
        return userNotifyDao.insertBatch(unList);
    }
}
