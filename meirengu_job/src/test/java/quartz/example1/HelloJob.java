package quartz.example1;

import org.quartz.*;

/**
 * Created by root on 2017/6/6.
 */
public class HelloJob implements Job {

    public HelloJob(){

    }

    public void execute(JobExecutionContext context) {
        JobKey key = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        JobDataMap triDataMap = context.getTrigger().getJobDataMap();
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");
        String jobSays1 = triDataMap.getString("jobSays");
        float myFloatValue1 = triDataMap.getFloat("myFloatValue");
        System.out.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is : " + myFloatValue);
        System.out.println("Instance " + key + " of DumbJob says: " + jobSays1 + ", and val is : " + myFloatValue1);
    }
}
