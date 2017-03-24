import com.meirengu.utils.DateAndTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-02-09 14:15
 */
public class Test {

    public static void main(String[] args) throws Exception {

        /*Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("需要执行的任务");
                timer.cancel();
            }
        };

        timer.schedule(task, 10*1000);*/

        Date createTime = new Date();
        String expireTimeTemp = DateAndTime.dateAdd("dd", DateAndTime.convertDateToString(createTime, "yyyy-MM-dd HH:mm:ss"), 2);
        System.out.println(expireTimeTemp);


        /*long s = 1488253610000l;
        //System.out.print(new Date(s));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        System.out.println(sf.format(date).toString());*/
    }
}
