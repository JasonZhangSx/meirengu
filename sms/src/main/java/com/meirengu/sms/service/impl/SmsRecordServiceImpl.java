package com.meirengu.sms.service.impl;

import com.meirengu.sms.dao.SmsRecordDao;
import com.meirengu.sms.model.SmsRecord;
import com.meirengu.sms.service.SmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信记录服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:28
 */
@Service
public class SmsRecordServiceImpl implements SmsRecordService{

    @Autowired
    SmsRecordDao smsRecordDao;

    @Override
    public int create(SmsRecord smsRecord) {
        return smsRecordDao.create(smsRecord);
    }
}
