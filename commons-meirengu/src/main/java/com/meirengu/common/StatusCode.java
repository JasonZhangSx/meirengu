package com.meirengu.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局状态码常量类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:22
 */
public class StatusCode {
    //成功(2字头)
    public static final int OK = 200;
    //客户端请求错误(4字头)
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int REQUEST_URI_TOO_LONG = 414;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int UPGRADE_REQUIRED = 426;
    //40000-40100//commons
    public static final int INVALID_ARGUMENT = 40001;
    public static final int MISSING_ARGUMENT = 40002;
    public static final int BAD_API_KEY = 40003;
    public static final int API_NOT_ALLOWED = 40004;
    public static final int IP_NOT_ALLOWED = 40005;
    public static final int OVER_ACCESS_LIMIT = 40006;
    public static final int OVER_ACCESS_RATE = 40007;
    public static final int SIGN_NOT_MATCH = 40008;
    public static final int BAD_SIGN_FORMAT = 40009;
    public static final int SIGN_NOT_VALID = 40010;
    public static final int DECODE_ERROR = 40011;
    public static final int DECRYPT_ERROR = 40012;
    public static final int CAPTCHA_EXPIRE = 40013;
    public static final int CAPTCHA_INVALID = 40014;
    public static final int PARAMETER_FORMAT_ERROR = 40015;
    public static final int MOBILE_FORMAT_ERROR = 40016;
    public static final int MOBILE_IS_NOT_EXITS = 40017;
    public static final int PASSWORD_IS_ERROR = 40018;
    public static final int MOBILE_IS_EXITS = 40019;
    //40100-40200//user_center
    public static final int TOKEN_IS_TIMEOUT = 40101;



    //40200-40300//news_cms

    //40300-40400//medical_beauty
    public static final int MB_CORRECT = 40300;
    public static final int MB_ERROR_SELECT = 40301;
    public static final int MB_ERROR_INSERT = 40302;
    public static final int MB_ERROR_UPDATE = 40303;
    public static final int MB_ERROR_DELETE = 40304;
    public static final int MB_ERROR = 40305;
    //40400-40500//shopping_mall
    public static final int SERVICE_CODE_USED = 40400;
    public static final int SERVICE_CODE_NOT_EXISTED = 40401;
    public static final int HOSPITAL_NOT_PERMITTED = 40402;
    public static final int SERVICE_CODE_EXPIRE = 40403;
    //40500-40600//crowd_funding

    //40600-40700//payment

    //40700-40700//sms
    public static final int ACCOUNT_BALANCE_LESS = 40701;
    public static final int KEYWORD_MATCHING = 40702;
    public static final int TEMPLATE_NOT_MATCH = 40703;
    public static final int PACKAGE_ERROR = 40704;
    public static final int DUP_IN_SHORT_TIME = 40705;
    public static final int TOO_MANY_TIME_IN_5 = 40706;
    public static final int BLACK_PHONE_FILTER = 40707;
    public static final int DAY_LIMIT_PER_MOBILE = 40708;
    public static final int SMS_TEXT_IS_EMPTY = 40709;
    public static final int TEMPLATE_IS_EMPTY = 40710;

    //40800-40900//other

    //服务器错误(5字头)
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;
    //50000-50100//commons
    public static final int UNKNOWN_EXCEPTION = 50000;
    public static final int DB_OPERATION_FAIL = 50001;
    public static final int RECORD_ALREADY_EXISTED = 50002;
    public static final int RECORD_NOT_EXISTED = 50003;


    //50100-50200//user_center
    public static final int CHECK_CODE_SEND_ERROR = 50101;
    public static final int CHECK_CODE_AND_PASSWORD_NOT_EMPTY = 50102;

    //50200-50300//admin_dashboard
    public static final int AD_LONGIN_SUCCESS = 50200;
    public static final int AD_LONGIN_FIAL = 50201;
    //50200-50300//news_cms
    public static final int FEEDBACK_ERROR_INSERT = 50200;
    public static final int BULLETIN_ERROR_INSERT = 50201;
    public static final int FEEDBACK_CONTENT_OUTOF = 50202;
    public static final int FEEDBACK_CONTENT_COUNT_OUTOF = 50203;
    public static final int BULLETIN_ERROR_LIST = 50204;

    //50300-50400//medical_beauty

    //50400-50500//shopping_mall

