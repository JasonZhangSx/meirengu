package com.meirengu.admin.service.impl;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLFeatureNotSupportedException;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/24 17:25.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder. getDbType();
    }
}
