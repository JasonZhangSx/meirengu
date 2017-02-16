package com.meirengu.wxcs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class WxConfigUtil {

    private static Properties props;
    private static File configFile;
    private static long lastModified = 0l;

    private static void init() {
        URL url = WxConfigUtil.class.getClassLoader().getResource("wxcs.properties");
        String file = url.getFile();
        configFile = new File(file);
        props = new Properties();
        load();
    }

    private static void load() {
        try {
            props.load(new FileInputStream(configFile));
            lastModified = configFile.lastModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getConfig(String key) {
        if (props == null || configFile == null) {
            init();
        }
        if (lastModified != configFile.lastModified()) {
            load();
        }
        return props.getProperty(key);
    }

    public static void main(String[] args) {
        init();
        load();
        System.out.println(getConfig("serviceCharacterEncoding"));
    }
}
