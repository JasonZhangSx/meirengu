package com.meirengu.commons.authority.controller;

import com.meirengu.commons.authority.service.PermissionService;
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
@RequestMapping("/perm")
public class PermissionController extends BaseController {
	
	private static Log logger = LogFactory.getLog(PermissionController.class);
	@Resource
	PermissionService permissionService;
	
	@RequestMapping("/config")
	public String config(HttpServletRequest req, HttpServletResponse res){
		logger.info("perm config...");
		
		return "main/system/perm/index";
	}又名
	
	@RequestMapping("/addView")
	public String addView(HttpServletRequest req, HttpServletResponse res){
		logger.info("perm addView...");
		
		return "main/system/perm/add";
	}
	
	@RequestMapping("/modifyView")
	public String modifyView(HttpServletRequest req, HttpServletResponse res, String id){
		logger.info("perm modifyView...");
		
		req.setAttribute("id", id);
		return "main/system/perm/edit";
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public ResponseEntity<String>  savePerm(HttpServletRequest req, HttpServletResponse res,String name, String type, String value){
		logger.info("save Perm...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			Permission perm = createPerm(null, name, type, value);
			boolean flag = permissionService.savePermission(perm);
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
	public ResponseEntity<String> modifyPerm(HttpServletRequest req, HttpServletResponse res,String id, String name, String type, String value){
		logger.info("modify Perm...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			Permission perm = createPerm(id, name, type, value);
			boolean flag = permissionService.modifyPermission(perm);
			if(!flag){
				throw new BusinessException("modify Perm fail!");
			}
			success="success";
			logger.info("save Perm success");
		}catch(BusinessException e){
			logger.info("BusinessException",e);
		}catch(Exception e){
			logger.info("modify Perm error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public ResponseEntity<String> deletePerm(String ids){
		logger.info("delete Perm...");
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			List<String> idList = Arrays.asList(ids.split(","));
			boolean flag = permissionService.deletePermission(idList);
			if(!flag){
				throw new BusinessException("delete Perm fail!");
			}
			success="success";
			logger.info("delete Perm success");			
		}catch(Exception e){
			logger.debug("delete Perm error ", e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/getById")
	public ResponseEntity<String> getById(HttpServletRequest req, HttpServletResponse res, String id){
		logger.info("get Perm by id...");
		ResultPage result = new ResultPage();
		String success="fail";
		Permission perm = new Permission();
		try{
			if(StringUtil.isEmpty(id)){
				throw new BusinessException("id is null or empty str");
			}
			perm = permissionService.selectPermission(Integer.parseInt(id));
			
			success = "success";
			result.setObj(perm);
			logger.info("get Perm by id success");
			
		}catch(NumberFormatException e){
			logger.debug("NumberFormatException",e);
		}catch(Exception e){
			perm = null;
			logger.debug("get Perm by id error ",e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/getListByPage")
	@ResponseBody
	public ResponseEntity<String> getListByPage(String start, String limit, String name, String type){
		logger.info("get perm list for page...");
		Page<Permission> page = new Page<Permission>();
		ResultPage result = new ResultPage();
		String success="fail";
		try{
			if(!StringUtil.isEmpty(start)){
				page.setPageNow(Integer.parseInt(start));
			}
			if(!StringUtil.isEmpty(limit)){
				page.setPageSize(Integer.parseInt(limit));
			}
			Permission perm = createPerm(null, name, type,null);
			page = permissionService.getPageList(page, perm);
			
			success="success";
			result.setObj(page);
			logger.info("get perm list for page success");
		}catch(Exception e){
			logger.debug("get perm list for page error ", e);
		}
		result.setSuccess(success);
		return doResponse(JsonUtil.toJson(result), "json", "utf-8");
	}
	
	@RequestMapping("/PermAssignView")
	public ModelAndView PermAssignView(Integer roleId, String roleName){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Permission> assignRoleList = permissionService.getPerByRoleId(roleId);
		List<Permission> allRoleList = permissionService.getAllPermission();
		allRoleList.removeAll(assignRoleList);
		map.put("assignList", assignRoleList);
		map.put("allList", allRoleList);
		map.put("roleId", roleId);
		map.put("roleName", roleName);
		return new ModelAndView("main/system/perm/assign", map);
	}
	
	@RequestMapping("/assign")
	@ResponseBody
	public Map<String,Object> assign(Integer[] permissionId, Integer roleId){
		Map<String,Object> map = new HashMap<String, Object>();
		int i = permissionService.assignPermission(permissionId, roleId);
		map.put("status", i);
		return map;
	}
	
	
	/**
	 * 组装权限实体
	 * @param id
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 * @Description:
	 */
	private Permission createPerm(String id, String name, String type,
			String value) {
		logger.info("create Perm...");
		Permission perm = new Permission();
		if(!StringUtil.isEmpty(id)){
			perm.setId(Integer.parseInt(id));
		}
		if(!StringUtil.isEmpty(name)){
			perm.setName(name);
		}
		if(!StringUtil.isEmpty(type)){
			perm.setType(type);
		}
		if(!StringUtil.isEmpty(value)){
			perm.setValue(value);
		}
		
		logger.info("create Perm success");
		return perm;
	}
}
