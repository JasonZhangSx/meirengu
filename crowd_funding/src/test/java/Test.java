import com.meirengu.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-15 15:41
 */
public class Test {

    public static void main(String[] args){

        Map<String, Object> map = new HashMap<>();
        map.put("flag", new Integer(0));
        System.out.println(map.get("flag"));
    }
}
