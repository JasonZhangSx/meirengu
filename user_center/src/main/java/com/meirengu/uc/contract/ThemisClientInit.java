package com.meirengu.uc.contract;

import com.google.common.collect.Maps;
import com.meirengu.uc.utils.ConfigUtil;
import org.mapu.themis.ThemisClient;

import java.util.Map;

/**
 * 保全客户端初始化，这里只作演示，实际项目中可将ThemisClient类配置成spring bean方式
 * @author luopeng
 *         Created on 2014/5/5.
 */
public class ThemisClientInit {

	//请填入服务地址（根据环境的不同选择不同的服务地址），沙箱环境，正式环境
	public static final String SERVICE_URL;

	//请填入你的APPKey
	public static final String APP_KEY;

	//请填入你的APPSecret
	public static final String APP_SECRET;

	protected static Map<String,String> props = Maps.newHashMap();

	private static ThemisClient themisClient;

	static{

//		String filePath = "file:///e:/tmp/config.properties";
//		String filePath = "file:///D:/workspace/user/yibaoquan/src/main/resources/config.properties";
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		Resource resource = resolver.getResource(filePath);
//		Properties properties = new Properties();
//		try {
//			properties.load(resource.getInputStream());
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//
//		Enumeration<?> it = properties.propertyNames();
//		while (it.hasMoreElements()){
//			String propName = (String)it.nextElement();
//			props.put(propName,properties.getProperty(propName));
//		}

		SERVICE_URL = ConfigUtil.getConfig("services_url");
		APP_KEY = 	ConfigUtil.getConfig("app_key");
		APP_SECRET = ConfigUtil.getConfig("app_secret");

		themisClient = new ThemisClient(SERVICE_URL,APP_KEY,APP_SECRET);
	}

	public static ThemisClient getClient(){
		return themisClient;
	}

}
