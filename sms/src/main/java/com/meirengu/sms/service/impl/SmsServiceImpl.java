package com.meirengu.sms.service.impl;

import com.meirengu.sms.service.SmsService;
import com.meirengu.sms.utils.ConfigUtil;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendSingleSmsInfo;
import com.yunpian.sdk.service.SmsOperator;
import com.yunpian.sdk.service.YunpianRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 短信服务实现类
 *
 * @author Marvin
 * @create 2017-01-15 上午10:34
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Override
    public ResultDO<SendSingleSmsInfo> singleSend(String mobile, String text) {
        logger.info("SmsServiceImpl.singleSend params >> mobile:{}, text:{}", new Object[]{mobile, text});
        YunpianRestClient client = new YunpianRestClient(ConfigUtil.getConfig("API_KEY_YUNPIAN"));
        SmsOperator smsOperator = client.getSmsOperator();
        ResultDO<SendSingleSmsInfo> result = smsOperator.singleSend(mobile, text);
        logger.info("SmsServiceImpl.singleSend result << {}", result);
        return result;
    }

    @Override
    public ResultDO<SendSingleSmsInfo> tplSingleSend(String mobile, Long tplId, String tplValue) {
        logger.info("SmsServiceImpl.tplSingleSend params >> mobile:{}, tplId:{}, tplValue:{}", new Object[]{mobile, tplId, tplValue});
        YunpianRestClient client = new YunpianRestClient(ConfigUtil.getConfig("API_KEY_YUNPIAN"));
        SmsOperator smsOperator = client.getSmsOperator();
        ResultDO<SendSingleSmsInfo> result = smsOperator.tplSingleSend(mobile, String.valueOf(tplId), tplValue);
        logger.info("SmsServiceImpl.tplSingleSend result << {}", result);
        return result;
    }
}
