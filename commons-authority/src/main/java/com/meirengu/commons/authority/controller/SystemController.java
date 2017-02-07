/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.controller;

import com.uqsoft.common.exception.BusinessException;
import com.uqsoft.common.util.ResultPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/system")
public class SystemController extends BaseController{
	
	 private static Log logger = LogFactory.getLog(SystemController.class);
	 
	 /**
	  * 系统登陆界面
	  * @param res
	  * @param resp
	  * @return
	  * @Description:
	  */
	 @RequestMapping("loginForm")
	 public String loginForm(HttpServletRequest res, HttpServletResponse resp){
		 logger.info("loginForm loading...");
		 
		 return "/login/index";
	 }
	
	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @param req
	 * @param resp
	 * @return
	 * @Description:
	 */
	@RequestMapping("login")
	@ResponseBody
	public void login(String username, String password, boolean rememberMe, HttpServletRequest req, HttpServletResponse resp){
		ResultPage  result = new ResultPage();
		String success="false";
		String message="user login fail";
		try{
			if(username==null || "".equals(username) || password==null || "".equals(password)){
				success="false";
				message = "user login fail, the req params is null";
				throw new BusinessException(message);
			}
			Subject user = SecurityUtils.getSubject();
			
			logger.info("User is authenticated:  " + user.isAuthenticated());
			
			UsernamePasswordToken token = new UsernamePasswordToken(username,password,rememberMe);
	        user.login(token);
	        
	        logger.info("User is authenticated:  " + user.isAuthenticated());
	        if(!user.isAuthenticated()){
	        	success="false";
	        	message = "user login fail, the user authenticated is false";
	        	throw new BusinessException(message);
	        }
	        
	        success="true";
	        message="user login success";
	        logger.info(message);
  
		}catch(BusinessException e){
			logger.debug(message);
		}catch(Exception e){
			success="false";
			message="user login error ";
			logger.error(message, e);
		}
		result.setSuccess(success);
		result.setMsg(message);
		doResponseByJson(resp, result);
	}
	
	/**
	 * 登出
	 * @param username
	 * @param req
	 * @param resp
	 * @return
	 * @Description:
	 */
	@RequestMapping("logout")
	public String logout(String username, HttpServletRequest req, HttpServletResponse resp){
		
		
		Subject user = SecurityUtils.getSubject();
		logger.info("User is authenticated:  " + user.isAuthenticated());
		if (user.isAuthenticated()) {
			user.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			if (logger.isDebugEnabled()) {
				logger.debug("用户" + username + "退出登录");
			}
		}
		
        logger.info("User is authenticated:  " + user.isAuthenticated());
        
        return "/login/index";
	}
}
