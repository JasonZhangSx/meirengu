package com.meirengu.news.utils;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author xfmei
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("cms");

	/**
	 * 获取图片展示地址
	 * @return
     */
	public static final String getShowPath(){
		return bundle.getString("picture.show.path");
	}

	/**
	 * 获取图片保存地址
	 * @return
     */
	public static final String getSavePath(){
		return bundle.getString("picture.save.path");
	}

	public static final String getValue(String key){
		return bundle.getString(key);
	}
}
