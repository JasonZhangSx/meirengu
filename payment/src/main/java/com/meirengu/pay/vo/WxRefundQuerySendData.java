package com.meirengu.pay.vo;

/**
 * 微信退款查询发送数据
 * @author 建新
 * @create 2017-02-15 17:30
 */
public class WxRefundQuerySendData {
    /**
     * 公众账号ID	appid	是	String(32)	wx8888888888888888	微信支付分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 商户号	mch_id	是	String(32)	1900000109	微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 设备号	device_info	否	String(32)	013467007045764	商户自定义的终端设备号，如门店编号、设备的ID等
     */
    private String device_info;
    /**
     * 随机字符串	nonce_str	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
     */
    private String nonce_str;
    /**
     * 签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
     */
    private String sign;
    /**
     * 签名类型	sign_type	否	String(32)	HMAC-SHA256	签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String sign_type;
    /**
     * 微信订单号	transaction_id	四选一	String(32)	1217752501201407033233368018	微信订单号
     */
    private String transaction_id;
    /**
     * 商户订单号	out_trade_no	String(32)	1217752501201407033233368018	商户系统内部的订单号
     */
    private String out_trade_no;
    /**
     * 商户退款单号	out_refund_no	String(32)	1217752501201407033233368018	商户侧传给微信的退款单号
     */
    private String out_refund_no;
    /**
     * 微信退款单号	refund_id	String(28)	1217752501201407033233368018    微信生成的退款单号，在申请退款接口有返回
     */
    private String refund_id;

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

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
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

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }
}
