package com.meirengu.wxcs.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.util.ConstUtil;

public class ContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//MemcachedHandler.destory();
//		ThreadExecutor.destroy();
		logger.info("destroy");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ConstUtil.initParas();
			//MemcachedHandler.init();
//			ThreadExecutor.init();
		} catch (Exception e) {
			logger.error("init fail");
			e.printStackTrace();
		}
	}

}