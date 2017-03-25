package com.meirengu.uc.service.impl;

import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.InviterDao;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huoyan403 on 3/23/2017.
 */
@Service
public class InviterServiceimpl extends BaseServiceImpl<Inviter> implements InviterService{

    @Autowired
    private InviterDao inviterDao;

}
