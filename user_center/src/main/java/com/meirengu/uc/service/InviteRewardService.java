package com.meirengu.uc.service;

import com.meirengu.service.BaseService;
import com.meirengu.uc.model.Inviter;

/**
 * Created by huoyan403 on 3/23/2017.
 */
public interface InviteRewardService extends BaseService<Inviter>{

    //处理order文件 并生成批处理user文件
    Boolean inviteReward(String fileName,String filePath);

    void noticePayment();
}
