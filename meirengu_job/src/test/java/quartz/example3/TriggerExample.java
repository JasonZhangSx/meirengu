package quartz.example3;

import org.quartz.SimpleTrigger;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.DateBuilder.*;


/**
 * Created by root on 2017/6/7.
 */


public class TriggerExample {
    public static void main(String[] args){
        SimpleTrigger trigger = (SimpleTrigger)newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(new Date())
                .forJob("job1", "group1")
                .build();

        trigger = (SimpleTrigger)newTrigger()
                .withIdentity("trigger2", "group2")
                .startAt(new Date())
                .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(10)
                    .withRepeatCount(10))
                .forJob("job1", "group1")
                .build();

        trigger = (SimpleTrigger)newTrigger()
                .withIdentity("trigger3", "group3")
                .startAt(futureDate(5, IntervalUnit.MINUTE))
                .forJob("job1", "group1")
                .build();

        trigger = (SimpleTrigger)newTrigger()
                .withIdentity("trigger3", "group3")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(5)
                        .repeatForever())
                .endAt(dateOf(22,0,0))
                .forJob("job1", "group1")
                .build();

        trigger = (SimpleTrigger)newTrigger()
                .withIdentity("trigger3", "group3")
                .startAt(evenHourDate(null))
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(2)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount())
                .endAt(dateOf(22,0,0))
                .forJob("job1", "group1")
                .build();
    }
}
