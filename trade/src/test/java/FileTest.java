import com.meirengu.utils.ObjectToFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2017/4/1.
 */
public class FileTest {
    public static void main(String[] args) {
//        String fileName = "c:/e/order.2017-04-01.txt";
//        try {
//            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
//            mapList = ObjectToFile.readObject(fileName);
//        } catch (Exception i) {
//            i.printStackTrace();
//        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("a",3);
        System.out.println(map.get("a"));
        System.out.println((String)map.get("a"));
    }
}
