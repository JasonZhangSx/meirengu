package quartz.example2;

import org.quartz.*;

/**
 * Created by root on 2017/6/6.
 */
public class HelloJob2 implements Job {

    String jobSays;
    float myFloatValue;

    public String getJobSays() {
        return jobSays;
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public float getMyFloatValue() {
        return myFloatValue;
    }

    public void setMyFloatValue(float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }

    public void execute(JobExecutionContext context) {
        JobKey key = context.getJobDetail().getKey();
        System.out.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is : " + myFloatValue);

    }
}
