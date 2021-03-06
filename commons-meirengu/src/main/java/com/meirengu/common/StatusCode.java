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
    public static final int SIGN_MISSING_ARGUMENT = 40020;
    public static final int TIMESTAMP_MISSING = 40021;
    //40100-40200//user_center
    public static final int TOKEN_IS_TIMEOUT = 40101;
    public static final int CHECK_CODE_AND_PASSWORD_NOT_EMPTY = 40102;
    public static final int EMAIL_FORMAT_ERROR = 40103;
    public static final int INVALID_USERNAME_OR_PASSWORD = 40104;
    public static final int PASSWORD_IS_MALFORMED = 40105;
    public static final int MSG_TEMPLATE_INVALID = 40106;
    public static final int UPLOAD_SIZE_ERROR = 40107;
    public static final int USER_IS_LOCKED = 40108;
    public static final int THE_THIRD_PARTY_IS_BOUND = 40109;

    //40200-40300//news_cms
    public static final int TELPHONE_EXIST = 40200;

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
    public static final int PAYMENT_ACCOUNT_ERROR_INSERT = 40600;
    public static final int PAYMENT_ACCOUNT_SUCCESS_INSERT = 40601;
    public static final int PAYMENT_ACCOUNT_ERROR_INSERT_REPEAT = 40602;
    public static final int PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL = 40603;
    public static final int PAYMENT_ACCOUNT_SUCCESS_SELECT = 40604;
    public static final int PAYMENT_ACCOUNT_ERROR_SELECT = 40605;
    public static final int PAYMENT_ACCOUNT_ERROR_PARAMETER = 40606;
    public static final int PAYMENT_ACCOUNT_ERROR_UPDATE = 40607;
    public static final int PAYMENT_ACCOUNT_SUCCESS_UPDATE = 40608;
    public static final int PAYMENT_RECORD_ERROR_INSERT = 40609;
    public static final int PAYMENT_RECORD_ERROR_INSERT_REPEAT = 40610;
    public static final int PAYMENT_RECORD_ERROR_INSERT_NO_EXISTENT = 40611;
    public static final int PAYMENT_RECORD_ERROR_INSERT_ORDER_AMOUNT_NO_CONFORM = 40612;
    public static final int PAYMENT_RECORD_ERROR_INSERT_PAYMENT_AMOUNT_NO_CONFORM = 40613;
    public static final int PAYMENT_RECORD_ERROR_INSERT_PAYMENT_BALANCE_NO_INSUFFICIENT = 40614;
    public static final int PAYMENT_RECORD_SUCCESS_REFUND_APPLY = 40615;
    public static final int PAYMENT_RECORD_ERROR_REFUND_APPLY = 40616;
    public static final int PAYMENT_RECORD_SUCCESS_WITHDRAWALS_APPLY = 40617;
    public static final int PAYMENT_RECORD_ERROR_WITHDRAWALS_APPLY = 40618;
    public static final int PAYMENT_RECORD_SUCCESS_RECHARGE_APPLY = 40619;
    public static final int PAYMENT_RECORD_ERROR_RECHARGE_APPLY = 40620;
    public static final int PAYMENT_RECORD_SUCCESS_PAYMENT_APPLY = 40621;
    public static final int PAYMENT_RECORD_ERROR_PAYMENT_APPLY = 40622;
    public static final int PAYMENT_RECORD_ERROR_REFUND_CONFIRM = 40623;
    public static final int PAYMENT_RECORD_SUCCESS_REFUND_CONFIRM = 40624;
    public static final int PAYMENT_RECORD_ERROR_REFUND_CONFIRM_ISNULL = 40625;
    public static final int PAYMENT_RECORD_ERROR_WITHDRAWALS_CONFIRM_ISNULL = 40626;
    public static final int PAYMENT_RECORD_ERROR_STATUS = 40627;
    public static final int PAYMENT_RECORD_ERROR_BAOFU_PAY = 40628;
    public static final int PAYMENT_RECORD_ERROR_BAOFU_PAY_ENCRYPTION = 40629;
    public static final int PAYMENT_RECORD_ERROR_BAOFU_PAY_READ_CONFIGURE = 40630;
    public static final int PAYMENT_RECORD_ERROR_BAOFU_PAY_RETURN_VALUE_ISNULL = 40631;
    public static final int PAYMENT_RECORD_ERROR_BAOFU_AUTH=40632;
    public static final int PAYMENT_RECORD_SUCCESS_BAOFU_AUTH=40633;
    public static final int CHANNEL_BANK_SUCCESS_SELECT=40634;
    public static final int CHANNEL_BANK_ERROR_SELECT=40635;
    public static final int CHANNEL_BANK_ERROR_SELECT_IS_NULL=40636;
    public static final int PAYMENT_RECORD_ERROR_SELECT_IS_NULL=40637;
    public static final int PAYMENT_RECORD_ERROR_SELECT=40638;
    public static final int PAYMENT_RECORD_SUCCESS_SELECT=40639;
    public static final int PAYMENT_ACCOUNT_CHECK_PWD_SUCCESS=40640;
    public static final int PAYMENT_ACCOUNT_CHECK_PWD_ERROR_INCONSISTENT=40641;
    public static final int PAYMENT_ACCOUNT_CHECK_PWD_ERROR=40642;
    public static final int PAYMENT_COMMIT_SUCCESS_SELECT=40643;
    public static final int PAYMENT_COMMIT_ERROR_SELECT=40644;
    public static final int PAYMENT_COMMIT_SUCCESS_INSERT=40645;
    public static final int PAYMENT_COMMIT_ERROR_INSERT=40646;
    public static final int PAYMENT_COMMIT_SUCCESS_UPDATE=40647;
    public static final int PAYMENT_COMMIT_ERROR_UPDATE=40648;
    public static final int PAYMENT_COMMIT_RECORD_SUCCESS_SELECT=40649;
    public static final int PAYMENT_COMMIT_RECORD_ERROR_SELECT=40650;
    public static final int PAYMENT_COMMIT_RECORD_SUCCESS_INSERT=40651;
    public static final int PAYMENT_COMMIT_RECORD_ERROR_INSERT=40652;
    public static final int PAYMENT_COMMIT_BONUS_SUCCESS_INSERT=40653;
    public static final int PAYMENT_COMMIT_BONUS_ERROR_INSERT=40654;
    public static final int PAYMENT_COMMIT_BONUS_ERROR_SELECT=40655;
    public static final int PAYMENT_COMMIT_BONUS_SUCCESS_SELECT=40656;
    public static final int PAYMENT_COLLECTION_SUCCESS_INSERT=40657;
    public static final int PAYMENT_COLLECTION_ERROR_INSERT=40658;
    public static final int PAYMENT_COLLECTION_SUCCESS_SELECT=40659;
    public static final int PAYMENT_COLLECTION_ERROR_SELECT=40660;
    public static final int PAYMENT_COLLECTION_RECORD_SUCCESS_INSERT=40661;
    public static final int PAYMENT_COLLECTION_RECORD_ERROR_INSERT=40662;
    public static final int PAYMENT_RECORD_SUCCESS_WITHDRAWALS_CONFIRM=40663;
    public static final int PAYMENT_RECORD_ERROR_WITHDRAWALS_CONFIRM=40664;


    //40700-40750//sms
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
    //40751-40800//trade


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
    public static final int ADDRESS_ID_NOT_EMPTY = 50102;
    public static final int ADDRESS_ID_AND_USER_ID_MISMATCH = 50103;
    public static final int USER_NOT_EXITS = 50104;
    public static final int ADDREAA_IS_NOT_ALLOWED_DELETE = 50105;
    public static final int OLD_PASSWORD_IS_ERROR = 50106;
    public static final int USER_IS_EXITS = 50107;
    public static final int USER_PASSWORD_IS_EXITS = 50108;
    public static final int USER_INVITER_IS_NOT_EXITS = 50109;
    public static final int ADDRESS_IS_NOT_EXITS = 50110;
    public static final int VETIFY_IS_NOT_ALLOWED = 50111;
    public static final int NOTIFY_GENERATE_FAIL = 50112;
    public static final int NOTIFY_READ_FAIL = 50113;
    public static final int CHECK_CODE_SENDER_REFUSED = 50114;
    public static final int VETIFY_IS_ERROR = 50115;
    public static final int BANK_ID_CARD_IS_EXITS = 50116;
    public static final int REGISTER_IS_FAILED = 50117;
    public static final int ID_CARD_IS_NOT_MATCH = 50118;
    public static final int RETRIEVE_PROJECT_GET_MESSAGE_EMPTY = 50119;
    public static final int RETRIEVE_PROJECT_GET_MESSAGE_FAILED = 50120;
    public static final int RETRIEVE_PAYMENT_GET_MESSAGE_FAILED = 50121;
    public static final int ID_CARD_IS_ERROR = 50122;
    public static final int UPLOAD_HTML_STAMP_FAILED = 50123;
    public static final int UPLOAD_PDF_FIX_FAILED = 50124;
    public static final int FAILED_GET_DOWNLOAD_LINK = 50125;
    public static final int USER_NOT_AUTH = 50126;
    public static final int ID_CARD_IS_EXITS = 50127;
    public static final int LOGIN_FAILED = 50128;
    public static final int UPDATE_ADDRESS_FAILED = 50129;
    public static final int DELETE_ADDRESS_FAILED = 50130;
    public static final int MODIFY_PASSWORD_FAILED = 50131;
    public static final int UNBUND_IS_FAILED = 50132;
    public static final int BUND_IS_FAILED = 50133;

    public static final int FAILED_UPDATE_USER_CONTRACT = 50134;

    //50200-50250//news_cms
    public static final int FEEDBACK_ERROR_INSERT = 50200;
    public static final int BULLETIN_ERROR_INSERT = 50201;
    public static final int FEEDBACK_CONTENT_OUTOF = 50202;
    public static final int FEEDBACK_CONTENT_COUNT_OUTOF = 50203;
    public static final int BULLETIN_ERROR_LIST = 50204;
    public static final int CLASS_IS_REPEATED = 50205;
    public static final int QUESTION_IS_REPEATED = 50206;
    public static final int SLIDESHOW_SELECT_ERROR = 50207;
    public static final int SLIDESHOW_INSERT_ERROR = 50208;
    public static final int SLIDESHOW_UPDATE_ERROR = 50209;
    public static final int VERSION_INSERT_ERROR = 20210;
    public static final int VERSION_UPDATE_ERROR = 20211;
    public static final int VERSION_DELETE_ERROR = 20212;
    public static final int IP_WHITE_INSERT_ERROR = 20213;
    public static final int IP_WHITE_UPDATE_ERROR = 20214;

    //50251-50300//rebate
    public static final int REBATE_BATCH_ERROR_INSERT = 50251;
    public static final int REBATE_SN_REPEAT = 50252;
    public static final int HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE = 50253;
    public static final int REBATE_BATCH_INVALIDITY = 50254;
    public static final int REBATE_RECEIVE_INVALIDITY = 50255;
    public static final int NOT_MATCH_REBATE_BATCH_RULE = 50256;
    public static final int REBATE_USE_FAIL = 50257;
    public static final int REBATE_RECEIVE_INSERT_ERROR = 50258;
    public static final int REBATE_UPDATE_ERROR = 50259;
    public static final int HAS_REACHE_REBATE_LIMIT = 50260;
    public static final int REACHE_USED_INSERT_ERROR = 50261;





    //50300-50400//medical_beauty

    //50400-50500//shopping_mall

    //50500-50600//crowd_funding
    public static final int ITEM_ERROR_INSERT = 50500;
    public static final int ITEM_ERROR_UPDATE = 50501;
    public static final int ITEM_ERROR_DELETE = 50502;
    public static final int PARTNER_CLASS_ERROR_INSERT = 50503;
    public static final int PARTNER_CLASS_ERROR_UPDATE = 50504;
    public static final int PARTNER_CLASS_ALREADY_DELETE = 50505;
    public static final int ITEM_LEVEL_ERROR_INSERT = 50506;
    public static final int ITEM_LEVEL_ERROR_UPDATE = 50507;
    public static final int ITEM_LEVEL_ERROR_DELETE = 50508;
    public static final int ITEM_CONTENT_ERROR_INSERT = 50509;
    public static final int ITEM_CONTENT_ERROR_UPDATE = 50510;
    public static final int ITEM_CONTENT_ERROR_DELETE = 50511;
    public static final int ITEM_COOPERATION_ERROR_INSERT = 50512;
    public static final int ITEM_COOPERATION_ERROR_UPDATE = 50513;
    public static final int ITEM_COOPERATION_ERROR_DELETE = 50514;
    public static final int ITEM_INTERESTED_ERROR_INSERT = 50515;
    public static final int ITEM_INTERESTED_ERROR_UPDATE = 50516;
    public static final int ITEM_INTERESTED_ERROR_CANCLE = 50517;
    public static final int ITEM_BE_INTERESTED = 50518;
    public static final int ITEM_NOT_BE_INTERESTED = 50519;
    public static final int ITEM_LEVEL_APPOINT_FULL = 50520;
    public static final int ITEM_LEVEL_COMPLETED = 50521;
    public static final int ITEM_LEVEL_NULL = 50522;
    public static final int ITEM_LEVEL_AMOUNT_NOT_MATCH = 50523;
    public static final int ITEM_APPOINT_NUM_NOT_ENOUGH = 50524;
    public static final int ITEM_BUY_NUM_NOT_ENOUGH = 50525;
    public static final int ITEM_LEVEL_TOTAL_AMOUNT_ERROR = 50526;
    public static final int TYPE_INSERT_ERROR = 50527;
    public static final int TYPE_UPDATE_ERROR = 50528;
    public static final int TYPE_DELETE_ERROR = 50529;
    public static final int ITEM_TYPE_INSERT_ERROR = 50530;
    public static final int ITEM_TYPE_UPDATE_ERROR = 50531;
    public static final int ITEM_TYPE_DELETE_ERROR = 50532;
    public static final int OPERATE_RECORD_INSERT_ERROR = 50533;
    public static final int LEVEL_ROLLBACK_OUT_NUMBER = 50534;
    public static final int SHAREHOLDER_INSERT_ERROR = 50535;
    public static final int SHAREHOLDER_UPDATE_ERROR = 50536;
    public static final int SHAREHOLDER_DELETE_ERROR = 50537;
    public static final int EXTENTION_INSERT_ERROR = 50538;
    public static final int EXTENTION_UPDATE_ERROR = 50539;
    public static final int EXTENTION_DELETE_ERROR = 50540;

    //50600-50700//payment

    //50700-50750//sms
    public static final int SUBMIT_SMS_FAILED = 50700;
    public static final int TEMPLATE_NOT_VALID = 50701;
    public static final int MARKET_FORBIDDEN = 50702;
    //50751-50800//trade
    public static final int SUBSCRIPTIONS_ORDER_ERROR_INSERT = 50751;
    public static final int APPOINTMENT_ORDER_ERROR_INSERT = 50752;
    public static final int CANDIDATE_ORDER_ERROR_INSERT = 50753;
    public static final int ORDER_ERROR_UPDATE = 50754;
    public static final int ITEM_LEVEL_NOT_MATCH = 50755;
    public static final int ITEM_LEVEL_HAVE_ENOUGH = 50756;
    public static final int ITEM_LEVEL_NUM_ERROR = 50757;
    public static final int APPOINTMENT_CANCEL_ERROR = 50758;
    public static final int APPOINTMENT_HAVE_AUDIT = 50759;
    public static final int DELETE_ORDER_ERROR = 50760;
    public static final int ORDER_STATUS_NOT_DELETE = 50761;
    public static final int ORDER_NOT_EXIST = 50762;
    public static final int ORDER_CANDIDATE_HANDLE_ERROR = 50763;
    public static final int REFUND_APPLY_ERROR = 50764;
    public static final int REFUND_ADUIT_ERROR = 50765;
    public static final int REFUND_PERIOD_EXPIRED = 50766;
    public static final int ORDER_STATUS_NOT_MATCH = 50767;













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
        codeMsgMap.put(StatusCode.SIGN_MISSING_ARGUMENT, "签名参数缺失");
        codeMsgMap.put(StatusCode.TIMESTAMP_MISSING, "时间戳缺失");

        //40100-40200//user_center
        codeMsgMap.put(StatusCode.TOKEN_IS_TIMEOUT, "token已失效 ");
        codeMsgMap.put(StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY, "动态密码和登录密码不能同时为空");
        codeMsgMap.put(StatusCode.EMAIL_FORMAT_ERROR, "邮箱格式错误");
        codeMsgMap.put(StatusCode.INVALID_USERNAME_OR_PASSWORD, "无效的用户名或密码");
        codeMsgMap.put(StatusCode.PASSWORD_IS_MALFORMED, "密码格式错误");
        codeMsgMap.put(StatusCode.MSG_TEMPLATE_INVALID, "消息模板无效");
        codeMsgMap.put(StatusCode.UPLOAD_SIZE_ERROR, "上传文件大小超过限制！");
        codeMsgMap.put(StatusCode.USER_IS_LOCKED, "账户已被锁定，请联系客服！");
        codeMsgMap.put(StatusCode.THE_THIRD_PARTY_IS_BOUND, "该第三方已绑定！");


        //40200-40300//news_cms
        codeMsgMap.put(StatusCode.TELPHONE_EXIST, "该电话已报名！");

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

        //40700-40750//sms
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
        //40751-40800//trade


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
        codeMsgMap.put(StatusCode.CHECK_CODE_SEND_ERROR, "动态密码获取失败！请重新获取");
        codeMsgMap.put(StatusCode.USER_NOT_EXITS, "用户不存在");
        codeMsgMap.put(StatusCode.USER_IS_EXITS, "用户已存在");
        codeMsgMap.put(StatusCode.USER_PASSWORD_IS_EXITS, "用户密码已存在");
        codeMsgMap.put(StatusCode.USER_INVITER_IS_NOT_EXITS, "邀请人不存在");
        codeMsgMap.put(StatusCode.ADDRESS_ID_NOT_EMPTY, "用户地址id不存在");
        codeMsgMap.put(StatusCode.ADDRESS_IS_NOT_EXITS, "用户地址不存在");
        codeMsgMap.put(StatusCode.ADDRESS_ID_AND_USER_ID_MISMATCH, "用户地址id与用户id不匹配");
        codeMsgMap.put(StatusCode.ADDREAA_IS_NOT_ALLOWED_DELETE, "默认地址不允许删除");
        codeMsgMap.put(StatusCode.OLD_PASSWORD_IS_ERROR, "原密码错误");
        codeMsgMap.put(StatusCode.NOTIFY_GENERATE_FAIL, "消息生成失败");
        codeMsgMap.put(StatusCode.NOTIFY_READ_FAIL, "消息设置已读失败");
        codeMsgMap.put(StatusCode.VETIFY_IS_NOT_ALLOWED, "用户超过允许认证次数");
        codeMsgMap.put(StatusCode.CHECK_CODE_SENDER_REFUSED, "验证码限制 30 秒发送一次");
        codeMsgMap.put(StatusCode.VETIFY_IS_ERROR, "认证失败");
        codeMsgMap.put(StatusCode.BANK_ID_CARD_IS_EXITS, "该卡已绑定");
        codeMsgMap.put(StatusCode.REGISTER_IS_FAILED, "注册失败");
        codeMsgMap.put(StatusCode.ID_CARD_IS_NOT_MATCH, "身份证号不匹配");
        codeMsgMap.put(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_EMPTY, "调取项目模块获取数据为空! ");
        codeMsgMap.put(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_FAILED, "调取项目模块获取项目信息失败! ");
        codeMsgMap.put(StatusCode.RETRIEVE_PAYMENT_GET_MESSAGE_FAILED, "调取支付模块获取认证信息失败! ");
        codeMsgMap.put(StatusCode.ID_CARD_IS_ERROR, "身份证号不存在! ");
        codeMsgMap.put(StatusCode.USER_NOT_AUTH, "用户未认证! ");

        codeMsgMap.put(StatusCode.UPLOAD_HTML_STAMP_FAILED, "上传html 盖章失败! ");
        codeMsgMap.put(StatusCode.UPLOAD_PDF_FIX_FAILED, "上传pdf 保全失败! ");
        codeMsgMap.put(StatusCode.FAILED_GET_DOWNLOAD_LINK, "获取合同下载链接失败! ");
        codeMsgMap.put(StatusCode.FAILED_UPDATE_USER_CONTRACT, "更新合同表失败! ");

        codeMsgMap.put(StatusCode.ID_CARD_IS_EXITS, "该身份证已绑定! ");
        codeMsgMap.put(StatusCode.LOGIN_FAILED, "登陆失败! ");
        codeMsgMap.put(StatusCode.UPDATE_ADDRESS_FAILED, "修改地址失败! ");
        codeMsgMap.put(StatusCode.DELETE_ADDRESS_FAILED, "删除地址失败! ");
        codeMsgMap.put(StatusCode.MODIFY_PASSWORD_FAILED, "修改密码失败! ");
        codeMsgMap.put(StatusCode.UNBUND_IS_FAILED, "解绑失败! ");
        codeMsgMap.put(StatusCode.BUND_IS_FAILED, "绑定失败! ");

        //50200-50300//news_cms
        codeMsgMap.put(StatusCode.FEEDBACK_ERROR_INSERT, "意见反馈保存失败，请重试");
        codeMsgMap.put(StatusCode.BULLETIN_ERROR_INSERT, "公告列表获取失败，请重试");
        codeMsgMap.put(StatusCode.FEEDBACK_CONTENT_OUTOF, "提交字数应在200以内，请修改后重新提交");
        codeMsgMap.put(StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF, "用户每天提交反馈意见上限5条，请您明天再来");
        codeMsgMap.put(StatusCode.BULLETIN_ERROR_LIST, "获取公告列表失败，请重试");
        codeMsgMap.put(StatusCode.CLASS_IS_REPEATED, "分类不能重复");
        codeMsgMap.put(StatusCode.QUESTION_IS_REPEATED, "问题不能重复");
        codeMsgMap.put(StatusCode.SLIDESHOW_SELECT_ERROR, "轮播图获取失败");
        codeMsgMap.put(StatusCode.SLIDESHOW_INSERT_ERROR, "轮播图增加失败");
        codeMsgMap.put(StatusCode.SLIDESHOW_UPDATE_ERROR, "轮播图修改失败");
        codeMsgMap.put(StatusCode.VERSION_INSERT_ERROR, "版本信息插入失败");
        codeMsgMap.put(StatusCode.VERSION_UPDATE_ERROR, "版本信息修改失败");
        codeMsgMap.put(StatusCode.VERSION_DELETE_ERROR, "版本信息删除失败");
        codeMsgMap.put(StatusCode.IP_WHITE_INSERT_ERROR, "IP白名单插入失败");
        codeMsgMap.put(StatusCode.IP_WHITE_UPDATE_ERROR, "IP白名单修改失败");

        //50251-50300//rebate
        codeMsgMap.put(StatusCode.REBATE_BATCH_ERROR_INSERT, "抵扣券批次信息保存失败，请重试");
        codeMsgMap.put(StatusCode.REBATE_SN_REPEAT, "券号生成器重复数量过多，请联系技术部处理");
        codeMsgMap.put(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE, "该批次优惠券已领取完，请及时添加");
        codeMsgMap.put(StatusCode.REBATE_BATCH_INVALIDITY, "没有有效的该标识下的优惠券");
        codeMsgMap.put(StatusCode.REBATE_RECEIVE_INVALIDITY, "该优惠券已无效，请更换其他优惠券");
        codeMsgMap.put(StatusCode.NOT_MATCH_REBATE_BATCH_RULE, "订单不符合该优惠券使用规则，请更换其他优惠券");
        codeMsgMap.put(StatusCode.REBATE_RECEIVE_INSERT_ERROR, "优惠券领取信息插入异常，请重试");
        codeMsgMap.put(StatusCode.REBATE_UPDATE_ERROR, "优惠券信息更新失败，请重试");
        codeMsgMap.put(StatusCode.HAS_REACHE_REBATE_LIMIT, "用户领取该优惠券已达限领次数");
        codeMsgMap.put(StatusCode.REACHE_USED_INSERT_ERROR, "抵扣券使用记录跟新失败");





        //50300-50400//medical_beauty

        //50400-50500//shopping_mall

        //50500-50600//crowd_funding
        codeMsgMap.put(StatusCode.ITEM_ERROR_INSERT, "项目保存失败，请重试");
        codeMsgMap.put(StatusCode.ITEM_ERROR_UPDATE, "项目修改失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_ERROR_DELETE, "项目删除失败， 请重试");
        codeMsgMap.put(StatusCode.PARTNER_CLASS_ERROR_INSERT, "行业分类保存失败，请重试");
        codeMsgMap.put(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "行业分类修改失败，请重试");
        codeMsgMap.put(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "行业分类已删除");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_ERROR_INSERT, "项目回报档位保存失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_ERROR_UPDATE, "项目回报档位修改失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_ERROR_DELETE, "项目回报档位删除失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_CONTENT_ERROR_INSERT, "项目内容保存失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_CONTENT_ERROR_UPDATE, "项目内容修改失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_CONTENT_ERROR_DELETE, "项目内容删除失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_COOPERATION_ERROR_INSERT, "项目合作设置保存失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_COOPERATION_ERROR_UPDATE, "项目合作设置修改失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_COOPERATION_ERROR_DELETE, "项目合作设置删除失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_INTERESTED_ERROR_INSERT, "设置感兴趣项目失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_INTERESTED_ERROR_UPDATE, "感兴趣项目修改失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_INTERESTED_ERROR_CANCLE, "取消感兴趣项目失败， 请重试");
        codeMsgMap.put(StatusCode.ITEM_BE_INTERESTED, "该用户对该项目感兴趣");
        codeMsgMap.put(StatusCode.ITEM_NOT_BE_INTERESTED, "该用户对该项目不感兴趣");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_APPOINT_FULL, "该档位已约满");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_COMPLETED, "该档位已完成");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_NULL, "档位信息为空");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH, "传入档位金额与该档位金额不一致");
        codeMsgMap.put(StatusCode.ITEM_APPOINT_NUM_NOT_ENOUGH, "档位可预约数量不足");
        codeMsgMap.put(StatusCode.ITEM_BUY_NUM_NOT_ENOUGH, "档位可购买数量不足");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR, "该档位总金额错误");
        codeMsgMap.put(StatusCode.TYPE_INSERT_ERROR, "众筹类型插入失败");
        codeMsgMap.put(StatusCode.TYPE_UPDATE_ERROR, "众筹类型修改失败");
        codeMsgMap.put(StatusCode.TYPE_DELETE_ERROR, "众筹类型删除失败");
        codeMsgMap.put(StatusCode.ITEM_TYPE_INSERT_ERROR, "众筹项目分类插入失败");
        codeMsgMap.put(StatusCode.ITEM_TYPE_UPDATE_ERROR, "众筹项目分类修改失败");
        codeMsgMap.put(StatusCode.ITEM_TYPE_DELETE_ERROR, "众筹项目分类删除失败");
        codeMsgMap.put(StatusCode.OPERATE_RECORD_INSERT_ERROR, "操作记录插入失败");
        codeMsgMap.put(StatusCode.LEVEL_ROLLBACK_OUT_NUMBER, "回滚档位数量超过已筹数量");
        codeMsgMap.put(StatusCode.SHAREHOLDER_INSERT_ERROR, "插入项目股东失败");
        codeMsgMap.put(StatusCode.SHAREHOLDER_UPDATE_ERROR, "更新项目股东失败");
        codeMsgMap.put(StatusCode.SHAREHOLDER_DELETE_ERROR, "删除项目股东失败");
        codeMsgMap.put(StatusCode.EXTENTION_INSERT_ERROR, "新增项目扩展信息失败");
        codeMsgMap.put(StatusCode.EXTENTION_UPDATE_ERROR, "更新项目扩展信息失败");
        codeMsgMap.put(StatusCode.EXTENTION_DELETE_ERROR, "删除项目扩展信息失败");

        //40600-40700//payment
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_ERROR_INSERT_REPEAT, "该用户资金账户已存在");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_SUCCESS_INSERT, "用户资金账户创建成功");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_ERROR_INSERT, "用户资金账户创建失败");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL, "该用户资金账户不存在");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_SUCCESS_SELECT, "该用户资金账户不存在");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT, "该用户资金账户查询失败");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_ERROR_PARAMETER, "参数有误");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_ERROR_UPDATE, "该用户资金账户更新失败");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_SUCCESS_UPDATE, "该用户资金账户更新成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_INSERT, "账户流水记录失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_INSERT_REPEAT, "该订单号已存在");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_INSERT_NO_EXISTENT, "该订单号不存在");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_INSERT_ORDER_AMOUNT_NO_CONFORM, "订单金额不符");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_INSERT_PAYMENT_AMOUNT_NO_CONFORM, "支付金额不符");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_INSERT_PAYMENT_BALANCE_NO_INSUFFICIENT, "余额不足");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_REFUND_APPLY, "退款申请成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_REFUND_APPLY, "退款申请失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_WITHDRAWALS_APPLY, "提现申请成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_APPLY, "提现申请失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_RECHARGE_APPLY, "充值申请成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_RECHARGE_APPLY, "充值申请失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_PAYMENT_APPLY, "支付申请成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_PAYMENT_APPLY, "支付申请失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM, "退款确认失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_REFUND_CONFIRM, "退款确认成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM_ISNULL, "该退款记录不存在");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_CONFIRM_ISNULL, "该提现记录不存在");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_STATUS, "流水记录有误");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY, "宝付支付失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_ENCRYPTION, "Base64加密失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_READ_CONFIGURE, "宝付配置读取失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_RETURN_VALUE_ISNULL, "宝付返回值为空");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_AUTH, "宝付实名失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_BAOFU_AUTH, "宝付实名成功");
        codeMsgMap.put(StatusCode.CHANNEL_BANK_SUCCESS_SELECT, "渠道支持银行查询成功");
        codeMsgMap.put(StatusCode.CHANNEL_BANK_ERROR_SELECT, "渠道支持银行查询失败");
        codeMsgMap.put(StatusCode.CHANNEL_BANK_ERROR_SELECT_IS_NULL, "渠道支持银行查询数据为空");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_SELECT_IS_NULL, "交易记录查询数据为空");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_SELECT, "交易记录查询数据成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_SELECT, "交易记录查询数据失败");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_CHECK_PWD_SUCCESS, "交易密码校验成功");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_CHECK_PWD_ERROR_INCONSISTENT, "交易密码不符");
        codeMsgMap.put(StatusCode.PAYMENT_ACCOUNT_CHECK_PWD_ERROR, "交易密码校验失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_SUCCESS_SELECT, "待打款查询成功");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_ERROR_SELECT, "待打款查询失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_SUCCESS_INSERT, "待打款添加成功");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_ERROR_INSERT, "待打款添加失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_SUCCESS_UPDATE, "待打款更新成功");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_ERROR_UPDATE, "待打款更新失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_RECORD_SUCCESS_SELECT, "打款记录查询成功");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_SELECT, "打款记录查询失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_RECORD_SUCCESS_INSERT, "打款记录添加成功");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_INSERT, "打款记录添加失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_BONUS_SUCCESS_INSERT, "分红记录添加成功");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_BONUS_ERROR_INSERT, "分红记录添加失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_BONUS_ERROR_SELECT, "分红记录查询失败");
        codeMsgMap.put(StatusCode.PAYMENT_COMMIT_BONUS_SUCCESS_SELECT, "分红记录查询成功");
        codeMsgMap.put(StatusCode.PAYMENT_COLLECTION_SUCCESS_INSERT, "待收款记录添加成功");
        codeMsgMap.put(StatusCode.PAYMENT_COLLECTION_ERROR_INSERT, "待收款记录添加失败");
        codeMsgMap.put(StatusCode.PAYMENT_COLLECTION_SUCCESS_SELECT, "待收款记录查询成功");
        codeMsgMap.put(StatusCode.PAYMENT_COLLECTION_ERROR_SELECT, "待收款记录查询失败");
        codeMsgMap.put(StatusCode.PAYMENT_COLLECTION_RECORD_SUCCESS_INSERT, "收款记录添加成功");
        codeMsgMap.put(StatusCode.PAYMENT_COLLECTION_RECORD_ERROR_INSERT, "收款记录添加失败");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_CONFIRM, "提现确认成功");
        codeMsgMap.put(StatusCode.PAYMENT_RECORD_SUCCESS_WITHDRAWALS_CONFIRM, "提现确认失败");
        //50700-50750//sms
        codeMsgMap.put(StatusCode.SUBMIT_SMS_FAILED, "发送短信失败");
        codeMsgMap.put(StatusCode.TEMPLATE_NOT_VALID, "模板无效");
        codeMsgMap.put(StatusCode.MARKET_FORBIDDEN, "营销短信暂停发送");
        //50751-50800//trade
        codeMsgMap.put(StatusCode.SUBSCRIPTIONS_ORDER_ERROR_INSERT, "认购订单保存失败，请重试");
        codeMsgMap.put(StatusCode.APPOINTMENT_ORDER_ERROR_INSERT, "预约订单保存失败，请重试");
        codeMsgMap.put(StatusCode.CANDIDATE_ORDER_ERROR_INSERT, "候补预约保存失败，请重试");
        codeMsgMap.put(StatusCode.ORDER_ERROR_UPDATE, "订单更新失败，请重试");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_NOT_MATCH, "档位当前状态不能进行该操作，请刷新档位信息");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_HAVE_ENOUGH, "该档位已被预约满，请您进入候补预约");
        codeMsgMap.put(StatusCode.ITEM_LEVEL_NUM_ERROR, "该档位剩余份数不足，请您重新操作");
        codeMsgMap.put(StatusCode.APPOINTMENT_CANCEL_ERROR, "取消预约订单失败，请重试");
        codeMsgMap.put(StatusCode.APPOINTMENT_HAVE_AUDIT, "预约订单已通过审核，不可取消");
        codeMsgMap.put(StatusCode.DELETE_ORDER_ERROR, "删除订单失败，请重试");
        codeMsgMap.put(StatusCode.ORDER_STATUS_NOT_DELETE, "只有已失效和已退款的订单可以删除");
        codeMsgMap.put(StatusCode.ORDER_NOT_EXIST, "该订单不存在");
        codeMsgMap.put(StatusCode.ORDER_CANDIDATE_HANDLE_ERROR, "候补预约订单处理失败");
        codeMsgMap.put(StatusCode.REFUND_APPLY_ERROR, "退款申请失败，请重试");
        codeMsgMap.put(StatusCode.REFUND_ADUIT_ERROR, "退款审核失败，请重试");
        codeMsgMap.put(StatusCode.REFUND_PERIOD_EXPIRED, "退款期限已过");
        codeMsgMap.put(StatusCode.ORDER_STATUS_NOT_MATCH, "订单当前状态不能进行该操作，请刷新订单信息");




        //50800-50900//other

    }
}
