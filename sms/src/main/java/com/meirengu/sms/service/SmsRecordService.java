package com.meirengu.sms.service;

import com.meirengu.sms.model.SmsRecord;
import org.springframework.stereotype.Service;
/**
 * 短信记录服务接口类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:26
 */
public interface SmsRecordService {
    /**
     * 新增验证码
     *
     * @param smsRecord
     * @return
     */
    int create(SmsRecord smsRecord);

}
