package com.meirengu.utils;

/**
 * 订单相关的编号生成
 * @author 建新
 * @create 2017-01-17 11:40
 */
public class OrderSNUtils {

    //前缀区分业务类型，第一位为业务类型：电商 9，众筹 8
    // 第二位为订单类型： 1 普通订单   2 联合订单   3退款订单     4退款订单   5充值订单

    private static final String ORDER_SN_PREFIX = "91";

    private static final String UNION_SN_PREFIX = "92";

    private static final String REFUND_SN_PREFIX = "93";

    private static final String SERVICE_CODE_PREFIX = "";
    //众筹认购订单
    public static final String CROWD_FUNDING_ORDER_SN_PREFIX = "81";
    //众筹退款订单
    public static final String CROWD_FUNDING_REFUND_SN_PREFIX = "83";
    //众筹预约订单
    public static final String CROWD_FUNDING_BOOK_SN_PREFIX = "84";
    //众筹充值订单
    public static final String CROWD_FUNDING_RECHARGE_SN_PREFIX = "85";

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

    public static String getOrderSNByPerfix(String perfix){

        StringBuffer sb = new StringBuffer();
        //2+8+6
        sb.append(perfix).append(RandomUtil.genTimeStamp(8)).append(RandomUtil.genRandomCode(6));

        return sb.toString();
    }


    public static void main(String[] args){
        System.out.println(RandomUtil.genTimeStamp(18));
        System.out.println(RandomUtil.genTimeStamp(6));
    }
}
