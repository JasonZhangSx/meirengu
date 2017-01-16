package com.meirengu.sms.service;

import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendSingleSmsInfo;

/**
 * 短信服务接口类
 *
 * @author Marvin
 * @create 2017-01-15 上午10:29
 */
public interface SmsService {

    /**
     * 单条发送
     * @param mobile
     * @param text
     * @return
     */
    ResultDO<SendSingleSmsInfo> singleSend(String mobile, String text);

    /**
     * 指定模板单发（不推荐使用，新用户请用智能匹配发送）
     * @param mobile
     * @param tplId
     * @param tplValue
     * @return
     */
    ResultDO<SendSingleSmsInfo> tplSingleSend(String mobile, Long tplId, String tplValue);

}
