package com.meirengu.trade.common;

/**
 * 抵扣券类别枚举
 * Created by maoruxin on 2017/3/23.
 */
public enum RebateTypeEnum {
    COMMON("通用型", 1),
    FULL_REBATE("满减型", 2),
    PROJECT_REBATE("固定项目抵扣券", 3);

    // 成员变量
    private String name;
    private int value;
    // 构造方法
    private RebateTypeEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }
    // 普通方法
    public static String getName(int value) {
        for (RebateTypeEnum c : RebateTypeEnum.values()) {
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
