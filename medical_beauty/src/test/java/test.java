import com.google.gson.Gson;
import com.meirengu.medical.util.HttpUtil;
import jdk.nashorn.internal.objects.Global;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/28 13:54.
 */
public class test {
    public static void main(String[] args) {
        Map params = new HashMap<String,String>();
        params.put("create_user", "1");
        String json= HttpUtil.httpPostForm("http://120.27.37.54/news_cms/article/list?create_user_type=3",params,"utf-8");
        Gson gson = new Gson();
        Map caseMap =gson.fromJson(json,Map.class);
        if (caseMap.get("code").equals("200")){
            caseMap= (Map) caseMap.get("data");
            caseMap.get("list");
            System.out.println(caseMap.toString());
        }
    }
}
