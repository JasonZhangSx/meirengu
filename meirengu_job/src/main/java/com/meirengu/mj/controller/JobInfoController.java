package com.meirengu.mj.controller;

import com.meirengu.mj.controller.annotation.PermessionLimit;
import com.meirengu.mj.core.enums.JobGroup;
import com.meirengu.mj.service.IMJobService;
import com.meirengu.mj.core.enums.ExecutorFailStrategyEnum;
import com.meirengu.utils.scheduleUtil.HandleCallbackParam;
import com.meirengu.utils.scheduleUtil.MJobInfo;
import com.meirengu.utils.scheduleUtil.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {

	@Resource
	private IMJobService xxlJobService;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("ExecutorFailStrategyEnum", ExecutorFailStrategyEnum.values());		// 失败处理策略-字典

		// 任务组
		List<Map<String, Object>> jobGroupList =  JobGroup.getJobGroupList();
		model.addAttribute("JobGroupList", jobGroupList);
		return "jobinfo/jobinfo.index";
	}

	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
			@RequestParam(required = false, defaultValue = "10") int length,
			int jobGroup, String executorHandler, String filterTime) {
		
		return xxlJobService.pageList(start, length, jobGroup, executorHandler, filterTime);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ReturnT<String> add(MJobInfo jobInfo) {
		return xxlJobService.add(jobInfo);
	}


	@RequestMapping(value = "/addJob", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ReturnT<String> addJob(@RequestBody MJobInfo jobInfo) {
		return xxlJobService.add(jobInfo);
	}
	
	@RequestMapping("/reschedule")
	@ResponseBody
	public ReturnT<String> reschedule(MJobInfo jobInfo) {
		return xxlJobService.reschedule(jobInfo);
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public ReturnT<String> remove(int id) {
		return xxlJobService.remove(id);
	}
	
	@RequestMapping("/pause")
	@ResponseBody
	public ReturnT<String> pause(int id) {
		return xxlJobService.pause(id);
	}
	
	@RequestMapping("/resume")
	@ResponseBody
	public ReturnT<String> resume(int id) {
		return xxlJobService.resume(id);
	}
	
	@RequestMapping("/trigger")
	@ResponseBody
	public ReturnT<String> triggerJob(int id) {
		return xxlJobService.triggerJob(id);
	}
	
}
