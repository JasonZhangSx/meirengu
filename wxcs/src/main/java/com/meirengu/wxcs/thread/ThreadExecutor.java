package com.meirengu.wxcs.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池管理者
 * 
 * @author Maxzh
 * @since 2010-12-24
 */
public class ThreadExecutor {
	
	private static ExecutorService executorService;
	private static Logger logger = LoggerFactory.getLogger(ThreadExecutor.class);
	
	public static void init(){
		executorService = Executors.newSingleThreadExecutor();
		logger.error("ThreadExecutor inited");
	}
	
	public static void destroy(){
		if(executorService!=null){
			executorService.shutdown();
			executorService = null;
		}
		logger.info("CustomExecutorService destroyed");
	}
	
	public static void execute(Runnable command) throws Exception{
		if(executorService==null){
			throw new Exception("ThreadExecutor has not been inited");
		}
		executorService.execute(command);
	}

}
