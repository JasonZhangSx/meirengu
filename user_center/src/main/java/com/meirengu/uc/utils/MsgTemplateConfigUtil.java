package com.meirengu.uc.utils;

import com.meirengu.utils.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

/**
 * 获取消息模板工具类
 *
 * @author 建新
 * @create 2017-03-23 12:21
 */
public class MsgTemplateConfigUtil {

    private static Properties props;

    private static File configFile;

    private static long lastModified = 0l;

    private static void init() {
        URL url = ConfigUtil.class.getClassLoader().getResource("message-template.properties");
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

        try {
            String value = props.getProperty(key);
            if(StringUtil.isEmpty(value)){
                return null;
            }
            return new String(value.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
