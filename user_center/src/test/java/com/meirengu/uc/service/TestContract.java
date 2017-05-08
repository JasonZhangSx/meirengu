package com.meirengu.uc.service;

import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.utils.ThemisClientInit;
import com.meirengu.utils.OSSFileUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mapu.themis.api.request.PingRequest;
import org.mapu.themis.api.response.PingResponse;
import rop.thirdparty.com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by huoyan403 on 4/12/2017.
 */
public class TestContract extends ThemisClientInit {

    @Test
    public void testContract(){
//        ContractFilePreservation contractFilePreservation = new ContractFilePreservation();
//        contractFilePreservation.ContractFilePreservation(new HashMap<>());
    }
      @Test
    public void testoss(){
          //文件夹名
          String contractFolderName = ConfigUtil.getConfig("contractFolderName");
          String endpoint = ConfigUtil.getConfig("endpoint");
          String accessKeyId = ConfigUtil.getConfig("accessKeyId");
          String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
          String bucketName = ConfigUtil.getConfig("bucketName");
          String callbackUrl = ConfigUtil.getConfig("callbackUrl");

          OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callbackUrl);
//          fileUtils.upload(inputStream,fileName,contractFolderName);
    }

          @Test
    public void testheTongBianhao(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        System.out.print("MRG-SYZR-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000));
//          fileUtils.upload(inputStream,fileName,contractFolderName);
    }
@Test
    public  void ping() {
//		Timer timer = new Timer();
//		TimerTask task = new TimerTask() {
//			public void run() {
        PingRequest request = new PingRequest();
        PingResponse response = getClient().testPing(request);
        if(!response.isSuccess()){
            try {
                FileUtils.write(new File("e:\\result.txt"), JSONObject.toJSONString(response), "UTF-8",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//			}
//		};
//		timer.schedule(task, 1000,1000);
    }


}
