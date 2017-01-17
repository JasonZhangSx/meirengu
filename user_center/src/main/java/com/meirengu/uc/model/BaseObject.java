package com.meirengu.uc.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 实体父类
 *
 * @author Marvin
 * @create 2017-01-10 下午2:41
 */
public class BaseObject implements Serializable {


    private static final long serialVersionUID = 1L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);

    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
