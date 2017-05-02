import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huoyan403 on 3/17/2017.
 */
public class Test {

    public static void main(String args[]) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeStart=sdf.parse("2011-09-20 12:30:45").getTime();
        System.out.println(timeStart);
        Date date = new Date(1493049600000L);
        System.out.println(sdf.format(date));
    }

    @org.junit.Test
    public void testGetByte(){
        for(int i=0;i<1000;i++){
            System.err.println((int)Math.random()*2);
        }
    }
    

}
