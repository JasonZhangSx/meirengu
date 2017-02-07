package com.meirengu.commons.authority.controller;

import com.uqsoft.common.access.entity.Page;
import com.uqsoft.common.access.entity.Permission;
import com.uqsoft.common.access.entity.Role;
import com.uqsoft.common.access.service.PermissionService;
import com.uqsoft.common.access.service.RoleService;
import com.uqsoft.common.exception.BusinessException;
import com.uqsoft.common.util.JsonUtil;
import com.uqsoft.common.util.ResultPage;
import com.uqsoft.common.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	private static Log logger = LogFactory.getLog(RoleController.class);
	
	@Resource
	RoleService roleService;
	
	@Resource
	PermissionService permissionService;
	
	@RequestMapping("/config")
	public String config(HttpServletRequest req, HttpServletResponse res){
		logger.info("perm config...");
		
		return "main/system/role/index";
	}
	
	@RequestMapping("/addView")
	public String addView(HttpServletRequest req, HttpServletResponse res){
		logger.info("perm addView...");
		
		return "main/system/role/add";
	}
	
	@RequestMapping("/modifyView")
	public String modifyView(HttpServletRequest req, HttpServletResponse res, String id){
		logger.info("perm modifyView...");
		
		req.setAttribute("id", id);
		return "main/system/role/edit";
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public ResponseEntity<String>  savePerm(HttpServletRequest req, HttpServletResponse res,String name,String value, String siteIds){
		logger.info("save Role...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			Role role = createRole(null, name,value,siteIds);
			boolean flag = roleService.saveRole(role);
			if(!flag){
				throw new BusinessException("save Perm fail!");
			}
			success="success";
			logger.info("save Perm success");
		}catch(BusinessException e){
			logger.info("BusinessException",e);
		}catch(Exception e){
			logger.info("save Perm error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseEntity<String> modifyRole(HttpServletRequest req, HttpServletResponse res,String id, String name,String value,String siteIds){
		logger.info("modify Role...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			Role role = createRole(id, name,value,siteIds);
			boolean flag = roleService.modifyRole(role);
			if(!flag){
				throw new BusinessException("modify Role fail!");
			}
			success="success";
			logger.info("save Role success");
		}catch(BusinessException e){
			logger.info("BusinessException",e);
		}catch(Exception e){
			logger.info("modify Role error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public ResponseEntity<String> deleteRole(String ids){
		logger.info("delete Role...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			List<String> idList = Arrays.asList(ids.split(","));
			boolean flag = roleService.deleteRole(idList);
			if(!flag){
				throw new BusinessException("delete Role fail!");
			}
			success="success";
			logger.info("delete Role success");			
		}catch(Exception e){
			logger.debug("delete Role error ", e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/getById")
	public ResponseEntity<String> getById(HttpServletRequest req, HttpServletResponse res, String id){
		logger.info("get Role by id...");
		ResultPage result = new ResultPage();
		String success="fail";
		Role role = new Role();
		try{
			if(StringUtil.isEmpty(id)){
				throw new BusinessException("id is null or empty str");
			}
			role = roleService.selectRole(Integer.parseInt(id));
			
			success = "success";
			result.setObj(role);
			logger.info("get Role by id success");
			
		}catch(NumberFormatException e){
			logger.debug("NumberFormatException",e);
		}catch(Exception e){
			role = null;
			logger.debug("get Role by id error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/getListByPage")
	@ResponseBody
	public ResponseEntity<String> getListByPage(String start, String limit, String name, String value){
		logger.info("get Role list for page...");
		Page<Role> page = new Page<Role>();
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			if(!StringUtil.isEmpty(start)){
				page.setPageNow(Integer.parseInt(start));
			}
			if(!StringUtil.isEmpty(limit)){
				page.setPageSize(Integer.parseInt(limit));
			}
			Role role = createRole(null, name, value, null);
			page = roleService.getPageList(page, role);
			
			success="success";
			result.setObj(page);
			logger.info("get Role list for page success");
		}catch(Exception e){
			logger.debug("get Role list for page error ", e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	
	@RequestMapping("/PermAssignView")
	public ModelAndView PermAssignView(Integer id, String name){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Permission> assignRoleList = permissionService.getPerByRoleId(id);
		List<Permission> allRoleList = permissionService.getAllPermission();
		allRoleList.removeAll(assignRoleList);
		map.put("assignList", assignRoleList);
		map.put("allList", allRoleList);
		map.put("id", id);
		map.put("name", name);
		return new ModelAndView("main/system/role/assign", map);
	}
	
	@RequestMapping("/assign")
	@ResponseBody
	public Map<String,Object> assign(Integer[] permissionId, Integer id){
		Map<String,Object> map = new HashMap<String, Object>();
		int i = permissionService.assignPermission(permissionId, id);
		map.put("status", i);
		return map;
	}
	
	/**
	 * 组装角色实体
	 * @param id
	 * @param name
	 * @param value
	 * @param siteIds
	 * @return
	 * @Description:
	 */
	private Role createRole(String id, String name,
			String value, String siteIds) {
		logger.info("create Role...");
		Role role = new Role();
		if(!StringUtil.isEmpty(id)){
			role.setId(Integer.parseInt(id));
		}
		if(!StringUtil.isEmpty(name)){
			role.setName(name);
		}
		
		if(!StringUtil.isEmpty(value)){
			role.setValue(value);
		}
		role.setSiteIds(siteIds);
		logger.info("create Role success");
		return role;
	}

}
