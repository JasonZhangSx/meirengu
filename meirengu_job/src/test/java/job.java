import com.meirengu.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2017/6/19.
 */
public class job {
    public static void main(String[] args) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("jobGroup", "1");
        paramMap.put("triggerType", "SimpleTrigger");
        paramMap.put("startTime", "2017-06-19 16:23:20");
        paramMap.put("jobDesc", "通过接口添加");
        paramMap.put("executorParam", "http://192.168.0.135/trade/order/generate_order_txt");
        paramMap.put("executorFailStrategy", "FAIL_ALARM");
        paramMap.put("author", "毛茹新");
        paramMap.put("alarmEmail", "381775433@qq.com");
        String url = "http://localhost:8080/mj/jobinfo/add";
        HttpUtil.HttpResult httpResult = HttpUtil.doPostForm(url, paramMap);
        System.out.println(httpResult);
    }
}
