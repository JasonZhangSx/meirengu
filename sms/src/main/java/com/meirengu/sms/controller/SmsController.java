package com.meirengu.sms.controller;

import com.meirengu.sms.common.StatusCode;
import com.meirengu.sms.model.Captcha;
import com.meirengu.sms.model.SmsRecord;
import com.meirengu.sms.service.CaptchaService;
import com.meirengu.sms.service.SmsRecordService;
import com.meirengu.sms.service.SmsService;
import com.meirengu.sms.utils.ConfigUtil;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendSingleSmsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;

/**
 * 短信接口类
 *
 * @author Marvin
 * @create 2017-01-11 下午6:06
 */
@RestController
public class SmsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    SmsService smsService;
    @Autowired
    CaptchaService captchaService;
    @Autowired
    SmsRecordService smsRecordService;

    /**
     * 单条发送
     * @param apikey 用户唯一标识
     * @param mobile 接收的手机号，仅支持单号码发送
     * @param text 短信内容
     * @param extend 下发号码扩展号，纯数字
     * @param uid 该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号
     * @param callback_url 短信发送后将向这个地址推送(运营商返回的)发送报告。
     * @return
     */
    @RequestMapping(value = "singleSend", method = RequestMethod.POST)
    public Map<String, Object> singleSend(@RequestParam(required = true)String apikey,
                                          @RequestParam(required = true)String mobile,
                                          @RequestParam(required = true)String text,
                                          @RequestParam(required = false) String extend,
                                          @RequestParam(required = false) Long uid,
                                          @RequestParam(required = false) String ip,
                                          @RequestParam(required = false) String callback_url){

        logger.info("SmsController.tplSendSms params >> apikey:{},mobile:{},text:{},extend:{},uid:{},ip:{},callback_url:{}", new Object[]{apikey,mobile,text,extend,uid,ip,callback_url});
        //verify params
        if(!ConfigUtil.getConfig("API_KEY_MEIRENGU").equals(apikey)){
            return super.setReturnMsg(StatusCode.BAD_API_KEY, null, StatusCode.codeMsgMap.get(StatusCode.BAD_API_KEY));
        }
        //invoke sms service
        ResultDO<SendSingleSmsInfo> resultDO = smsService.singleSend(mobile, text);
        if (resultDO.isSuccess()){
            //store db
            return super.setReturnMsg(StatusCode.OK,resultDO.getData(),StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setReturnMsg(StatusCode.UNKNOWN_EXCEPTION,resultDO.getData(),StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 指定模版单发
     * @param apikey 用户唯一标识
     * @param mobile 接收的手机号
     * @param tpl_id 模板id
     * @param tpl_value 变量名和变量值对，必须urlencode再传递
     * @param extend 扩展号
     * @param uid 用户自定义唯一id
     * @return
     */
    @RequestMapping(value = "tplSingleSend", method = RequestMethod.POST)
    public Map<String, Object> tplSingleSend(@RequestParam(required = true)String apikey,
                                             @RequestParam(required = true)String mobile,
                                             @RequestParam(required = true)Long tpl_id,
                                             @RequestParam(required = true)String tpl_value,
                                             @RequestParam(required = false) String extend,
                                             @RequestParam(required = false) String uid,
                                             @RequestParam(required = false) String ip){

        logger.info("SmsController.tplSendSms params >> apikey:{},mobile:{},tpl_id:{},tpl_value:{},extend:{},uid:{},ip:{}", new Object[]{apikey,mobile,tpl_id,tpl_value,extend,uid,ip});
        //verify params
        if(!ConfigUtil.getConfig("API_KEY_MEIRENGU").equals(apikey)){
            return super.setReturnMsg(StatusCode.BAD_API_KEY, null, StatusCode.codeMsgMap.get(StatusCode.BAD_API_KEY));
        }
        //invoke sms service
        ResultDO<SendSingleSmsInfo> resultDO = smsService.tplSingleSend(mobile, tpl_id, tpl_value);
        if (resultDO.isSuccess()){
            int captchaCreateResult = -1;
            if (tpl_id != null && (tpl_id.longValue() == 1690670 || tpl_id.longValue() ==1690714)){
                Captcha captcha = this.wrapCaptcha(mobile, tpl_id, tpl_value, uid,ip);
                if (captcha != null){
                    captchaCreateResult = captchaService.create(captcha);
                }
            }
            int smsRecordCreateResult = smsRecordService.create(this.wrapSmsRecord(resultDO,tpl_value,uid,ip));
            logger.info("SmsController.tplSendSms result << apikey:{},mobile:{},tpl_id:{}, resultDO:{}, captchaCreateResult:{}, smsRecordCreateResult:{}", new Object[]{apikey,mobile,tpl_id,resultDO,captchaCreateResult,smsRecordCreateResult});
            //store db sms_record
            return super.setReturnMsg(StatusCode.OK,resultDO.getData(),StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setReturnMsg(StatusCode.UNKNOWN_EXCEPTION,null,StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 包装验证码类
     * @param mobile
     * @param tpl_id
     * @param tpl_value
     * @param uid
     * @param ip
     * @return
     */
    private Captcha wrapCaptcha(String mobile, Long tpl_id, String tpl_value, String uid, String ip){
        try{
            Captcha captcha = new Captcha();
            captcha.setMobile(mobile);
            captcha.setUid(uid);
            captcha.setIp(ip);
            captcha.setUse(false);
            int code = -1;
            Date createTime = new Date();
            Date expireTime = null;
            captcha.setCreateTime(createTime);
            if (tpl_id == 1690670){
                code = Integer.valueOf(getTplValue(tpl_value,"#code#"));
                expireTime = new Date(createTime.getTime() + Long.valueOf(ConfigUtil.getConfig("EXPIRE_CAPTCHA_LOGIN")));
            }else if(tpl_id == 1690714){
                code = Integer.valueOf(getTplValue(tpl_value,"#service_sn#"));
                expireTime = new Date(createTime.getTime() + Long.valueOf(ConfigUtil.getConfig("EXPIRE_CAPTCHA_SERIVICE")));
            }
            captcha.setCode(code);
            captcha.setExpireTime(expireTime);
            return captcha;
        }catch (Exception e){
            logger.error("SmsController.wrapCaptcha error : {}", e);
        }
        return null;
    }

    /**
     * 包装短信记录类
     * @param resultDO
     * @param text
     * @param uid
     * @param ip
     * @return
     */
    private SmsRecord wrapSmsRecord(ResultDO<SendSingleSmsInfo> resultDO, String text, String uid, String ip){
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setSid(resultDO.getData().getSid());
        smsRecord.setMobile(resultDO.getData().getMobile());
        smsRecord.setText(text);
        smsRecord.setCode(resultDO.getData().getCode());
        smsRecord.setMsg(resultDO.getData().getMsg());
        smsRecord.setCount(resultDO.getData().getCount());
        smsRecord.setFee(resultDO.getData().getFee());
        smsRecord.setUid(uid);
        smsRecord.setIp(ip);
        return smsRecord;
    }

    /**
     * 获取模版占位符值
     * @param tplValueText
     * @param key
     * @return
     */
    private String getTplValue(String tplValueText, String key){
        try{
            String [] keyValuePairs = tplValueText.split("&");
            for (int i = 0; i < keyValuePairs.length; i++){
                String [] keyValuePair = keyValuePairs[i].split("=");
                for (int j = 0; j < keyValuePair.length; j++){
                    if (key.equals(keyValuePair[0])){
                        return keyValuePair[1];
                    }
                }
            }
        }catch (Exception e){
            logger.error("SmsController.getTplValue error: {}" ,e);
        }
        return "";
    }

}
