import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huoyan403 on 3/17/2017.
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String args[]) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeStart=sdf.parse("2011-09-20 12:30:45").getTime();
        System.out.println(timeStart);
        Date date = new Date(1493049600000L);
        System.out.println(sdf.format(date));
    }

    @org.junit.Test
    public void testGetByte(){
//        System.err.print(StringUtil.isEmpty("") && StringUtil.isEmpty("") && StringUtil.isEmpty(""));
        System.err.print(!StringUtil.isEmpty("1") ||  !StringUtil.isEmpty(null) ||  !StringUtil.isEmpty(null));


    }

    @org.junit.Test
    public void testSubString(){
        String string = "MRG-SYZR-20170503-3551";
        String result = string.substring(4,8);
        logger.info(result);

    }
     @org.junit.Test
    public void testdatadiff() throws Exception{
         int i  = DateAndTime.dateDiff("dd","2011-11-04 12:00:00","2011-10-01 10:00:00");
         int j  = DateAndTime.dateDiff("dd","2011-10-01 00:00:00","2011-11-04 00:00:59");
         logger.info(i+"");
         logger.info(j+"");
     }

    

}
