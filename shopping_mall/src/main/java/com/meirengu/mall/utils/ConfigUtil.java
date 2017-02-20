package com.meirengu.mall.utils;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author xfmei
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("mall");

	/**
	 * 获取案例图片展示地址
	 * @return
     */
	public static final String getCaseShowPath(){
		return bundle.getString("case.picture.show.path");
	}

	/**
	 * 获取案例图片保存地址
	 * @return
     */
	public static final String getCaseSavePath(){
		return bundle.getString("case.picture.save.path");
	}

	/**
	 * 获取推荐位图片展示地址
	 * @return
	 */
	public static final String getRecommendShowPath(){
		return bundle.getString("recommend.picture.show.path");
	}

	/**
	 * 获取推荐位图片保存地址
	 * @return
	 */
	public static final String getRecommendSavePath(){
		return bundle.getString("recommend.picture.save.path");
	}


	public static final String getValue(String key){
		return bundle.getString(key);
	}
}
