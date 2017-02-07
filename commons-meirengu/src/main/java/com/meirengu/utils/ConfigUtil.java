package com.meirengu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 通用参数工具类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:22
 */
public class ConfigUtil {

    protected static Properties props;

    protected static InputStream in;

    protected static long lastModified = 0l;

    protected static void init() {

        in = ConfigUtil.class.getClassLoader().getResourceAsStream("commons.properties");

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
