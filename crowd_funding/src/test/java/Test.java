import com.meirengu.utils.DateAndTime;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-15 15:41
 */
public class Test {

    public static void main(String[] args) throws Exception{

        /*Map<String, Object> map = new HashMap<>();
        map.put("flag", new Integer(0));
        System.out.println(map.get("flag"));*/
        /*String sm = "is_page=true&item_status=你好&key=ca26e68b388b4ffcad4942fa648695f&page=1&per_page=10&timestamp=1490255543665&secret=09721ab88e0a552087391be1ef0c6826";
        String s = MD5Util.MD5(sm, "UTF-8").toUpperCase();
        System.out.println(s);

        String sm1 = "is_page=true&item_status=10,11&key=ca26e68b388b4ffcad4942fa648695f&page=1&per_page=10&timestamp=1490255543665&secret=09721ab88e0a552087391be1ef0c6826";
        String s1 = MD5Util.MD5(sm1, "UTF-8").toUpperCase();
        System.out.println(s1);*/
        /*String s = "哈哈123";
        System.out.println(s);

        String dat = DateAndTime.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");

        System.out.println(DateAndTime.convertStringToDate(DateAndTime.dateAdd("dd", dat, 3)));*/
        /*System.out.println(Math.ceil(3.54343));
        System.out.println(Math.round(3.1 - 0.5));*/

        /*String curIP = "192.168.0.135";
        String fromIP = "192.168.0.1";
        String toIP = "192.168.0.255";

        String curSeg[] = curIP.split("\\.", -1);
        String fromSeg[] = fromIP.split("\\.", -1);
        String toSeg[] = toIP.split("\\.", -1);

        int iCurIP = 0;
        int iFromIP = 0;
        int iToIP = 0;

        //turn ip to int
        for(int i=0; i<curSeg.length; i++) {

            iCurIP = iCurIP << 8 | Integer.parseInt(curSeg[i]);
            iFromIP = iFromIP << 8 | Integer.parseInt(fromSeg[i]);
            iToIP = iToIP << 8 | Integer.parseInt(toSeg[i]);
        }


        System.out.println(iCurIP>=iFromIP && iCurIP<=iToIP);*/

        System.out.println(~2);
    }
}
