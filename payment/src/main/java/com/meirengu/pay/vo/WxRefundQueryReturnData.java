package com.meirengu.pay.vo;

import com.meirengu.utils.StringUtil;

/**
 * 微信退款查询返回数据
 * @author 建新
 * @create 2017-02-15 17:39
 */
public class WxRefundQueryReturnData {
    /**
     * 返回状态码	return_code	是	String(16)	SUCCESS	SUCCESS/FAIL
     */
    private String return_code;
    /**
     * 返回信息	return_msg	否	String(128)	签名失败    返回信息，如非空，为错误原因  签名失败    参数格式校验错误
     */
    private String return_msg;
    /**
     * 业务结果	result_code	是	String(16)	SUCCESS SUCCESS/FAIL    SUCCESS退款申请接收成功，结果通过退款查询接口查询    FAIL
     */
    private String result_code;
    /**
     * 错误码	err_code	是	String(32)	SYSTEMERROR	错误码详见第6节
     */
    private String err_code;
    /**
     * 错误描述	err_code_des	是	String(32)	系统错误	结果信息描述
     */
    private String err_code_des;
    /**
     * 公众账号ID	appid	是	String(32)	wx8888888888888888	微信分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 商户号	mch_id	是	String(32)	1900000109	微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 设备号	device_info	否	String(32)	013467007045764	终端设备号
     */
    private String device_info;
    /**
     * 随机字符串	nonce_str	是	String(28)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位
     */
    private String nonce_str;
    /**
     * 签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名算法
     */
    private String sign;
    /**
     * 微信订单号	transaction_id	是	String(32)	1217752501201407033233368018	微信订单号
     */
    private String transaction_id;
    /**
     * 商户订单号	out_trade_no	是	String(32)	1217752501201407033233368018	商户系统内部的订单号
     */
    private String out_trade_no;
    /**
     * 订单金额	total_fee	是	Int	100	订单总金额，单位为分，只能为整数，详见支付金额
     */
    private Integer total_fee;
    /**
     * 应结订单金额	settlement_total_fee	否	Int	100	应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     */
    private Integer settlement_total_fee;
    /**
     * 货币种类	fee_type	否	String(8)	CNY	订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    private String fee_type;
    /**
     * 现金支付金额	cash_fee	是	Int	100	现金支付金额，单位为分，只能为整数，详见支付金额
     */
    private Integer cash_fee;
    /**
     * 退款笔数	refund_count	是	Int	1	退款记录数
     */
    private Integer refund_count;
    /**
     * 退款资金来源	refund_account	否	String(30)	REFUND_SOURCE_RECHARGE_FUNDS
     * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款/基本账户
     * REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款
     */
    private Integer refund_accout;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public Integer getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(Integer settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public Integer getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(Integer refund_count) {
        this.refund_count = refund_count;
    }

    public Integer getRefund_accout() {
        return refund_accout;
    }

    public void setRefund_accout(Integer refund_accout) {
        this.refund_accout = refund_accout;
    }
}
