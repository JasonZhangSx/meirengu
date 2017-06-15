import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class pickNewsJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("在"+sdf.format(new Date())+"扒取新闻");
    }

    public static void main(String args[]) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(pickNewsJob.class)
                .withIdentity("job1", "jgroup1").build();
        SimpleTrigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(100, 10))
                .startNow()
                .build();

        //创建scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
    }
}