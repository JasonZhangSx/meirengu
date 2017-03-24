package com.meirengu.uc.service;
import com.meirengu.uc.model.Notify;
import com.meirengu.service.BaseService;

import java.util.Map;

/**
 * Notify服务接口 
 * @author 建新
 * @create Wed Mar 22 21:53:51 CST 2017
 */
public interface NotifyService extends BaseService<Notify>{

    /**
     * 生成notify以及userNotify
     * @param sender
     * @param content
     * @param type
     * @param target
     * @param targetType
     * @param action
     * @param receiver
     */
    void genMoreNotify(Integer sender, String content, Integer type, Integer target,
                          String targetType, String action, String receiver);

    /**
     * 生成点对点消息
     * @param sender
     * @param content
     * @param type
     * @param target
     * @param targeType
     * @param action
     * @param receiver
     * @return
     */
    boolean genNotify(Integer sender, String content, Integer type, Integer target,
                          String targeType, String action, Integer receiver);

    /**
     * 获取消息详情，notify和userNotify的合集
     * @param id
     * @return
     */
    Map<String, Object> getDetail(Integer id);
}
