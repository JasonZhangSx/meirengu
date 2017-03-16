package com.meirengu.trade.common;

/**
 * 订单状态枚举类
 * Created by maoruxin on 2017/3/15.
 */
public enum OrderStateEnum {
    BOOK("预约待审核",1),
    BOOK_ADUIT_PASS("预约审核通过（前端显示未付款状态）",2),
    BOOK_ADUIT_FAIL("预约审核未通过（前端显示已关闭状态）",3),
    UNPAID("未付款",4),
    LOSS_EFFICACY("已失效（前端显示已关闭状态）",5),
    PAID("已付款",6),
    LOSS_EFFICACY_UNUSED("未消费失效",7),
    USED("已消费",8),
    REFUND_APPLY("申请退款",9),
    REFUND_CONFIRM("确认退款",10),
    REFUND_REFUSE("拒绝退款",11),
    REFUND_SUCCESS("退款成功",12),
    PAYMENT_FAIL("支付失败",13);

    // 成员变量
    private String name;
    private int value;
    // 构造方法
    private OrderStateEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }
    // 普通方法
    public static String getName(int value) {
        for (OrderStateEnum c : OrderStateEnum.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }



}
