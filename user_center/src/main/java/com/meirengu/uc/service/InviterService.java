package com.meirengu.uc.service;

import com.meirengu.service.BaseService;
import com.meirengu.uc.model.Inviter;

import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/23/2017.
 */
public interface InviterService extends BaseService<Inviter>{

    Inviter detail(Inviter inviter);

    void getReward(List<Map<String, Object>> list);
}
