package com.meirengu.uc.service;

import com.meirengu.utils.GenEntityMysql;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-22 21:51
 */
public class GenEntityUtils {

    public static void main(String[] args) {

        GenEntityMysql gm = new GenEntityMysql();
        String basePath = System.getProperty("user.dir");
        String projectName = "user_center";
        String packageFather = "com.meirengu.uc";
        String packageModel = "model";
        String packageDao = "dao";
        String packageService = "service";
        String packageServiceImpl = "impl";
        String packageController = "controller";
        String mapperPath = "resources\\mybatis\\";

        String authorName = "建新";//作者名字
        //数据库连接
        String url = "jdbc:mysql://192.168.0.135:3306/user_center";
        String username = "dev";
        String password = "dev@1qa";
        String driver = "com.mysql.jdbc.Driver";
        String databaseName = "user_center";
        gm.initParams(basePath, projectName, packageFather, packageModel, packageDao, packageService,
                packageServiceImpl, packageController, mapperPath, authorName, url,
                username, password, driver, databaseName);
        gm.start("notify,user_notify");
    }
}
