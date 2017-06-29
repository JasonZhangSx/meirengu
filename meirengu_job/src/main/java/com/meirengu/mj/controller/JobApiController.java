package com.meirengu.mj.controller;

import com.meirengu.mj.dao.IMJobInfoDao;
import com.meirengu.mj.dao.IMJobLogDao;
import com.meirengu.mj.controller.annotation.PermessionLimit;
import com.meirengu.mj.core.model.MJobLog;
import com.meirengu.utils.scheduleUtil.MJobInfo;
import com.meirengu.mj.core.schedule.MJobDynamicScheduler;
import com.meirengu.utils.scheduleUtil.HandleCallbackParam;
import com.meirengu.utils.scheduleUtil.ReturnT;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by xuxueli on 17/5/10.
 */
@Controller
public class JobApiController {
    private static Logger logger = LoggerFactory.getLogger(JobApiController.class);

    @Resource
    public IMJobLogDao xxlJobLogDao;
    @Resource
    private IMJobInfoDao xxlJobInfoDao;


    @RequestMapping(value= "/api/callback", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    @PermessionLimit(limit=false)
    public ReturnT<String> callback(@RequestBody HandleCallbackParam handleCallbackParam){


        // valid log item
        MJobLog log = xxlJobLogDao.load(handleCallbackParam.getLogId());
        if (log == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "log item not found.");
        }

        // trigger success, to trigger child job, and avoid repeat trigger child job
        String childTriggerMsg = null;
        if (ReturnT.SUCCESS_CODE==handleCallbackParam.getExecuteResult().getCode() && ReturnT.SUCCESS_CODE!=log.getHandleCode()) {
            MJobInfo xxlJobInfo = xxlJobInfoDao.loadById(log.getJobId());
            if (xxlJobInfo!=null && StringUtils.isNotBlank(xxlJobInfo.getChildJobKey())) {
                childTriggerMsg = "<hr>";
                String[] childJobKeys = xxlJobInfo.getChildJobKey().split(",");
                for (int i = 0; i < childJobKeys.length; i++) {
                    String[] jobKeyArr = childJobKeys[i].split("_");
                    if (jobKeyArr!=null && jobKeyArr.length==2) {
                        MJobInfo childJobInfo = xxlJobInfoDao.loadById(Integer.valueOf(jobKeyArr[1]));
                        if (childJobInfo!=null) {
                            try {
                                boolean ret = MJobDynamicScheduler.triggerJob(String.valueOf(childJobInfo.getId()), String.valueOf(childJobInfo.getJobGroup()));

                                // add msg
                                childTriggerMsg += MessageFormat.format("<br> {0}/{1} 触发子任务成功, 子任务Key: {2}, status: {3}, 子任务描述: {4}",
                                        (i+1), childJobKeys.length, childJobKeys[i], ret, childJobInfo.getJobDesc());
                            } catch (SchedulerException e) {
                                logger.error("", e);
                            }
                        } else {
                            childTriggerMsg += MessageFormat.format("<br> {0}/{1} 触发子任务失败, 子任务xxlJobInfo不存在, 子任务Key: {2}",
                                    (i+1), childJobKeys.length, childJobKeys[i]);
                        }
                    } else {
                        childTriggerMsg += MessageFormat.format("<br> {0}/{1} 触发子任务失败, 子任务Key格式错误, 子任务Key: {2}",
                                (i+1), childJobKeys.length, childJobKeys[i]);
                    }
                }

            }
        }

        // handle msg
        StringBuffer handleMsg = new StringBuffer();
        if (log.getHandleMsg()!=null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (handleCallbackParam.getExecuteResult().getMsg() != null) {
            handleMsg.append(handleCallbackParam.getExecuteResult().getMsg());
        }
        if (childTriggerMsg !=null) {
            handleMsg.append("<br>子任务触发备注：").append(childTriggerMsg);
        }

        // success, save log
        log.setHandleTime(new Date());
        log.setHandleCode(handleCallbackParam.getExecuteResult().getCode());
        log.setHandleMsg(handleMsg.toString());
        xxlJobLogDao.updateHandleInfo(log);

        return ReturnT.SUCCESS;
    }
}
