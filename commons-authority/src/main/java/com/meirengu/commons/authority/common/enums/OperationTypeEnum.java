package com.meirengu.commons.authority.common.enums;

/**
 * Created by xuxueli on 17/5/9.
 */
public enum OperationTypeEnum {

    INSERT(1),

    DELETE(2),

    UPDATE(3),

    SELECT(4),

    EXPORT(5);

    private int index;

    OperationTypeEnum(int idx) {
        this.index = idx;
    }

    public int getIndex() {
        return index;
    }

}
