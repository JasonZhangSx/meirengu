package com.meirengu.sms.dao;

import com.meirengu.sms.model.SmsRecord;

/**
 * 短信记录数据访问对象类
 *
 * @author Marvin
 * @create 2017-01-12 下午7:30
 */
public interface SmsRecordDao {

    /**
     * 新增验证码
     *
     * @param smsRecord
     * @return
     */
    int create(SmsRecord smsRecord);

}
