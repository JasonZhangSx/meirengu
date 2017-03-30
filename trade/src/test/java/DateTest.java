import com.meirengu.utils.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by root on 2017/3/30.
 */
public class DateTest {

    @Test
    public void test1(){
        Date beginDate = DateUtils.getCurrentDayBeginTime();
        System.out.println(DateUtils.dateToStr(beginDate));
        Date endDate = DateUtils.getCurrentDayEndTime();
        System.out.println(DateUtils.dateToStr(endDate));
    }
    public static void main(String[] args) {
        Date beginDate = DateUtils.getCurrentDayBeginTime();
        System.out.println(DateUtils.dateToStr(beginDate,"yyyy-MM-dd HH:mm:ss"));
        Date endDate = DateUtils.getCurrentDayEndTime();
        System.out.println(DateUtils.dateToStr(endDate,"yyyy-MM-dd HH:mm:ss"));
    }
}
