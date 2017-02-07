package com.meirengu.commons.authority.controller;

import com.uqsoft.common.access.entity.Page;
import com.uqsoft.common.access.entity.Role;
import com.uqsoft.common.access.entity.User;
import com.uqsoft.common.access.service.RoleService;
import com.uqsoft.common.access.service.UserService;
import com.uqsoft.common.exception.BusinessException;
import com.uqsoft.common.util.JsonUtil;
import com.uqsoft.common.util.ResultPage;
import com.uqsoft.common.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	private static Log logger = LogFactory.getLog(UserController.class);
	
	@Resource
	UserService userService;
	
	@Resource
	RoleService roleService;

	@RequestMapping("/config")
	public String config(HttpServletRequest req, HttpServletResponse res){
		logger.info("user config...");
		
		return "main/system/user/index";
	}
	
	@RequestMapping("/addView")
	public String addView(HttpServletRequest req, HttpServletResponse res){
		logger.info("user addView...");
		
		return "main/system/user/add";
	}
	
	@RequestMapping("/modifyView")
	public String modifyView(HttpServletRequest req, HttpServletResponse res, String id){
		logger.info("user modifyView...");
		
		req.setAttribute("id", id);
		return "main/system/user/edit";
	}
	
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public ResponseEntity<String> modifyPwd(HttpServletRequest req, HttpServletResponse res,String oldPassword, String newPassword){
		logger.info("modify User pwd...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			Subject user = SecurityUtils.getSubject();
			String loginName = (String) user.getPrincipal();
			boolean flag = userService.changePassword(loginName, oldPassword, newPassword);
			if(!flag){
				throw new BusinessException("modify User pwd fail!");
			}
			success="success";
			logger.info("modify User pwd success");
		}catch(BusinessException e){
			logger.info("BusinessException",e);
		}catch(Exception e){
			logger.info("modify User pwd error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ResponseEntity<String>  saveUser(HttpServletRequest req, HttpServletResponse res,String name, String password, String nickName,String jobNumber){
		logger.info("save User...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			User user = createUser(null, name, password, nickName, jobNumber);
			boolean flag = userService.saveUser(user);
			if(!flag){
				throw new BusinessException("save User fail!");
			}
			success="success";
			logger.info("save User success");
		}catch(BusinessException e){
			logger.info("BusinessException",e);
		}catch(Exception e){
			logger.info("save User error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseEntity<String> modifyUser(HttpServletRequest req, HttpServletResponse res,String id, String name, String password, String nickName,String jobNumber){
		logger.info("modify User...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			User user = createUser(id, name, null, nickName, jobNumber);
			boolean flag = userService.modifyUser(user);
			if(!flag){
				throw new BusinessException("modify User fail!");
			}
			success="success";
			logger.info("save User success");
		}catch(BusinessException e){
			logger.info("BusinessException",e);
		}catch(Exception e){
			logger.info("modify User error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public ResponseEntity<String> deleteUser(String ids){
		logger.info("delete User...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			List<String> idList = Arrays.asList(ids.split(","));
			boolean flag = userService.deleteUser(idList);
			if(!flag){
				throw new BusinessException("delete User fail!");
			}
			success="success";
			logger.info("delete User success");			
		}catch(Exception e){
			logger.debug("delete User error ", e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/getById")
	public ResponseEntity<String> getById(HttpServletRequest req, HttpServletResponse res, String id){
		logger.info("get User by id...");
		ResultPage result = new ResultPage();
		String success="fail";
		User user = new User();
		try{
			if(StringUtil.isEmpty(id)){
				throw new BusinessException("id is null or empty str");
			}
			user = userService.getById(Integer.parseInt(id));
			
			success = "success";
			result.setObj(user);
			logger.info("get User by id success");
			
		}catch(NumberFormatException e){
			logger.debug("NumberFormatException",e);
		}catch(Exception e){
			user = null;
			logger.debug("get User by id error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/getListByPage")
	@ResponseBody
	public ResponseEntity<String> getListByPage(String start, String limit, String name, String nickName, String jobNumber){
		logger.info("get user list for page...");
		Page<User> page = new Page<User>();
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			if(!StringUtil.isEmpty(start)){
				page.setPageNow(Integer.parseInt(start));
			}
			if(!StringUtil.isEmpty(limit)){
				page.setPageSize(Integer.parseInt(limit));
			}
			User user = createUser(null, name, null, nickName, jobNumber);
			page = userService.getPageList(page, user);
			
			success="success";
			result.setObj(page);
			logger.info("get user list for page success");
		}catch(Exception e){
			logger.debug("get user list for page error ", e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/RoleAssignView")
	public ModelAndView toRoleAssign(Integer id, String name){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Role> assignRoleList = roleService.getRolesByUsername(name);
		List<Role> allRoleList = roleService.getAllRole();
		allRoleList.removeAll(assignRoleList);
		map.put("assignList", assignRoleList);
		map.put("allList", allRoleList);
		map.put("id", id);
		map.put("name", name);
		return new ModelAndView("main/system/user/assign", map);
	}
	
	@RequestMapping("/assign")
	@ResponseBody
	public Map<String,Object> assign(Integer[] roleId, Integer id, String name){
		Map<String,Object> map = new HashMap<String, Object>();
		int i = roleService.assignRoles(roleId, id, name);
		map.put("status", i);
		return map;
	}
	
	/**
	 * 组装用户实体
	 * @param id
	 * @param name
	 * @param password
	 * @param nickName
	 * @param jobNumber
	 * @return
	 * @Description:
	 */
	private User createUser(String id, String name, String password,
			String nickName, String jobNumber) {
		logger.info("create User...");
		User user = new User();
		if(!StringUtil.isEmpty(id)){
			user.setId(Integer.parseInt(id));
		}
		if(!StringUtil.isEmpty(name)){
			user.setName(name);
		}
		if(!StringUtil.isEmpty(password)){
			user.setPassword(password);
		}
		if(!StringUtil.isEmpty(nickName)){
			user.setNickName(nickName);
		}
		if(!StringUtil.isEmpty(jobNumber)){
			user.setJobNumber(jobNumber);
		}
		
		logger.info("create user success");
		return user;
	}
}
