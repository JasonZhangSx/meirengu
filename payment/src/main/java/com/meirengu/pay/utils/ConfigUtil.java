package com.meirengu.pay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置公用类
 * @author 建新
 * @create 2017-02-13 10:50
 */
public class ConfigUtil {

    protected static Properties props;

    protected static InputStream in;

    protected static long lastModified = 0l;

    protected static void init() {

        in = ConfigUtil.class.getClassLoader().getResourceAsStream("pay.properties");

        props = new Properties();

        load();
    }

    protected static void load() {
        try {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getConfig(String key) {
        if (props == null) {
            init();
        }

        return props.getProperty(key);
    }
}