    //50500-50600//crowd_funding
    public static final int ITEM_ERROR_INSERT = 50500;
    public static final int ITEM_ERROR_UPDATE = 50501;
    //50600-50700//payment

    //50700-50800//sms
    public static final int SUBMIT_SMS_FAILED = 50700;
    public static final int TEMPLATE_NOT_VALID = 50701;
    public static final int MARKET_FORBIDDEN = 50702;



    //50800-50900//other


    public static final Map<Integer, String> codeMsgMap = new HashMap();

    public StatusCode() {
    }

    public static String getErrorMsg(int code) {
        switch (code) {
            case StatusCode.INVALID_ARGUMENT:
                return "请求参数有误";
            case StatusCode.MISSING_ARGUMENT:
                return "请求参数缺失";
            case StatusCode.UNAUTHORIZED:
                return "当前的请求需要验证";
            case StatusCode.API_NOT_ALLOWED:
                return "API没有权限";
            default:
                return "未知异常";
        }
    }

    static {
        //成功(2字头)
        codeMsgMap.put(StatusCode.OK, "成功");
        //客户端请求错误(4字头)
        codeMsgMap.put(StatusCode.BAD_REQUEST, "无效的请求");
        codeMsgMap.put(StatusCode.UNAUTHORIZED, "当前的请求需要验证");
        codeMsgMap.put(StatusCode.FORBIDDEN, "服务器禁止请求");
        codeMsgMap.put(StatusCode.NOT_FOUND, "请求的资源没有找到");
        codeMsgMap.put(StatusCode.REQUEST_TIMEOUT, "请求超时");
        codeMsgMap.put(StatusCode.REQUEST_ENTITY_TOO_LARGE, "请求提交的实体数据大小超过了服务器能够处理的范围");
        codeMsgMap.put(StatusCode.REQUEST_URI_TOO_LONG, "请求的URI长度超过了服务器能够解释的长度");
        codeMsgMap.put(StatusCode.UNSUPPORTED_MEDIA_TYPE, "请求中提交的实体并不是服务器中所支持的格式");
        codeMsgMap.put(StatusCode.UPGRADE_REQUIRED, "客户端应当切换到TLS/1.0");
        //40000-40100//commons
        codeMsgMap.put(StatusCode.INVALID_ARGUMENT, "请求参数有误");
        codeMsgMap.put(StatusCode.MISSING_ARGUMENT, "请求参数缺失");
        codeMsgMap.put(StatusCode.BAD_API_KEY, "非法的apikey");
        codeMsgMap.put(StatusCode.API_NOT_ALLOWED, "API没有权限");
        codeMsgMap.put(StatusCode.IP_NOT_ALLOWED, "IP没有权限");
        codeMsgMap.put(StatusCode.OVER_ACCESS_LIMIT, "访问次数超限");
        codeMsgMap.put(StatusCode.OVER_ACCESS_RATE, "访问频率超限");
        codeMsgMap.put(StatusCode.SIGN_NOT_MATCH, "签名不匹配");
        codeMsgMap.put(StatusCode.BAD_SIGN_FORMAT, "签名格式不正确");
        codeMsgMap.put(StatusCode.SIGN_NOT_VALID, "签名校验失败");
        codeMsgMap.put(StatusCode.DECODE_ERROR, "解码失败");
        codeMsgMap.put(StatusCode.DECRYPT_ERROR, "解密失败");
        codeMsgMap.put(StatusCode.CAPTCHA_EXPIRE, "验证码过期");
        codeMsgMap.put(StatusCode.CAPTCHA_INVALID, "验证码无效");
        codeMsgMap.put(StatusCode.PARAMETER_FORMAT_ERROR, "参数格式错误");
        codeMsgMap.put(StatusCode.MOBILE_FORMAT_ERROR, "手机格式错误");
        codeMsgMap.put(StatusCode.MOBILE_IS_NOT_EXITS, "手机号码不存在");
        codeMsgMap.put(StatusCode.MOBILE_IS_EXITS, "手机号码已注册");
        codeMsgMap.put(StatusCode.PASSWORD_IS_ERROR, "手机密码错误");
        //40100-40200//user_center
        codeMsgMap.put(StatusCode.TOKEN_IS_TIMEOUT, "token已失效 ");

        //40200-40300//news_cms

        //40300-40400//medical_beauty
        codeMsgMap.put(StatusCode.MB_CORRECT, "操作成功");
        codeMsgMap.put(StatusCode.MB_ERROR_SELECT, "查询失败,请重试.");
        codeMsgMap.put(StatusCode.MB_ERROR_INSERT, "添加失败,请重试.");
        codeMsgMap.put(StatusCode.MB_ERROR_UPDATE, "更新失败,请重试.");
        codeMsgMap.put(StatusCode.MB_ERROR_DELETE, "删除失败,请重试.");
        codeMsgMap.put(StatusCode.MB_ERROR, "操作失败");
        //40400-40500//shopping_mall
        codeMsgMap.put(StatusCode.SERVICE_CODE_USED, "服务码已使用");
        codeMsgMap.put(StatusCode.SERVICE_CODE_NOT_EXISTED, "服务码不存在");
        codeMsgMap.put(StatusCode.HOSPITAL_NOT_PERMITTED, "医院无权限验证服务码");
        codeMsgMap.put(StatusCode.SERVICE_CODE_EXPIRE, "服务码已过期");
        //40500-40600//crowd_funding

        //40600-40700//payment

        //40700-40700//sms
        codeMsgMap.put(StatusCode.SMS_TEXT_IS_EMPTY, "短信内容不能为空");
        codeMsgMap.put(StatusCode.TEMPLATE_IS_EMPTY, "模板ID或模板内容不能为空");
        codeMsgMap.put(StatusCode.ACCOUNT_BALANCE_LESS, "账户余额不足");
        codeMsgMap.put(StatusCode.KEYWORD_MATCHING, "关键词屏蔽");
        codeMsgMap.put(StatusCode.TEMPLATE_NOT_MATCH, "模板不匹配");
        codeMsgMap.put(StatusCode.PACKAGE_ERROR, "流量包错误");
        codeMsgMap.put(StatusCode.DUP_IN_SHORT_TIME, "同一手机号30秒内重复提交相同的内容");
        codeMsgMap.put(StatusCode.TOO_MANY_TIME_IN_5, "同一手机号5分钟内重复提交相同的内容超过3次");
        codeMsgMap.put(StatusCode.BLACK_PHONE_FILTER, "手机号黑名单过滤");
        codeMsgMap.put(StatusCode.DAY_LIMIT_PER_MOBILE, "24小时内同一手机号发送次数超过限制");
        //40800-40900//other

        //服务器错误(5字头)
        codeMsgMap.put(StatusCode.INTERNAL_SERVER_ERROR, "服务器错误");
        codeMsgMap.put(StatusCode.BAD_GATEWAY, "网关错误,上游服务器接收到无效的响应");
        codeMsgMap.put(StatusCode.SERVICE_UNAVAILABLE, "服务器维护或者过载，服务器当前无法处理请求");
        codeMsgMap.put(StatusCode.GATEWAY_TIMEOUT, "网关超时");
        codeMsgMap.put(StatusCode.HTTP_VERSION_NOT_SUPPORTED, "服务器不支持HTTP版本");
        //50000-50100//commons
        codeMsgMap.put(StatusCode.UNKNOWN_EXCEPTION, "服务器未知异常");
        codeMsgMap.put(StatusCode.DB_OPERATION_FAIL, "系统繁忙");
        codeMsgMap.put(StatusCode.RECORD_ALREADY_EXISTED, "记录已经存在");
        codeMsgMap.put(StatusCode.RECORD_NOT_EXISTED, "记录不存在");

        //50100-50200//user_center
        codeMsgMap.put(StatusCode.CHECK_CODE_SEND_ERROR, "动态密码发送失败");
        codeMsgMap.put(StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY, "动态密码和登录密码不能同时为空");

        //50200-50300//news_cms

        //50300-50400//medical_beauty

        //50400-50500//shopping_mall

        //50500-50600//crowd_funding
        codeMsgMap.put(StatusCode.ITEM_ERROR_INSERT, "项目插入失败");
        codeMsgMap.put(StatusCode.ITEM_ERROR_UPDATE, "项目修改失败");
        //50600-50700//payment

        //50700-50800//sms
        codeMsgMap.put(StatusCode.SUBMIT_SMS_FAILED, "发送短信失败");
        codeMsgMap.put(StatusCode.TEMPLATE_NOT_VALID, "模板无效");
        codeMsgMap.put(StatusCode.MARKET_FORBIDDEN, "营销短信暂停发送");
        //50800-50900//other

    }
}
