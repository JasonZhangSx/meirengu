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
	 * 获取图片上传路径
	 * 
	 */
	public static final String getLoadPicPath() {
		return bundle.getString("loadPicturePath");
	}

	/**
	 * 获取图片显示路径
	 * 
	 */
	public static final String getPicShowPath() {
		return bundle.getString("PictureShowPath");
	}

}
