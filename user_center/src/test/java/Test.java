import com.meirengu.utils.JacksonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/17/2017.
 */
public class Test {

    public static void main(String args[]){

       /* String mobile = "13207603761";
        System.err.print(mobile.substring(7,11));*/

        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("b","4");
        map1.put("a","5");
        map1.put("c","3");
        map1.put("d","5");
        String string = JacksonUtil.toJSon(map1);
//        String string = "{719191733=7.00, 171436521=749200.00}";
        Map<String,String> map =  (Map<String,String>) JacksonUtil.readValue(string,Map.class);

        for(String key : map.keySet()){
            String value = map.get(key);
            System.out.println(key+"  "+value);
        }
//       System.out.print("kong_123");
//       System.out.print("areaList_120100".getBytes());
       /* List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<1000000;i++){
            list.add(UuidUtils.getShortUuid());
        }
        for(int i=0;i<1000000;i++){
            for(int j=0;j<1000000;j++){
                if(i!=j){
                    if(list.get(i).equals(list.get(j))){
                        System.err.println(1);
                    }
                }
            }
        }
*/
        for(int i=0;i<1000;i++){



        }
    }
}
