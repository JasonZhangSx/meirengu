package com.meirengu.mall.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类实体
 * @author 建新
 * @create 2017-01-10 17:15
 */
public class BaseEntity implements Serializable{

    private static final long serialVersionUID = 4652158570194824987L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);

    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
