package com.meirengu.mj.core.schedule;

import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.listeners.SchedulerListenerSupport;

/**
 * 调度监听器
 * Created by maoruxin on 2017/6/21.
 */
public class MSchedulerListener extends SchedulerListenerSupport {

    @Override
    public void triggerFinalized(Trigger trigger) {
        MJobDynamicScheduler.triggerFinalized(trigger);
    }
}
