package com.meirengu.mall.utils;

/**
 * 订单相关的编号生成
 * @author 建新
 * @create 2017-01-17 11:40
 */
public class OrderSNUtils {

    private static final String ORDER_SN_PREFIX = "91";

    private static final String UNION_SN_PREFIX = "92";

    private static final String REFUND_SN_PREFIX = "93";

    private static final String SERVICE_CODE_PREFIX = "";

    public static String getOrderSN(){

        StringBuffer sb = new StringBuffer();
        //2+8+6
        sb.append(ORDER_SN_PREFIX).append(RandomUtil.genTimeStamp(8)).append(RandomUtil.genRandomCode(6));

        return sb.toString();
    }

    public static String getUnionSN(){

        StringBuffer sb = new StringBuffer();
        //2+8+6
        sb.append(UNION_SN_PREFIX).append(RandomUtil.genTimeStamp(8)).append(RandomUtil.genRandomCode(6));

        return sb.toString();
    }

    public static String getRefundSN(){

        StringBuffer sb = new StringBuffer();
        //2+8+6
        sb.append(REFUND_SN_PREFIX).append(RandomUtil.genTimeStamp(8)).append(RandomUtil.genRandomCode(6));

        return sb.toString();
    }

    public static String getServiceCode(){
        StringBuffer sb = new StringBuffer();
        //0+5+5
        sb.append(SERVICE_CODE_PREFIX).append(RandomUtil.genTimeStamp(5)).append(RandomUtil.genRandomCode(5));

        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(RandomUtil.genTimeStamp(18));
        System.out.println(RandomUtil.genTimeStamp(6));
    }
}
