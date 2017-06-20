package com.meirengu.mj.core.schedule;

import com.meirengu.mj.core.jobbean.RemoteHttpJobBean;
import com.meirengu.mj.core.thread.JobFailMonitorHelper;
import com.meirengu.mj.dao.IMJobInfoDao;
import com.meirengu.mj.dao.IMJobLogDao;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import com.meirengu.utils.scheduleUtil.MJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.*;

/**
 * base quartz scheduler util
 * @author xuxueli 2015-12-19 16:13:53
 */
public final class MJobDynamicScheduler implements ApplicationContextAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MJobDynamicScheduler.class);
    
    // Scheduler
    private static Scheduler scheduler;
    public void setScheduler(Scheduler scheduler) {
		MJobDynamicScheduler.scheduler = scheduler;
	}
    
    public void init() throws Exception {
        // admin monitor run
        JobFailMonitorHelper.getInstance().start();
    }
    
    // destroy
    public void destroy(){
        // admin monitor stop
        JobFailMonitorHelper.getInstance().toStop();

    }
    
    // xxlJobLogDao、xxlJobInfoDao
    public static IMJobLogDao xxlJobLogDao;
    public static IMJobInfoDao xxlJobInfoDao;

    @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		MJobDynamicScheduler.xxlJobLogDao = applicationContext.getBean(IMJobLogDao.class);
		MJobDynamicScheduler.xxlJobInfoDao = applicationContext.getBean(IMJobInfoDao.class);
	}
    
	@Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(scheduler, "quartz scheduler is null");
        logger.info(">>>>>>>>> init quartz scheduler success.[{}]", scheduler);
    }
	
	// getJobKeys
	@Deprecated
	public static List<Map<String, Object>> getJobList(){
		List<Map<String, Object>> jobList = new ArrayList<Map<String,Object>>();
		
		try {
			if (scheduler.getJobGroupNames()==null || scheduler.getJobGroupNames().size()==0) {
				return null;
			}
			String groupName = scheduler.getJobGroupNames().get(0);
			Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
			if (jobKeys!=null && jobKeys.size()>0) {
				for (JobKey jobKey : jobKeys) {
			        TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), Scheduler.DEFAULT_GROUP);
			        Trigger trigger = scheduler.getTrigger(triggerKey);
			        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			        TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			        Map<String, Object> jobMap = new HashMap<String, Object>();
			        jobMap.put("TriggerKey", triggerKey);
			        jobMap.put("Trigger", trigger);
			        jobMap.put("JobDetail", jobDetail);
			        jobMap.put("TriggerState", triggerState);
			        jobList.add(jobMap);
				}
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
		return jobList;
	}
	
	// fill job info
	public static void fillJobInfo(MJobInfo jobInfo) {
		// TriggerKey : name + group
        String group = String.valueOf(jobInfo.getJobGroup());
        String name = String.valueOf(jobInfo.getId());
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);

        try {
			Trigger trigger = scheduler.getTrigger(triggerKey);

			TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			
			// parse params
			if (trigger!=null && trigger instanceof CronTriggerImpl) {
				String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
				jobInfo.setJobCron(cronExpression);
			}

            if (trigger!=null && trigger.getStartTime() != null) {
                jobInfo.setStartTime(trigger.getStartTime());
            }

            //JobKey jobKey = new JobKey(jobInfo.getJobName(), String.valueOf(jobInfo.getJobGroup()));
            //JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            //String jobClass = jobDetail.getJobClass().getName();

			if (triggerState!=null) {
				jobInfo.setJobStatus(triggerState.name());
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	// check if exists
	public static boolean checkExists(String jobName, String jobGroup) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return scheduler.checkExists(triggerKey);
	}

    // simpleJob 新增
    @SuppressWarnings("unchecked")
    public static boolean addSimpleJob(String jobName, String jobGroup, Date startTime) throws SchedulerException {
        // TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);

        // TriggerKey valid if_exists
        if (checkExists(jobName, jobGroup)) {
            logger.info(">>>>>>>>> addJob fail, job already exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
            return false;
        }

        // CronTrigger : TriggerKey + cronExpression	// withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires();
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(simpleScheduleBuilder).startAt(startTime).build();

        // JobDetail : jobClass
        Class<? extends Job> jobClass_ = RemoteHttpJobBean.class;   // Class.forName(jobInfo.getJobClass());

        JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();
        /*if (jobInfo.getJobData()!=null) {
        	JobDataMap jobDataMap = jobDetail.getJobDataMap();
        	jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(), Map.class));
        	// JobExecutionContext context.getMergedJobDataMap().get("mailGuid");
		}*/

        // schedule : jobDetail + cronTrigger
        Date date = scheduler.scheduleJob(jobDetail, simpleTrigger);

        logger.info(">>>>>>>>>>> addSimpleJob success, jobDetail:{}, simpleTrigger:{}, date:{}", jobDetail, simpleTrigger, date);
        return true;
    }

	// cronJob 新增
	@SuppressWarnings("unchecked")
	public static boolean addCronJob(String jobName, String jobGroup, String cronExpression) throws SchedulerException {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        
        // TriggerKey valid if_exists
        if (checkExists(jobName, jobGroup)) {
            logger.info(">>>>>>>>> addJob fail, job already exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
            return false;
        }
        
        // CronTrigger : TriggerKey + cronExpression	// withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

        // JobDetail : jobClass
		Class<? extends Job> jobClass_ = RemoteHttpJobBean.class;   // Class.forName(jobInfo.getJobClass());
        
		JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();
        /*if (jobInfo.getJobData()!=null) {
        	JobDataMap jobDataMap = jobDetail.getJobDataMap();
        	jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(), Map.class));	
        	// JobExecutionContext context.getMergedJobDataMap().get("mailGuid");
		}*/
        
        // schedule : jobDetail + cronTrigger
        Date date = scheduler.scheduleJob(jobDetail, cronTrigger);

        logger.info(">>>>>>>>>>> addCronJob success, jobDetail:{}, cronTrigger:{}, date:{}", jobDetail, cronTrigger, date);
        return true;
    }

    // reschedule
    public static boolean rescheduleSimpleJob(String jobGroup, String jobName, Date startTime) throws SchedulerException {

        // TriggerKey valid if_exists
        if (!checkExists(jobName, jobGroup)) {
            logger.info(">>>>>>>>>>> rescheduleJob fail, job not exists, JobGroup:{}, JobName:{}", jobGroup, jobName);
            return false;
        }

        // TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        SimpleTrigger oldTrigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);

        if (oldTrigger != null) {
            // avoid repeat
            Date oldStartTime = oldTrigger.getStartTime();
            if (oldStartTime.getTime() == startTime.getTime()){
                return true;
            }

            // CronTrigger : TriggerKey + cronExpression
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires();
            oldTrigger = oldTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(simpleScheduleBuilder).startAt(startTime).build();

            // rescheduleJob
            scheduler.rescheduleJob(triggerKey, oldTrigger);
        } else {
            // CronTrigger : TriggerKey + cronExpression
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires();
            SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(simpleScheduleBuilder).startAt(startTime).build();

            // JobDetail-JobDataMap fresh
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            /*JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.clear();
            jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(), Map.class));*/

            // Trigger fresh
            HashSet<Trigger> triggerSet = new HashSet<Trigger>();
            triggerSet.add(simpleTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        }

        logger.info(">>>>>>>>>>> resumeJob success, JobGroup:{}, JobName:{}", jobGroup, jobName);
        return true;
    }
    
    // reschedule
	public static boolean rescheduleCronJob(String jobGroup, String jobName, String cronExpression) throws SchedulerException {
    	
    	// TriggerKey valid if_exists
        if (!checkExists(jobName, jobGroup)) {
        	logger.info(">>>>>>>>>>> rescheduleJob fail, job not exists, JobGroup:{}, JobName:{}", jobGroup, jobName);
            return false;
        }
        
        // TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        if (oldTrigger != null) {
            // avoid repeat
            String oldCron = oldTrigger.getCronExpression();
            if (oldCron.equals(cronExpression)){
                return true;
            }

            // CronTrigger : TriggerKey + cronExpression
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            oldTrigger = oldTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // rescheduleJob
            scheduler.rescheduleJob(triggerKey, oldTrigger);
        } else {
            // CronTrigger : TriggerKey + cronExpression
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // JobDetail-JobDataMap fresh
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            /*JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.clear();
            jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(), Map.class));*/

            // Trigger fresh
            HashSet<Trigger> triggerSet = new HashSet<Trigger>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        }

        logger.info(">>>>>>>>>>> resumeJob success, JobGroup:{}, JobName:{}", jobGroup, jobName);
        return true;
    }
    
    // unscheduleJob
    public static boolean removeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            result = scheduler.unscheduleJob(triggerKey);
            logger.info(">>>>>>>>>>> removeJob, triggerKey:{}, result [{}]", triggerKey, result);
        }
        return true;
    }

    // Pause
    public static boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> pauseJob success, triggerKey:{}", triggerKey);
        } else {
        	logger.info(">>>>>>>>>>> pauseJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }
    
    // resume
    public static boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> resumeJob success, triggerKey:{}", triggerKey);
        } else {
        	logger.info(">>>>>>>>>>> resumeJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }
    
    // run
    public static boolean triggerJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	JobKey jobKey = new JobKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.triggerJob(jobKey);
            result = true;
            logger.info(">>>>>>>>>>> runJob success, jobKey:{}", jobKey);
        } else {
        	logger.info(">>>>>>>>>>> runJob fail, jobKey:{}", jobKey);
        }
        return result;
    }


}