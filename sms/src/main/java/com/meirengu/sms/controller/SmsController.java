package com.meirengu.sms.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.sms.model.SmsRecord;
import com.meirengu.sms.service.SmsRecordService;
import com.meirengu.sms.service.SmsService;
import com.meirengu.sms.utils.TemplateConfigUtil;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendSingleSmsInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    SmsRecordService smsRecordService;

    /**
     * 单条发送
     *
     * @param mobile 接收的手机号，仅支持单号码发送
     * @param text   短信内容
     * @param extend 下发号码扩展号，纯数字
     * @param uid    该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号
     * @param ip     调用ip
     * @return
     */
    @RequestMapping(value = "single_send", method = RequestMethod.POST)
    public Result singleSend(@RequestParam(value = "mobile", required = true) String mobile,
                             @RequestParam(value = "text", required = true) String text,
                             @RequestParam(value = "extend", required = false) String extend,
                             @RequestParam(value = "uid", required = false) String uid,
                             @RequestParam(value = "ip", required = false) String ip) {
        logger.info("SmsController.singleSend params >> mobile:{},text:{},extend:{},uid:{},ip:{}",
                new Object[]{mobile, text, extend, uid, ip});
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if (StringUtils.isEmpty(text)) {
            return super.setResult(StatusCode.SMS_TEXT_IS_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode
                    .SMS_TEXT_IS_EMPTY));
        }
        //防刷逻辑
        //...
        //invoke sms service
        ResultDO<SendSingleSmsInfo> resultDO = smsService.singleSend(mobile, text);
        if (resultDO != null && resultDO.isSuccess()) {
            //store db 优化改成异步
            smsRecordService.create(this.wrapSmsRecord(resultDO, text, uid, ip));
            logger.info("SmsController.singleSend result << mobile:{},text:{}, resultDO:{}", new
                    Object[]{mobile, text, resultDO});
            return super.setResult(StatusCode.OK, resultDO.getData(), StatusCode.codeMsgMap.get(StatusCode.OK));
        } else {
            return super.setResult(StatusCode.SUBMIT_SMS_FAILED, resultDO.getData(), StatusCode.codeMsgMap.get
                    (StatusCode.SUBMIT_SMS_FAILED));
        }
    }

    /**
     * 按我们自己的模板发送单条短信
     * @param mobile  手机号
     * @param tpl_id  sms-template.properties 中指定的模板的key
     * @param extend 扩展字段
     * @param uid 业务对象
     * @param ip 请求ip
     * @param request
     * @return
     */
    @RequestMapping(value = "single_send_tpl", method = RequestMethod.POST)
    @ResponseBody
    public Result singleSendTpl(@RequestParam(value = "mobile", required = true) String mobile,
                             @RequestParam(value = "tpl_id", required = true) Long tpl_id,
                             @RequestParam(value = "extend", required = false) String extend,
                             @RequestParam(value = "uid", required = false) String uid,
                             @RequestParam(value = "ip", required = false) String ip,
                             HttpServletRequest request) {

        try {
            String templateStr = TemplateConfigUtil.getConfig(String.valueOf(tpl_id));

            if(StringUtil.isEmpty(templateStr)){
                return super.setResult(StatusCode.TEMPLATE_NOT_VALID, "", StatusCode.codeMsgMap.get(StatusCode.TEMPLATE_NOT_VALID));
            }

            List<String> list = extractMessageByRegular(templateStr);
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                String key = list.get(i);
                String value = request.getParameter(list.get(i));
                if (StringUtil.isEmpty(value)) {
                    return super.setResult(StatusCode.MISSING_ARGUMENT, "", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
                }
                if (!map.containsKey(key)) {
                    map.put(key, value);
                }
            }

            for (String key : map.keySet()) {
                String value = map.get(key);
                templateStr = templateStr.replace("#" + key + "#", value);
            }

            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                        .MOBILE_FORMAT_ERROR));
            }
            //防刷逻辑
            //...
            //invoke sms service
            ResultDO<SendSingleSmsInfo> resultDO = smsService.singleSend(mobile, templateStr);
            if (resultDO != null && resultDO.isSuccess()) {
                //store db 优化改成异步
                smsRecordService.create(this.wrapSmsRecord(resultDO, templateStr, uid, ip));
                logger.info("SmsController.singleSend result << mobile:{},text:{}, resultDO:{}", new
                        Object[]{mobile, templateStr, resultDO});
                return super.setResult(StatusCode.OK, resultDO.getData(), StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                return super.setResult(StatusCode.SUBMIT_SMS_FAILED, resultDO.getData(), StatusCode.codeMsgMap.get
                        (StatusCode.SUBMIT_SMS_FAILED));
            }
        }catch (Exception e){
            logger.error("SmsController.singleSend throw exception: ", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 指定模版单发
     *
     * @param mobile    接收的手机号
     * @param tpl_id    模板id
     * @param tpl_value 变量名和变量值对，必须urlencode再传递
     * @param extend    下发号码扩展号，纯数字
     * @param uid       该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号
     * @param ip        调用ip
     * @return
     */
    @RequestMapping(value = "tpl_single_send", method = RequestMethod.POST)
    public Result tplSingleSend(@RequestParam(value = "mobile", required = true) String mobile,
                                @RequestParam(value = "tpl_id", required = true) Long tpl_id,
                                @RequestParam(value = "tpl_value", required = true) String tpl_value,
                                @RequestParam(value = "extend", required = false) String extend,
                                @RequestParam(value = "uid", required = false) String uid,
                                @RequestParam(value = "ip", required = false) String ip) {
        logger.info("SmsController.tplSingleSend params >> mobile:{},tpl_id:{},tpl_value:{},extend:{},uid:{}," +
                "ip:{}", new Object[]{mobile, tpl_id, tpl_value, extend, uid, ip});
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if (tpl_id == null) {
            return super.setResult(StatusCode.TEMPLATE_IS_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode
                    .TEMPLATE_IS_EMPTY));
        }
        if (StringUtils.isEmpty(tpl_value)) {
            return super.setResult(StatusCode.TEMPLATE_IS_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode
                    .TEMPLATE_IS_EMPTY));
        }
        //防刷逻辑
        //...
        //invoke sms service
        ResultDO<SendSingleSmsInfo> resultDO = smsService.tplSingleSend(mobile, tpl_id, tpl_value);
        if (resultDO != null && resultDO.isSuccess()) {
            //store db 优化改成异步
            smsRecordService.create(this.wrapSmsRecord(resultDO, tpl_value, uid, ip));
            logger.info("SmsController.tplSingleSend result << mobile:{}, tpl_id:{}, resultDO:{}", new
                    Object[]{mobile, tpl_id, resultDO});
            //store db sms_record
            return super.setResult(StatusCode.OK, resultDO.getData(), StatusCode.codeMsgMap.get(StatusCode.OK));
        } else {
            return super.setResult(StatusCode.SUBMIT_SMS_FAILED, resultDO.getData(), StatusCode.codeMsgMap.get(StatusCode
                    .SUBMIT_SMS_FAILED));
        }
    }


    /**
     * 包装短信记录类
     *
     * @param resultDO
     * @param text
     * @param uid
     * @param ip
     * @return
     */
    private SmsRecord wrapSmsRecord(ResultDO<SendSingleSmsInfo> resultDO, String text, String uid, String ip) {
        if (resultDO != null && resultDO.getData() != null) {
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
        } else {
            return null;
        }

    }

    public static List<String> extractMessageByRegular(String msg){
        List<String> list=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\#[^\\#]*\\#)");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(1, m.group().length()-1));
        }
        return list;
    }

}
