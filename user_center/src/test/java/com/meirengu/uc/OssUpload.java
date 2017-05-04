package com.meirengu.uc;

import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.DateUtils;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.OSSFileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 4/25/2017.
 */
public class OssUpload {

    private static final Logger logger = LoggerFactory.getLogger(OssUpload.class);

    //测试list<String[]>
    @Test
    public void uploadTest001(){

       try {

            //oss配置信息  从oss读取文件
            String contractFolderName = ConfigUtil.getConfig("contractFolderName");
            String endpoint = ConfigUtil.getConfig("endpoint");
            String accessKeyId = ConfigUtil.getConfig("accessKeyId");
            String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
            String bucketName = ConfigUtil.getConfig("bucketName");
            String callback = ConfigUtil.getConfig("callbackUrl");

           List<String[]> list = new ArrayList<>();
           String[] arr = new String[3];
            arr[0] = "112123123";
            arr[1] = "456456456";
            arr[2] = "8080";

           String[] arr1 = new String[3];
            arr1[0] = "123456";
            arr1[1] = "789567";
            arr1[2] = "8090";

           list.add(arr);
           list.add(arr1);
           StringBuffer sb = new StringBuffer();
           for(int i=0;i<list.size();i++){
               String[] arr2 = list.get(i);
               for (int index = 0; index < arr2.length; index++)
               {
                   sb.append(arr2[index]);
                   if(index<arr2.length-1){
                       sb.append(",");
                   }
               }
               if(i<list.size()-1){
                   sb.append("|");
               }
           }


            String foldName = "user";
           String fileName = "user."+ DateUtils.getCurrentDate()+".txt";
           OSSFileUtils fileUpload = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
           fileUpload.upload(sb.toString(),fileName,foldName);

           OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
           String userInviterInfo = IOUtils.toString(fileUtils.download(foldName,fileName),"UTF-8");

//           String string = JacksonUtil.readValue(order,String.class);
           logger.info("批处理邀请人信息"+userInviterInfo);
       }catch (Exception e){
           e.printStackTrace();
        }
    }
    //测试list<Map>
    @Test
    public void uploadTest002(){

        try {

            //oss配置信息  从oss读取文件
            String contractFolderName = ConfigUtil.getConfig("contractFolderName");
            String endpoint = ConfigUtil.getConfig("endpoint");
            String accessKeyId = ConfigUtil.getConfig("accessKeyId");
            String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
            String bucketName = ConfigUtil.getConfig("bucketName");
            String callback = ConfigUtil.getConfig("callbackUrl");

            List<Map<String,String>> list = new ArrayList<>();

            Map<String,String> map = new HashMap<>();
            map.put("userId","2222222");
            map.put("investMoney","123.00");
            map.put("type","1");
            Map<String,String> map1 = new HashMap<>();
            map1.put("userId","11111");
            map1.put("investMoney","122.00");
            map1.put("type","2");

            list.add(map);
            list.add(map1);
            String result = JacksonUtil.toJSon(list);
            String foldName = "order";
            String fileName = "order."+ DateUtils.getCurrentDate()+".txt";
            OSSFileUtils fileUpload = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
            fileUpload.upload(result,fileName,foldName);

            OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
            String order = IOUtils.toString(fileUtils.download(foldName,fileName),"UTF-8");


            List<Map<String,String>> investInfo = JacksonUtil.readValue(order,List.class);

            // 根据用户id 获取邀请人信息 判断是否为空
            for (Map map11:investInfo){
                    logger.info("userId = "+map11.get("userId"));
                    logger.info("investMoney = "+map11.get("investMoney"));
                    logger.info("type = "+map11.get("type"));
//                for(Object userId : map11.keySet()){
//
//                    Object investMoney = map11.get(userId);
//                    logger.info("investMoney = "+investMoney);
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
