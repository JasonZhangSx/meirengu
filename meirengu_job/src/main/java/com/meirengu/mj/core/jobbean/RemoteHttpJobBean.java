package com.meirengu.mj.core.jobbean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.mj.core.enums.ExecutorFailStrategyEnum;
import com.meirengu.mj.core.model.MJobInfo;
import com.meirengu.mj.core.model.MJobLog;
import com.meirengu.mj.core.model.TriggerParam;
import com.meirengu.mj.core.schedule.MJobDynamicScheduler;
import com.meirengu.mj.core.thread.JobFailMonitorHelper;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.scheduleUtil.ReturnT;
import org.apache.http.HttpStatus;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * http job bean
 * “@DisallowConcurrentExecution” diable concurrent, thread size can not be only one, better given more
 * @author xuxueli 2015-12-17 18:20:34
 */
//@DisallowConcurrentExecution
public class RemoteHttpJobBean extends QuartzJobBean {
	private static Logger logger = LoggerFactory.getLogger(RemoteHttpJobBean.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		// load job
		JobKey jobKey = context.getTrigger().getJobKey();
		Integer jobId = Integer.valueOf(jobKey.getName());
		MJobInfo jobInfo = MJobDynamicScheduler.xxlJobInfoDao.loadById(jobId);

		// log part-1
		MJobLog jobLog = new MJobLog();
		jobLog.setJobGroup(jobInfo.getJobGroup());
		jobLog.setJobId(jobInfo.getId());
		MJobDynamicScheduler.xxlJobLogDao.save(jobLog);
		logger.debug(">>>>>>>>>>> xxl-job trigger start, jobId:{}", jobLog.getId());

		// log part-2 param
		//jobLog.setExecutorAddress(executorAddress);
		jobLog.setExecutorHandler(jobInfo.getExecutorHandler());
		jobLog.setExecutorParam(jobInfo.getExecutorParam());
		jobLog.setTriggerTime(new Date());

		// trigger request
		TriggerParam triggerParam = new TriggerParam();
		triggerParam.setJobId(jobInfo.getId());
		triggerParam.setExecutorHandler(jobInfo.getExecutorHandler());
		triggerParam.setExecutorParams(jobInfo.getExecutorParam());
		triggerParam.setLogId(jobLog.getId());
		triggerParam.setLogDateTim(jobLog.getTriggerTime().getTime());

		// do trigger
		ReturnT<String> triggerResult = doTrigger(triggerParam, jobInfo, jobLog);

		// fail retry
		if (triggerResult.getCode()==ReturnT.FAIL_CODE &&
				ExecutorFailStrategyEnum.match(jobInfo.getExecutorFailStrategy(), null) == ExecutorFailStrategyEnum.FAIL_RETRY) {
			ReturnT<String> retryTriggerResult = doTrigger(triggerParam, jobInfo, jobLog);

			triggerResult.setCode(retryTriggerResult.getCode());
			triggerResult.setMsg(triggerResult.getMsg() + "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试<<<<<<<<<<< </span><br><br>" +retryTriggerResult.getMsg());
		}

		// log part-2
		jobLog.setTriggerCode(triggerResult.getCode());
		jobLog.setTriggerMsg(triggerResult.getMsg());
		MJobDynamicScheduler.xxlJobLogDao.updateTriggerInfo(jobLog);

		// monitor triger
		JobFailMonitorHelper.monitor(jobLog.getId());
		logger.debug(">>>>>>>>>>> xxl-job trigger end, jobId:{}", jobLog.getId());
    }

    public ReturnT<String> doTrigger(TriggerParam triggerParam, MJobInfo jobInfo, MJobLog jobLog){
		StringBuffer triggerSb = new StringBuffer();

        triggerSb.append("<br>失败处理策略：").append(ExecutorFailStrategyEnum.match(jobInfo.getExecutorFailStrategy(), ExecutorFailStrategyEnum.FAIL_ALARM).getTitle());

		// trigger remote executor
		ReturnT<String> runResult = runExecutor(triggerParam);
		triggerSb.append("<br>----------------------<br>").append(runResult.getMsg());

		return new ReturnT<String>(runResult.getCode(), triggerSb.toString());
	}

	/**
	 * run executor
	 * @param triggerParam
	 * @return
	 */
	public ReturnT<String> runExecutor(TriggerParam triggerParam){
		ReturnT<String> runResult = null;
		try {
			String executorParams = triggerParam.getExecutorParams();
			String url = null;
			if (executorParams.contains("?")) {
				url = executorParams + "&logId=" + triggerParam.getLogId();
			} else {
				url = executorParams + "?logId=" + triggerParam.getLogId();
			}
			HttpUtil.HttpResult httpResult = HttpUtil.doGet(url);
			logger.debug("Request: {} getResponse: {}", url, httpResult);
			if (httpResult.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject resultJson = JSON.parseObject(httpResult.getContent());
				int code = resultJson.getIntValue("code");
				if (code == StatusCode.OK) {
					//成功
					runResult = new ReturnT<String>(ReturnT.SUCCESS_CODE, resultJson.getString("msg"));
				} else {
					//失败
					runResult = new ReturnT<String>(ReturnT.FAIL_CODE, resultJson.getString("msg"));
				}
			} else {
				//失败
				runResult = new ReturnT<String>(ReturnT.FAIL_CODE, "" + httpResult.getStatusCode());
			}
		} catch (Exception e) {
			logger.error("", e);
			runResult = new ReturnT<String>(ReturnT.FAIL_CODE, ""+e );
		}
		StringBuffer sb = new StringBuffer("触发调度：");
		sb.append("<br>code：").append(runResult.getCode());
		sb.append("<br>msg：").append(runResult.getMsg());
		runResult.setMsg(sb.toString());
		return runResult;
	}

}