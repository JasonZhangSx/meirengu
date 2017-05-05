package com.meirengu.uc.thread;

import com.meirengu.uc.common.Constants;
import com.meirengu.uc.dao.InviterDao;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.model.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huoyan403 on 5/4/2017.
 */
public class InitInviterThread implements Runnable{


    private User user;
    private UserDao userDao;
    private InviterDao inviterDao;

    public InitInviterThread(User user, UserDao userDao, InviterDao inviterDao) {
        this.user = user;
        this.userDao = userDao;
        this.inviterDao = inviterDao;
    }

    @Override
    public void run() {
        //初始化邀请关系
        User userInviter = userDao.retrieveByPhone(user.getMobileInviter());
        Inviter inviter = new Inviter();
        inviter.setUserId(userInviter.getUserId());
        inviter.setInvitedUserId(user.getUserId());
        inviter.setInvitedUserPhone(user.getPhone());
        inviter.setRegisterTime(new Date());
        inviter.setReward(new BigDecimal(Constants.ZERO_STRING));
        inviterDao.insert(inviter);
    }
}
