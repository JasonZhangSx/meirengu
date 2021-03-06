package com.meirengu.utils.scheduleUtil;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * xxl-job info
 * @author xuxueli  2016-1-12 18:25:49
 */
public class MJobInfo {
	
	private int id;				// 主键ID	    (JobKey.name)
	
	private int jobGroup;		// 执行器主键ID	(JobKey.group)
	private String jobCron;		// 任务执行CRON表达式 【base on quartz】
	private String jobDesc;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	private String triggerType;
	
	private Date addTime;
	private Date updateTime;
	
	private String author;		// 负责人
	private String alarmEmail;	// 报警邮件

	private String executorHandler;		    // 执行器，任务Handler名称
	private String executorParam;		    // 执行器，任务参数
	private String executorFailStrategy;	// 失败处理策略
	
	private String childJobKey;		// 子任务Key

	private Integer finalized;		//  a Trigger has reached the condition in which it will never fire again

	// copy from quartz
	private String jobStatus;		// 任务状态 【base on quartz】

	private String quartzUrl;		// 调度服务地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(int jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobCron() {
		return jobCron;
	}

	public void setJobCron(String jobCron) {
		this.jobCron = jobCron;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAlarmEmail() {
		return alarmEmail;
	}

	public void setAlarmEmail(String alarmEmail) {
		this.alarmEmail = alarmEmail;
	}

    public String getExecutorHandler() {
		return executorHandler;
	}

	public void setExecutorHandler(String executorHandler) {
		this.executorHandler = executorHandler;
	}

	public String getExecutorParam() {
		return executorParam;
	}

	public void setExecutorParam(String executorParam) {
		this.executorParam = executorParam;
	}

	public String getExecutorFailStrategy() {
		return executorFailStrategy;
	}

	public void setExecutorFailStrategy(String executorFailStrategy) {
		this.executorFailStrategy = executorFailStrategy;
	}

	public String getChildJobKey() {
		return childJobKey;
	}

	public void setChildJobKey(String childJobKey) {
		this.childJobKey = childJobKey;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getQuartzUrl() {
		return quartzUrl;
	}

	public void setQuartzUrl(String quartzUrl) {
		this.quartzUrl = quartzUrl;
	}

	public Integer getFinalized() {
		return finalized;
	}

	public void setFinalized(Integer finalized) {
		this.finalized = finalized;
	}
}
