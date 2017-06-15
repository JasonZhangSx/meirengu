package quartz.example1;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
/**
 * Created by root on 2017/6/6.
 */
public class example {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();
        sched.start();
        // define the job and tie it to our
        JobDetail job = newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.14f)
                .build();
        // Trigger the job to run now, andthen every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .usingJobData("jobSays", "tri Hello World!")
                .usingJobData("myFloatValue", 3.16f)
                .startNow()
                .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(40)
                    .withRepeatCount(5))
                .build();
        sched.scheduleJob(job, trigger);

    }
}
