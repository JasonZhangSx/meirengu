package com.meirengu.uc.dao;
import com.meirengu.uc.model.UserNotify;
import com.meirengu.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserNotifyDao 
 * @author 建新
 * @create Wed Mar 22 21:53:51 CST 2017
 */
public interface UserNotifyDao extends BaseDao<UserNotify>{
    /**
     * 批量插入userNotify
     * @param unList
     * @return
     */
    int insertBatch(@Param("list") List<UserNotify> unList);

    /**
     * 根据用户id获取未读消息总数
     * @param userId
     * @return
     */
    int getNotReadCount(int userId);
}
