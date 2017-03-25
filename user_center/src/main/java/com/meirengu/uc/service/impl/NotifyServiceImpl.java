package com.meirengu.uc.service.impl;
import com.meirengu.exception.BusinessException;
import com.meirengu.uc.common.Constants;
import com.meirengu.uc.dao.NotifyDao;
import com.meirengu.uc.model.Notify;
import com.meirengu.uc.model.UserNotify;
import com.meirengu.uc.service.NotifyService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.service.UserNotifyService;
import com.meirengu.utils.ObjectUtils;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Notify服务实现层 
 * @author 建新
 * @create Wed Mar 22 21:53:51 CST 2017
 */
@Service
public class NotifyServiceImpl extends BaseServiceImpl<Notify> implements NotifyService{

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyServiceImpl.class);

    @Autowired
    NotifyDao notifyDao;
    @Autowired
    UserNotifyService userNotifyService;

    @Override
    public void genMoreNotify(Integer sender, String content, Integer type, Integer target, String targetType, String
            action, String receiver) {
        try {
            Notify n = new Notify();
            n.setContent(content);
            n.setType(type);
            if(target == null){
                target = 0;
            }
            if(StringUtil.isEmpty(targetType)){
                targetType = "";
            }
            if(StringUtil.isEmpty(action)){
                action = "";
            }
            n.setTarget(target);
            n.setTargetType(targetType);
            n.setAction(action);
            n.setSender(sender);
            n.setCreateTime(new Date());
            int insertNum = notifyDao.insert(n);
            if(insertNum != 1){
                //LOGGER.error(">> insert notify error... return num is {}", insertNum);
                throw new BusinessException(">> insert notify throw exception");
            }
            if(StringUtil.isEmpty(receiver)){
                throw new BusinessException("NotifyServiceImpl.genNotify receiver is null");
            }

            String[] receivers = receiver.split(",");
            List<UserNotify> unList = new ArrayList<>();
            List<List<UserNotify>> lists = new ArrayList<>();
            for (String rc : receivers){
                UserNotify un = new UserNotify();
                un.setNotifyId(n.getId());
                if(StringUtil.isEmpty(rc)){
                    continue;
                }
                un.setUserId(Integer.parseInt(rc));
                un.setIsRead(Constants.NOTIFY_UNREAD);
                un.setCreateTime(new Date());
                unList.add(un);
            }

            //每次插入的记录
            int perCount = 10000;
            double count = Math.ceil((double) unList.size()/perCount);

            for(int i = 0; i < count; i++){
                List<UserNotify> ulist;
                if(i == count-1){
                    ulist = unList.subList(i*perCount, unList.size());
                }else {
                    ulist = unList.subList(i*perCount, i*perCount+perCount);
                }

                int unNum = userNotifyService.insertBatch(ulist);
                if(unNum == ulist.size()){
                    LOGGER.info(">> insert userNotify success....");
                }else if(unNum != ulist.size() && unNum > 0){
                    LOGGER.warn(">> insert userNotify success, but lose some... need send num is {}, " +
                            "in fact return num is {}", new Object[]{unList.size(), unNum});
                }else {
                    throw new BusinessException(">> insert user notify error");
                }
            }
        }catch (Exception e){
            throw new BusinessException("NotifyServiceImpl.genNotify throw exception:", e);
        }
    }

    @Transactional
    public boolean genNotify(Integer sender, String content, Integer type, Integer target,
                          String targetType, String action, Integer receiver){
        try {
            Notify n = new Notify();
            n.setContent(content);
            n.setType(type);
            if(target == null){
                target = 0;
            }
            if(StringUtil.isEmpty(targetType)){
                targetType = "";
            }
            if(StringUtil.isEmpty(action)){
                action = "";
            }
            n.setTarget(target);
            n.setTargetType(targetType);
            n.setAction(action);
            n.setSender(sender);
            n.setCreateTime(new Date());
            int insertNum = notifyDao.insert(n);
            if(insertNum != 1){
                //LOGGER.error(">> insert notify error... return num is {}", insertNum);
                throw new BusinessException("NotifyServiceImpl.genNotify insert notify throw exception");
            }
            if(StringUtil.isEmpty(receiver)){
                throw new BusinessException("NotifyServiceImpl.genNotify receiver is null");
            }

            UserNotify un = new UserNotify();
            un.setNotifyId(n.getId());
            un.setUserId(receiver);
            un.setIsRead(Constants.NOTIFY_UNREAD);
            un.setCreateTime(new Date());

            int unInsertNum = userNotifyService.insert(un);
            if(unInsertNum != 1){
                throw new BusinessException("NotifyServiceImpl.genNotify insert user_notify throw exception");
            }

            return true;

        }catch (Exception e){
            throw new BusinessException("NotifyServiceImpl.genNotify throw exception:", e);
        }
    }

    @Override
    public Map<String, Object> getDetail(Integer id) {
        UserNotify un = userNotifyService.detail(id);
        if(un == null){
            LOGGER.warn(">> get userNotify is null... id is {}", id);
            return null;
        }
        int notifyId = un.getNotifyId();
        Notify n = this.detail(notifyId);
        if(n == null){
            LOGGER.warn(">> get notify is null... notifyId is {}", notifyId);
            return null;
        }
        try {
            Map<String, Object> unMap = ObjectUtils.bean2Map(un);
            Map<String, Object> nMap = ObjectUtils.bean2Map(n);
            nMap.remove("id");
            nMap.remove("createTime");
            nMap.putAll(unMap);
            un.setIsRead(Constants.NOTIFY_READ);
            int updateNum = userNotifyService.update(un);
            if(updateNum != 1){
                LOGGER.warn(">> update user notify status error....");
            }
            return nMap;
        } catch (IllegalAccessException e) {
            throw new BusinessException("NotifyServiceImpl.getDetail throw exception:{}",e);
        }
    }


}
