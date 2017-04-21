package com.meirengu.uc.service;
import com.meirengu.uc.model.UserNotify;
import com.meirengu.service.BaseService;

import java.util.List;

/**
 * UserNotify服务接口 
 * @author 建新
 * @create Wed Mar 22 21:53:51 CST 2017
 */
public interface UserNotifyService extends BaseService<UserNotify>{
    /**
     * 批量插入userNotify
     * @param unList
     * @return
     */
    int insertBatch(List<UserNotify> unList);
    /**
     * 根据用户id获取未读消息总数
     * @param userId
     * @return
     */
    int getNotReadCount(int userId);
}
