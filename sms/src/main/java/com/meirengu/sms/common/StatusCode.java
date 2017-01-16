package com.meirengu.sms.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态码常量类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:22
 */
public class StatusCode {
    public static final int INVALID_ARGUMENT = 1;
    public static final int MISSING_ARGUMENT = 2;
    public static final int QUERY_FAIL = 3;
    public static final int AUTH_FAIL = 4;
    public static final int SUBMIT_FAIL = 5;
    public static final int API_CALL_LIMIT = 6;
    public static final int OK = 200;
    public static final int ARGUMENT_MISSING = 1;
    public static final int BAD_ARGUMENT_FORMAT = 2;
    public static final int MONEY_NOT_ENOUGH = 3;
    public static final int BLACK_WORD = 4;
    public static final int TPL_NOT_FOUND = 5;
    public static final int ADD_TPL_FAILED = 6;
    public static final int TPL_NOT_VALID = 7;
    public static final int DUP_IN_SHORT_TIME = 8;
    public static final int TOO_MANY_TIME_IN_5 = 9;
    public static final int BLACK_PHONE_FILTER = 10;
    public static final int GET_METHOD_NOT_SUPPORT = 11;
    public static final int POST_METHOD_NOT_SUPPORT = 12;
    public static final int MARKET_FORBIDDEN = 13;
    public static final int DECODE_ERROR = 14;
    public static final int SIGN_NOT_MATCH = 15;
    public static final int BAD_SIGN_FORMAT = 16;
    public static final int DAY_LIMIT_PER_MOBILE = 17;
    public static final int SIGN_NOT_VALID = 18;
    public static final int REQUEST_NOT_VALID = 19;
    public static final int DECRYPT_ERROR = 21;
    public static final int REGION_NOT_SUPPORT = 20;
    public static final int HOUR_LIMIT_PER_MOBILE = 22;
    public static final int REGION_NOT_IN_TPL_LIST = 23;
    public static final int ADD_ALARM_SETTING_FAILED = 24;
    public static final int LENGTH_NOT_MATCH = 25;
    public static final int PACKAGE_ERROR = 26;
    public static final int NO_MONEY_FEE_TYPE_FAILED = 27;
    public static final int CARRIER_FAILED = 28;
    public static final int BAD_API_KEY = -1;
    public static final int API_NOT_ALLOWED = -2;
    public static final int IP_NOT_ALLOWED = -3;
    public static final int OVER_ACCESS_LIMIT = -4;
    public static final int OVER_ACCESS_RATE = -5;
    public static final int NOT_SUPPORT_BATCH = -6;
    public static final int UNKNOWN_EXCEPTION = -50;
    public static final int DB_OPERATION_FAIL = -51;
    public static final int RECHARGE_FAILED = -52;
    public static final int SUBMIT_SMS_FAILED = -53;
    public static final int RECORD_ALREADY_EXISTED = -54;
    public static final int RECORD_NOT_EXISTED = -55;
    public static final int PROM_FAILED = -56;
    public static final int SIGE_NOT_SET = -57;
    public static final int CAPTCHA_EXPIRE = -58;
    public static final Map<Integer, String> codeMsgMap = new HashMap();

    public StatusCode() {
    }

    public static String getErrorMsg(int code) {
        switch (code) {
            case 1:
                return "Invalid arguments";
            case 2:
                return "Missing required arguments";
            case 3:
                return "Query fail on server";
            case 4:
                return "Auth fail";
            case 5:
                return "Submit fail";
            case 6:
                return "Api call limit";
            default:
                return "Unknown error";
        }
    }

    static {
        codeMsgMap.put(Integer.valueOf(200), "OK");
        codeMsgMap.put(Integer.valueOf(1), "请求参数缺失");
        codeMsgMap.put(Integer.valueOf(2), "请求参数格式错误");
        codeMsgMap.put(Integer.valueOf(3), "账户余额不足");
        codeMsgMap.put(Integer.valueOf(4), "关键词屏蔽");
        codeMsgMap.put(Integer.valueOf(5), "未找到匹配的模板");
        codeMsgMap.put(Integer.valueOf(20), "暂不支持的国家地区");
        codeMsgMap.put(Integer.valueOf(23), "号码归属地不在模板可发送的地区内");
        codeMsgMap.put(Integer.valueOf(26), "流量包错误");
        codeMsgMap.put(Integer.valueOf(-1), "非法的apikey");
        codeMsgMap.put(Integer.valueOf(-2), "API没有权限");
        codeMsgMap.put(Integer.valueOf(-3), "IP没有权限");
        codeMsgMap.put(Integer.valueOf(-4), "访问次数超限");
        codeMsgMap.put(Integer.valueOf(-5), "访问频率超限");
        codeMsgMap.put(Integer.valueOf(-53), "提交短信失败");
        codeMsgMap.put(Integer.valueOf(-54), "记录已经存在");
        codeMsgMap.put(Integer.valueOf(-55), "记录不存在");
        codeMsgMap.put(Integer.valueOf(6), "添加模板失败");
        codeMsgMap.put(Integer.valueOf(7), "模板不可用");
        codeMsgMap.put(Integer.valueOf(8), "同一手机号30秒内重复提交相同的内容");
        codeMsgMap.put(Integer.valueOf(9), "同一手机号5分钟内重复提交相同的内容超过3次");
        codeMsgMap.put(Integer.valueOf(10), "手机号黑名单过滤");
        codeMsgMap.put(Integer.valueOf(11), "接口不支持GET方式调用");
        codeMsgMap.put(Integer.valueOf(12), "接口不支持POST方式调用");
        codeMsgMap.put(Integer.valueOf(13), "营销短信暂停发送");
        codeMsgMap.put(Integer.valueOf(14), "解码失败");
        codeMsgMap.put(Integer.valueOf(15), "签名不匹配");
        codeMsgMap.put(Integer.valueOf(16), "签名格式不正确");
        codeMsgMap.put(Integer.valueOf(17), "24小时内同一手机号发送次数超过限制");
        codeMsgMap.put(Integer.valueOf(18), "签名校验失败");
        codeMsgMap.put(Integer.valueOf(19), "请求已失效");
        codeMsgMap.put(Integer.valueOf(21), "解密失败");
        codeMsgMap.put(Integer.valueOf(-50), "未知异常");
        codeMsgMap.put(Integer.valueOf(-51), "系统繁忙");
        codeMsgMap.put(Integer.valueOf(-52), "充值失败");
        codeMsgMap.put(Integer.valueOf(-56), "赠送失败");
        codeMsgMap.put(Integer.valueOf(-57), "用户开通过固定签名功能，但签名未设置");
        codeMsgMap.put(Integer.valueOf(-58), "验证码已过期");
    }
}
