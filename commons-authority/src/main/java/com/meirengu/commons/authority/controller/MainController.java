/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**  
 * @Title:  
 * @Description:  
 * @Author:duyulong@uqsoft.com 
 * @Since:2015-4-17  
 * @Version:1.1.0  
 */

@Controller
@RequestMapping("/main")
public class MainController extends BaseController{
	
	private static Log logger = LogFactory.getLog(MainController.class);
	
	/**
	 * 主界面
	 * @param res
	 * @param resp
	 * @return
	 * @Description:
	 */
	@RequestMapping("/loading")
	public String loading(HttpServletRequest res, HttpServletResponse resp){
		
		logger.info("main loading...");
		
		
		return "/main/index";
	}
}
