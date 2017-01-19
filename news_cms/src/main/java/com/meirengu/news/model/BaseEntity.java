package com.meirengu.news.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by 建新 on 2016/12/29.
 */
public class BaseEntity implements Serializable {

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
